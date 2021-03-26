import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Testing_AddressBookJSONServer {

	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}
	
	private AddressBookData[] getEmployeeList() {
		Response response = RestAssured.get("/Contacts");
		System.out.println("Address Book Contacts  in json server  are : \n"+response.asString());
		AddressBookData[] arrayOfContacts = new Gson().fromJson(response.asString(),AddressBookData[].class);
		return arrayOfContacts;
	}

	
	@Test
	public void givenAddressBookDataInJSONServer_WhenRetreived_ShouldMatchCount()
	{
		AddressBookData[] arrayOfContacts = getEmployeeList();
		AddressBook_DB contact;
		contact = new AddressBook_DB(Arrays.asList(arrayOfContacts));
		long entries = contact.countEntries();
		Assert.assertEquals(2,entries);
	}
	
}