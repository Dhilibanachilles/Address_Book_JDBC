import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class Testing_AddressBook
{
    AddressBook_DB addressBook = new AddressBook_DB() ;
    

         @Test
        public void  givenAddressBookData_WhenReturned_ShouldMatchEntryCount()
        {
        	List<AddressBookData> addressBookList = addressBook.readData();
        	Assert.assertEquals(7,addressBookList.size());
        }
         
     
 		@Test
 		public void givenCity_WhenUpdated_ShouldSyncWithDatabaseUsingPreparedStatement()
 		{
 			List<AddressBookData> addressBookData = addressBook.readData();
 			addressBook.updateAddressBookData("Priya","Chandigarh");
 			boolean result = addressBook.checkAddressBookDataSyncWithDB("Priya");
 			Assert.assertTrue(result);
 		}
 		
 		
 				@Test 
 				public void givenDateRange_WhenRetrieved_ShouldMatchContactEntriesCount()
 				{
 					List<AddressBookData> addressBookData = addressBook.readData();
 		            LocalDate startDate = LocalDate.of(2018,01,01);
 		            LocalDate endDate = LocalDate.now();
 		    		addressBookData = addressBook.getAddressBookDataForDateRange(Date.valueOf(startDate), Date.valueOf(endDate));
 		    		Assert.assertEquals(7, addressBookData.size());
 				}
 				
 				
 				@Test 
 				public void givenCity_WhenRetrieved_ShouldMatchContactEntriesCount()
 				{
 					List<AddressBookData> addressBookData = addressBook.readData();
 		            String city = "gurgaon";
 		    		addressBookData = addressBook.getAddressBookDataByCity(city);
 		    		Assert.assertEquals(3, addressBookData.size());
 				}
 				
 				@Test 
 				public void givenState_WhenRetrieved_ShouldMatchContactEntriesCount()
 				{
 					List<AddressBookData> addressBookData = addressBook.readData();
 		            String state = "haryana";
 		    		addressBookData = addressBook.getAddressBookDataByState(state);
 		    		Assert.assertEquals(6, addressBookData.size());
 				}

 			
 				@Test
 				public void givenNewContact_WhenAdded_ShouldSyncWithDatabase() {
 					addressBook.readData();
 					addressBook.addContactToAddressBook("balbir", "gahlawat","khanpur","jhajjar","haryana",123455,12324435,"balbir@abc.com",Date.valueOf(LocalDate.of(2019,12,23)));
 					boolean result = addressBook.checkAddressBookDataSyncWithDB("balbir");
 					Assert.assertTrue(result);
 				}
}