package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.nio.charset.Charset;
import java.security.Principal;
import java.util.Random;

@Controller
public class CredentialsController {

    private final CredentialService credentialService;
    private final UserService userService;
    private final NotesService notesService;
    private final FileUploadService fileUploadService;
    private final EncryptionService encryptionService;
    private Logger logger = LoggerFactory.getLogger(CredentialsController.class);

    public CredentialsController(CredentialService credentialService, UserService userService, NotesService notesService, FileUploadService fileUploadService, EncryptionService encryptionService)
    {
        this.credentialService = credentialService;
        this.userService = userService;
        this.notesService = notesService;
        this.fileUploadService = fileUploadService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/credentials")
    public String credentialController(Principal principal,@ModelAttribute("Files") Files files, @ModelAttribute("NoteForm") NoteForm noteForm, @ModelAttribute("CredentialsForm") CredentialForm credentialForm, Model model)
    {
        User user = userService.getUser(principal.getName());

        model.addAttribute("credentials",this.credentialService.getCredentails(user.getUserId()));
        model.addAttribute("notesupload",this.notesService.getNotesList(user.getUserId()));
        model.addAttribute("filesUpload", this.fileUploadService.getAllFiles(user.getUserId()));
        return "home";
    }

    @PostMapping("/credentials")
    public String postCredentialsForm(Principal principal, @ModelAttribute("Files") Files files, @ModelAttribute("NoteForm") NoteForm noteForm, @ModelAttribute("CredentialsForm") CredentialForm credentialForm, Model model)
    {
        User user = userService.getUser(principal.getName());

        if (!credentialForm.getCredentialid().equalsIgnoreCase("")){
            try {
                this.credentialService.updateCredentials(credentialForm,user.getUserId());
            }catch (Exception e){
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
            }
        }else {
            try{
                byte[] array = new byte[7];
                new Random().nextBytes(array);
                String generatedString = new String(array, Charset.forName("UTF-8"));
                credentialForm.setKey(generatedString);
                this.credentialService.createCredentials(credentialForm,user.getUserId());
            }catch (Exception e){
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
            }
        }

        model.addAttribute("credentials",this.credentialService.getCredentails(user.getUserId()));
        model.addAttribute("notesupload",this.notesService.getNotesList(user.getUserId()));
        model.addAttribute("filesUpload", this.fileUploadService.getAllFiles(user.getUserId()));

        return "home";
    }

    @GetMapping("/credentials/delete/{credentialid}")
    public String deleteCredentials(Principal principal,@PathVariable("credentialid") int credentialid, @ModelAttribute("Files") Files files, @ModelAttribute("NoteForm") NoteForm noteForm, @ModelAttribute("CredentialsForm") CredentialForm credentialForm, Model model)
    {
        User user = userService.getUser(principal.getName());

        this.credentialService.deleteCredentials(credentialid);

        model.addAttribute("credentials",this.credentialService.getCredentails(user.getUserId()));
        model.addAttribute("notesupload",this.notesService.getNotesList(user.getUserId()));
        model.addAttribute("filesUpload", this.fileUploadService.getAllFiles(user.getUserId()));

        return "home";
    }
}
