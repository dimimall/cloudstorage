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

	@Order(1)
	@Test
	public void getLoginPage() {
		driver.get(baseURL + "/home");
		Assertions.assertFalse(driver.getTitle().equals("Home"));

		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Order(2)
	@Test
	public void testRegisterPage()
	{
		String firstName = "Dimitra";
		String lastName = "Malliarou";
		String username = "dimimall";
		String password = "10011982abc";

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

//		CredentialTest credentialTest = new CredentialTest(driver);
//		credentialTest.logoutHomePage();
//		wait.until(ExpectedConditions.titleContains("Login"));
//		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(baseURL + "/home");
		Assertions.assertFalse(driver.getTitle().equals("Home"));
	}

	@Order(3)
	@Test
	public void testCreateNotes()
	{
		String firstName = "Dimitra";
		String lastName = "Malliarou";
		String username = "dimitra";
		String password = "10011982Abc";

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

		String noteTitle = "Test Note";
		String noteDescription = "test test test";

		NoteTest noteTest = new NoteTest(driver);
		noteTest.openNavTapNote();
		noteTest.createNote(noteTitle,noteDescription);
		//noteTest.editNoteButton();
		noteTest.waitNoteModelPage();

		Notes notes = noteTest.getFirstNote();
		Assertions.assertEquals(noteTitle,notes.getNoteTitle());
		Assertions.assertEquals(noteDescription,notes.getNoteDescription());
	}

	@Order(4)
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
		//credentialTest.editCredentialButton();
		credentialTest.waitModeCredentiallPage();

		Credentials credentials = credentialTest.getFirstCredentials();
		Assertions.assertEquals(url,credentials.getUrl());
		Assertions.assertEquals(username1,credentials.getUsername());
		Assertions.assertEquals(password1,credentials.getPassword());
	}
}
