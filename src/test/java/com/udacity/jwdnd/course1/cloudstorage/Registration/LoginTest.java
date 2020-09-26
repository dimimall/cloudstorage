package com.udacity.jwdnd.course1.cloudstorage.Registration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginTest {

    private WebDriver webDriver;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement userpassword;

    @FindBy(id = "submit")
    private WebElement submit_button;

    public LoginTest(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver,this);
    }

    public void loginUser(String username, String password)
    {
        this.username.sendKeys(username);
        this.userpassword.sendKeys(password);
        this.submit_button.click();
    }
}
