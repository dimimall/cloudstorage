package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileUploadService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final FileUploadService fileUploadService;
    private final NotesService notesService;
    private final CredentialService credentialService;
    private final UserService userService;

    public HomeController(FileUploadService fileUploadService, NotesService notesService, CredentialService credentialService, UserService userService){
        this.fileUploadService = fileUploadService;
        this.notesService = notesService;
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @GetMapping
    public String homePage(Principal principal, @ModelAttribute("Files") Files files, @ModelAttribute("NoteForm") NoteForm noteForm, @ModelAttribute("CredentialsForm") CredentialForm credentialForm, Model model){
        User user = userService.getUser(principal.getName());
        System.out.println("current user: "+user.getUsername());

        model.addAttribute("filesUpload",this.fileUploadService.getAllFiles(user.getUserId()));
        model.addAttribute("notesupload",this.notesService.getNotesList(user.getUserId()));
        model.addAttribute("credentials",this.credentialService.getCredentails(user.getUserId()));
        return "home";
    }
}
