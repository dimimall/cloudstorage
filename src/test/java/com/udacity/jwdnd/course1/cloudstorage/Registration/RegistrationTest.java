package com.udacity.jwdnd.course1.cloudstorage.Registration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationTest {

    private WebDriver webDriver;

//    @FindBy(id = "signup-link")
//    private WebElement singup;

    @FindBy(id = "inputFirstName")
    private WebElement firstname;

    @FindBy(id = "inputLastName")
    private WebElement lastname;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement userpassword;

    @FindBy(id = "submit")
    private WebElement submit_button;

//    @FindBy(id = "login_button")
//    private WebElement login_button;
//
    @FindBy(id = "login_link")
    private WebElement login;
//
//    @FindBy(id = "logout")
//    private WebElement logout;

    public RegistrationTest(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver,this);
    }

//    public void signUpPage(){
//        System.out.println("click signup button");
//        new WebDriverWait(this.webDriver,15).until(ExpectedConditions.elementToBeClickable(this.singup))
//                .click();
//    }
//
    public void loginPage(){
        System.out.println("click login button");
        new WebDriverWait(this.webDriver,15)
                .until(ExpectedConditions.elementToBeClickable(this.login))
                .click();
    }
//
//    public void logoutPage() {
//        System.out.println("click logout button");
//        new WebDriverWait(this.webDriver,15)
//                .until(ExpectedConditions.elementToBeClickable(this.logout))
//                .click();
//    }

    public void createUser(String firstname, String lastname, String username, String password)
    {
        this.firstname.sendKeys(firstname);
        this.lastname.sendKeys(lastname);
        this.username.sendKeys(username);
        this.userpassword.sendKeys(password);
        this.submit_button.click();
        //this.login.click();
    }

}
