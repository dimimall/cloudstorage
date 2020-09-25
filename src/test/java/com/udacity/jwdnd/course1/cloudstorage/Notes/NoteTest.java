package com.udacity.jwdnd.course1.cloudstorage.Notes;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NoteTest {
    public WebDriver webDriver;
    public JavascriptExecutor js;
    public WebDriverWait webDriverWait;

    @FindBy(id = "logout")
    public WebElement logout;

    @FindBy(id = "note-title")
    public WebElement notetitle;

    @FindBy(id = "note-description")
    public WebElement notedescription;

    @FindBy(id = "note_submit")
    public WebElement note_submit;

    @FindBy(id = "edit_button")
    public WebElement edit_button;

    @FindBy(id = "delete_button")
    public WebElement delete_button;

    @FindBy(id = "nav-notes-tab")
    public WebElement nav_notes_tab;

    @FindBy(id = "add_note_button")
    public WebElement add_note_button;

    @FindBy(id = "noteModalLabel")
    public WebElement noteModalLabel;

    public NoteTest(WebDriver webDriver)
    {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
        webDriverWait = new WebDriverWait(this.webDriver,15);
        this.js = (JavascriptExecutor) webDriver;
    }

    public void logoutHomePage()
    {
        System.out.println("click logout button");
        webDriverWait.until(ExpectedConditions.elementToBeClickable(logout));
        this.js.executeScript("arguments[0].click();",logout);
    }

    public void openNavTapNote()
    {
        System.out.println("nav note tap");
        webDriverWait.until(ExpectedConditions.elementToBeClickable(nav_notes_tab));
        this.js.executeScript("arguments[0].click();",nav_notes_tab);
    }

    public void submitNoteButton()
    {
        System.out.println("click submit note button");
        webDriverWait.until(ExpectedConditions.elementToBeClickable(note_submit));
        this.js.executeScript("arguments[0].click();",note_submit);
    }

    public void editNoteButton()
    {
        System.out.println("click edit note button");
        webDriverWait.until(ExpectedConditions.elementToBeClickable(edit_button)).click();
        this.js.executeScript("arguments[0].click();",edit_button);
    }

    public void waitNoteModelPage() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(noteModalLabel));
    }

    public void deleteNoteButton(){
        System.out.println("click delete note button");
        webDriverWait.until(ExpectedConditions.elementToBeClickable(delete_button));
        this.js.executeScript("arguments[0].click();",delete_button);
    }

    public void createNote(String notetitle, String notedescription)
    {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(add_note_button));
        js.executeScript("arguments[0].click();",add_note_button);
        waitNoteModelPage();
        js.executeScript("arguments[0].value='"+notetitle+"';",this.notetitle);
        js.executeScript("arguments[0].value='"+notedescription+"';",this.notedescription);
        js.executeScript("arguments[0].click();",note_submit);
    }

    public void updateNote(Notes notes, String notetitle, String notedescription)
    {
        notes.setNoteTitle(notetitle);
        notes.setNoteDescription(notedescription);

        js.executeScript("arguments[0].click();",edit_button);

        this.notetitle.clear();
        this.notedescription.clear();

        js.executeScript("arguments[0].value='"+notes.getNoteTitle()+"';",this.notetitle);
        js.executeScript("arguments[0].value='"+notes.getNoteDescription()+"';",this.notedescription);
        js.executeScript("arguments[0].click();",note_submit);
    }

    public Notes getFirstNote(){
        Notes notes = new Notes();
        String notetitle = (String) js.executeScript("return arguments[0].value",this.notetitle);
        String notedescription = (String) js.executeScript("return arguments[0].value",this.notedescription);
        notes.setNoteTitle(notetitle);
        notes.setNoteDescription(notedescription);
        return notes;
    }
}
