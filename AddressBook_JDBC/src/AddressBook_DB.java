import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.cj.jdbc.Driver;

public class AddressBook_DB {

	 private AddressBook_DataBaseService addressBookService;
	List<AddressBookData> addressBookList = new ArrayList<>();
	
	public AddressBook_DB() {
		addressBookService = AddressBook_DataBaseService.getInstance();
	}

	public AddressBook_DB(List<AddressBookData> addressBookList) {
		this();
		this.addressBookList = new ArrayList<>(addressBookList);
	}
	
	public List<AddressBookData> readData() {
		this.addressBookList = addressBookService.readData(); 
		return  addressBookList;
	}

	public void updateAddressBookData(String firstname, String city)
	{
		int result = new AddressBook_DataBaseService().updateEmployeeSalaryResult(firstname, city);
		if(result == 0) return;
		AddressBookData AddressBookData = this.getAddressBookData(firstname);
		if(AddressBookData != null) AddressBookData.setCity(city);
	}
	
	private AddressBookData getAddressBookData(String firstname) {
      AddressBookData addressBookData ;
      addressBookData = this.addressBookList.stream()
    		             .filter(addressBookEntry  ->  (addressBookEntry.getFirstname()).equals(firstname))
    		             .findFirst()
    		             .orElse(null);
		return addressBookData;
	}

	public boolean checkAddressBookDataSyncWithDB(String firstname) {
		try {
			return addressBookService.getAddressBookData(firstname).get(0).getFirstname().equals(getAddressBookData(firstname).getFirstname());
		} catch (IndexOutOfBoundsException e) {
		}
		return false;
	}

	public List<AddressBookData> getAddressBookDataForDateRange(Date startDate, Date endDate) {
		return addressBookService.getAddressBookDataForDateRange( startDate,  endDate);
	}

	public List<AddressBookData> getAddressBookDataByCity(String city) {
		return addressBookService.getAddressBookDataByCity(city);
	}

	public List<AddressBookData> getAddressBookDataByState(String state) {
		return addressBookService.getAddressBookDataByState(state);
	}

	public void addContactToAddressBook(String firstname, String lastname, String address, String city, String state,
			int zip, int phonenumber, String email, Date date) {
           addressBookList.add(addressBookService.addContactToAddressBook(firstname,lastname,address,city,state,zip,phonenumber,email,date));		
	}

	int count= 0;
	public int addContactToAddressBook(List<AddressBookData> asList) {
		Map<Integer,Boolean> contactAdditionStatus = new HashMap<Integer, Boolean>();
		asList.forEach(addressBookData ->{
			Runnable task = () -> {
				contactAdditionStatus.put(addressBookData.hashCode(),false);
				System.out.println("Employee Being Added: "+Thread.currentThread().getName());
				this.addContactToAddressBook(addressBookData.getFirstname(),
						addressBookData.getLastname(), addressBookData.getAddress()
				        , addressBookData.getCity(),addressBookData.getState(),addressBookData.getZip(),addressBookData.getPhonenumber()
				        , addressBookData.getEmail(),addressBookData.getDate());
				contactAdditionStatus.put(addressBookData.hashCode(), true);
				System.out.println("Employee Added : "+Thread.currentThread().getName());
				count++;
			};
			Thread thread = new Thread(task,addressBookData.getFirstname());
			thread.start();

		});
		while(contactAdditionStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(count);
		System.out.println(this.addressBookList);	
		return count;
	
	}

	public long countEntries() {
	 return addressBookList.size();
	}
}