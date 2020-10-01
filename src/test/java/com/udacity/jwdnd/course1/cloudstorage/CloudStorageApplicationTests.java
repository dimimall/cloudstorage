package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.Credentials.CredentialTest;
import com.udacity.jwdnd.course1.cloudstorage.Notes.NoteTest;
import com.udacity.jwdnd.course1.cloudstorage.Registration.LoginTest;
import com.udacity.jwdnd.course1.cloudstorage.Registration.RegistrationTest;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private WebDriverWait wait;
	public String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.firefoxdriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new FirefoxDriver();
		baseURL = "http://localhost:" + port;
		wait = new WebDriverWait(driver, 5);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	//@Order(1)
	@Test
	public void getLoginPage() {
		driver.get(baseURL + "/home");
		Assertions.assertFalse(driver.getTitle().equals("Home"));

		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	//@Order(2)
	@Test
	public void testRegisterPage()
	{
		String firstName = "Dimitra";
		String lastName = "Malliarou";
		String username = "dimimall";
		String password = "10011982abc";

		driver.get(baseURL + "/home");
		Assertions.assertFalse(driver.getTitle().equals("Home"));

		driver.get(baseURL + "/signup");
		wait.until(ExpectedConditions.titleContains("Sign Up"));
		Assertions.assertEquals("Sign Up", driver.getTitle());

		RegistrationTest registrationTest = new RegistrationTest(driver);
		registrationTest.createUser(firstName,lastName,username,password);

		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());

		LoginTest loginTest = new LoginTest(driver);
		loginTest.loginUser(username,password);

		wait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertEquals("Home", driver.getTitle());

		NoteTest noteTest = new NoteTest(driver);
		noteTest.logoutHomePage();
		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());


		driver.get(baseURL + "/home");
		Assertions.assertFalse(driver.getTitle().equals("Home"));
	}

//	//@Order(3)
	@Test
	public void testCreateNotes()
	{
		String firstName = "Theodora";
		String lastName = "Malliarou";
		String username = "theodora";
		String password = "10011982Abc";

		driver.get(baseURL + "/signup");
		wait.until(ExpectedConditions.titleContains("Sign Up"));
		Assertions.assertEquals("Sign Up", driver.getTitle());

		RegistrationTest registrationTest = new RegistrationTest(driver);
		registrationTest.createUser(firstName,lastName,username,password);
		//registrationTest.loginPage();

		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
		LoginTest loginTest = new LoginTest(driver);
		loginTest.loginUser(username,password);

		Assertions.assertEquals("Home", driver.getTitle());

		String noteTitle = "Test Note";
		String noteDescription = "test test test";

		NoteTest noteTest = new NoteTest(driver);
		noteTest.openNavTapNote();
		noteTest.createNote(noteTitle,noteDescription);
		noteTest.logoutHomePage();

		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
		loginTest.loginUser(username,password);

		wait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertEquals("Home", driver.getTitle());

		noteTest.openNavTapNote();
		noteTest.editNoteButton();

		Notes notes = noteTest.getFirstNote();
		Assertions.assertEquals(noteTitle,notes.getNoteTitle());
		Assertions.assertEquals(noteDescription,notes.getNoteDescription());
	}

	//@Order(4)
	@Test
	public void testEditNotes()
	{
		String firstName = "Mary";
		String lastName = "Malliarou";
		String username = "mary";
		String password = "10011982aBc";

		driver.get(baseURL + "/signup");
		wait.until(ExpectedConditions.titleContains("Sign Up"));
		Assertions.assertEquals("Sign Up", driver.getTitle());

		RegistrationTest registrationTest = new RegistrationTest(driver);
		registrationTest.createUser(firstName,lastName,username,password);
		//registrationTest.loginPage();

		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
		LoginTest loginTest = new LoginTest(driver);
		loginTest.loginUser(username,password);

		Assertions.assertEquals("Home", driver.getTitle());

		String noteTitle = "Test Note";
		String noteDescription = "test test test";

		NoteTest noteTest = new NoteTest(driver);
		noteTest.openNavTapNote();
		noteTest.createNote(noteTitle,noteDescription);


		noteTest.logoutHomePage();
		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
		loginTest.loginUser(username,password);

		wait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertEquals("Home", driver.getTitle());

		String newNoteTitle = "To-Do List";
		String newNoteDescription = "Walk the dog, Wash the Car";

		noteTest.openNavTapNote();
		noteTest.editNoteButton();

		Notes firstNote = noteTest.getFirstNote();
		noteTest.updateNote(firstNote, newNoteTitle, newNoteDescription); //Redirects to result page
		noteTest.logoutHomePage();

		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
		loginTest.loginUser(username,password);

		wait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertEquals("Home", driver.getTitle());

		noteTest.openNavTapNote();
		noteTest.editNoteButton();

		Notes newNote = noteTest.getFirstNote();
		Assertions.assertEquals(newNoteTitle, newNote.getNoteTitle());
//		Assertions.assertEquals(newNoteDescription, newNote.getNoteDescription());
	}

	//@Order(5)
	@Test
	public void testDeleteNote() {
		// Data to be used
		String firstName = "Jeff";
		String lastName = "Bezos";
		String username = "jeff123";
		String password = "bez123";

		// Signing up User, Creating a Note and logging out.
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		RegistrationTest registrationTest = new RegistrationTest(driver);
		registrationTest.createUser(firstName, lastName, username, password);
		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());

		// Initializing Selenium Object Page and logging new user in.
		LoginTest loginTest = new LoginTest(driver);
		loginTest.loginUser(username, password); // Automatically redirects to home page.
		wait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertEquals("Home", driver.getTitle());

		// Creating Note
		NoteTest noteTest = new NoteTest(driver);
		noteTest.openNavTapNote();
		noteTest.createNote("Any Title", "Any Description"); //Redirects to result page

		noteTest.logoutHomePage();
		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());

		// LOGGING IN EXISTING USER AND DELETING NOTE.
		loginTest.loginUser(username, password);
		wait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertEquals("Home", driver.getTitle());

		noteTest.openNavTapNote();
		noteTest.deleteNoteButton();

		noteTest.logoutHomePage();

		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
		loginTest.loginUser(username,password);

		wait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertEquals("Home", driver.getTitle());

		// VERIFYING NOTE WAS SUCCESSFULLY DELETED
		noteTest.openNavTapNote();
		Assertions.assertFalse(noteTest.firstNoteDelete());

	}

//	@Order(6)
	@Test
	public void testCreateCredentials()
	{
		String firstName = "Dimitra";
		String lastName = "Malliarou";
		String username = "dimi";
		String password = "10011982aBc";

		driver.get(baseURL + "/signup");
		wait.until(ExpectedConditions.titleContains("Sign Up"));
		Assertions.assertEquals("Sign Up", driver.getTitle());

		RegistrationTest registrationTest = new RegistrationTest(driver);
		registrationTest.createUser(firstName,lastName,username,password);

		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
		LoginTest loginTest = new LoginTest(driver);
		loginTest.loginUser(username,password);

		Assertions.assertEquals("Home", driver.getTitle());

		String url = "Test Note";
		String username1 = "test test test";
		String password1 = "10011982abc";

		CredentialTest credentialTest = new CredentialTest(driver);
		credentialTest.openNavTapCredential();
		credentialTest.createCredential(url,username1,password1);
		credentialTest.logoutHomePage();

		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
		loginTest.loginUser(username,password);

		wait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertEquals("Home", driver.getTitle());

		credentialTest.openNavTapCredential();
		credentialTest.editCredentialButton();

		Credentials credentials = credentialTest.getFirstCredentials();
		Assertions.assertEquals(url,credentials.getUrl());
		Assertions.assertEquals(username1,credentials.getUsername());
		//Assertions.assertEquals(password1,credentials.getPassword());
	}
//	@Order(7)
	@Test
	public void testEditCredential() {
		// Data to be used
		String firstName = "Neil";
		String lastName = "Armstrong";
		String username = "neil123";
		String password = "arm123";

		// Signing up User, Creating a Credential and logging out.
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		RegistrationTest registrationTest = new RegistrationTest(driver);
		registrationTest.createUser(firstName, lastName, username, password);
		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
		// Initializing Selenium Object Page and logging new user in.
		LoginTest loginTest = new LoginTest(driver);
		loginTest.loginUser(username, password); // Automatically redirects to home page.
		wait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertEquals("Home", driver.getTitle());

		// CREATING CREDENTIALS
		String credentialUrl = "www.google.com";
		String credentialUsername = "Martinez";
		String credentialPassword = "Brothers";
		CredentialTest credentialTest = new CredentialTest(driver);
		credentialTest.openNavTapCredential();
		credentialTest.createCredential(credentialUrl, credentialUsername, credentialPassword);
		credentialTest.logoutHomePage();

		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
		loginTest.loginUser(username, password);

		// LOGGING IN EXISTING USER AND EDITING CREDENTIALS.
		String newUrlCredential = "www.facebook.com";
		String newUsernameCredential = "Buzz";
		String newPasswordCredential = "Aldrin";

		wait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertEquals("Home", driver.getTitle());
		credentialTest.openNavTapCredential();

		// EDITING CREDENTIALS
		credentialTest.editCredentialButton();
		Credentials firstCredentials = credentialTest.getFirstCredentials();

		credentialTest.updateCredential(firstCredentials, newUrlCredential, newUsernameCredential, newPasswordCredential); //Redirects to result page
		credentialTest.logoutHomePage();

		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
		loginTest.loginUser(username,password);

		wait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertEquals("Home", driver.getTitle());

		// VERIFY EDITED CREDENTIALS
		credentialTest.openNavTapCredential();
		credentialTest.editCredentialButton();

		Credentials newCredentials = credentialTest.getFirstCredentials();
		Assertions.assertEquals(newUrlCredential, newCredentials.getUrl());
		Assertions.assertEquals(newUsernameCredential, newCredentials.getUsername());
		//Assertions.assertEquals(newPasswordCredential, newCredentials.getPassword());
	}

//	@Order(8)
	@Test
	public void testDeleteCredential() {
		// Data to be used
		String firstName = "Michael";
		String lastName = "Jackson";
		String username = "mich123";
		String password = "jack123";

		// Signing up User, Creating a Credential and logging out.
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		RegistrationTest registrationTest = new RegistrationTest(driver);
		registrationTest.createUser(firstName, lastName, username, password);
		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
		// Initializing Selenium Object Page and logging new user in.
		LoginTest loginTest = new LoginTest(driver);
		loginTest.loginUser(username, password); // Automatically redirects to home page.
		wait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertEquals("Home", driver.getTitle());
		// CREATING CREDENTIALS
		String credentialUrl = "www.google.com";
		String credentialUsername = "Martinez";
		String credentialPassword = "Brothers";
		CredentialTest credentialTest = new CredentialTest(driver);
		credentialTest.openNavTapCredential();
		credentialTest.createCredential(credentialUrl, credentialUsername, credentialPassword);

		credentialTest.logoutHomePage();
		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
		loginTest.loginUser(username, password);

		// LOGGING IN EXISTING USER AND DELETING CREDENTIALS.
		String newUrlCredential = "www.facebook.com";
		String newUsernameCredential = "Buzz";
		String newPasswordCredential = "Aldrin";

		wait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertEquals("Home", driver.getTitle());
		credentialTest.openNavTapCredential();

		// DELETING CREDENTIALS
		credentialTest.deleteCredentialButton();
		credentialTest.logoutHomePage();

		wait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
		loginTest.loginUser(username,password);

		wait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertEquals("Home", driver.getTitle());

		// VERIFYING NOTE WAS SUCCESSFULLY DELETED
		credentialTest.openNavTapCredential();
		Assertions.assertFalse(credentialTest.firstCredentialDelete());
	}
}
