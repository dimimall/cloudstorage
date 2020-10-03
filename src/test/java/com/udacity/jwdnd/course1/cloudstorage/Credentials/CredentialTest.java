package com.udacity.jwdnd.course1.cloudstorage.Credentials;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialTest {

    public WebDriver webDriver;
    public JavascriptExecutor js;
    public WebDriverWait webDriverWait;

    @FindBy(id = "logout")
    public WebElement logout;

    @FindBy(id = "credential-url")
    public WebElement credential_url;

    @FindBy(id = "credential-username")
    public WebElement credential_username;

    @FindBy(id = "credential-password")
    public WebElement credential_password;

    @FindBy(id = "credential_submit")
    public WebElement credential_submit;

    @FindBy(id = "edit-button")
    public WebElement edit_button;

    @FindBy(id = "delete-button")
    public WebElement delete_button;

    @FindBy(id = "nav-credentials-tab")
    public WebElement nav_credentials_tab;

    @FindBy(id = "add-credential")
    public WebElement add_credential;

    @FindBy(id = "credentialModalLabel")
    public WebElement credentialModalLabel;

    public CredentialTest(WebDriver webDriver)
    {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
        webDriverWait = new WebDriverWait(this.webDriver,15);
        this.js = (JavascriptExecutor) webDriver;
    }

    public void logoutHomePage()
    {
        System.out.println("click logout button");
        try {
            webDriverWait.until(ExpectedConditions.elementToBeClickable(logout));
            this.js.executeScript("arguments[0].click();",logout);
        }catch (StaleElementReferenceException e){
            WebElement logoutBtn = webDriver.findElement(By.id("logout"));
            this.js.executeScript("arguments[0].click();", logoutBtn);
        }
    }

    public void openNavTapCredential()
    {
        System.out.println("nav credential tap");
        webDriverWait.until(ExpectedConditions.elementToBeClickable(nav_credentials_tab));
        this.js.executeScript("arguments[0].click();",nav_credentials_tab);
    }

    public void submitCredentialButton()
    {
        System.out.println("click submit credential button");
        webDriverWait.until(ExpectedConditions.elementToBeClickable(credential_submit));
        this.js.executeScript("arguments[0].click();",credential_submit);
    }

    public void editCredentialButton()
    {
        System.out.println("click edit credential button");
        webDriverWait.until(ExpectedConditions.visibilityOf(edit_button));
        this.js.executeScript("arguments[0].click();",edit_button);
    }

    public void waitModeCredentiallPage() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(credentialModalLabel));
    }

    public void deleteCredentialButton(){
        System.out.println("click delete note button");
        webDriverWait.until(ExpectedConditions.elementToBeClickable(delete_button));
        this.js.executeScript("arguments[0].click();",delete_button);
    }

    public boolean firstCredentialDelete(){
        try{
            webDriver.findElement(By.id("credential-url-row"));
            webDriver.findElement(By.id("credential-username-row"));
            webDriver.findElement(By.id("credential-password-row"));
            return true;
        }catch (NoSuchElementException e){
            return false;
        }
    }

    public void createCredential(String url, String username, String password)
    {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(add_credential));
        js.executeScript("arguments[0].click();",add_credential);
        js.executeScript("arguments[0].value='"+url+"';",this.credential_url);
        js.executeScript("arguments[0].value='"+username+"';",this.credential_username);
        js.executeScript("arguments[0].value='"+password+"';",this.credential_password);
        js.executeScript("arguments[0].click();",credential_submit);
    }

    public void updateCredential(Credentials credentials, String url, String username, String password)
    {
        credentials.setUrl(url);
        credentials.setUsername(username);
        credentials.setPassword(password);

        js.executeScript("arguments[0].click();",edit_button);

        this.credential_url.clear();
        this.credential_username.clear();
        this.credential_password.clear();

        js.executeScript("arguments[0].value='"+credentials.getUrl()+"';",this.credential_url);
        js.executeScript("arguments[0].value='"+credentials.getUsername()+"';",this.credential_username);
        js.executeScript("arguments[0].value='"+credentials.getPassword()+"';",this.credential_password);
        js.executeScript("arguments[0].click();",credential_submit);
    }

    private String getValueFromInput(String inputId){
        WebElement input = webDriver.findElement(By.id(inputId));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(input));
        js.executeScript("arguments[0].click();", input);
        return input.getAttribute("value");
    }

    public Credentials getFirstCredentials()
    {
        String url = getValueFromInput("credential-url");
        String username = getValueFromInput("credential-username");
        String password = getValueFromInput("credential-password");
        return new Credentials(null,url,username,null,password,null);
    }
}
