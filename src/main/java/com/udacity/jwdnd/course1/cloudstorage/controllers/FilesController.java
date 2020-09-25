package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileUploadService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;

@Controller
public class FilesController {

    private final FileUploadService fileUploadService;
    private final UserService userService;
    private final NotesService notesService;
    private final CredentialService credentialService;

    public FilesController(FileUploadService fileUploadService, UserService userService, NotesService notesService, CredentialService credentialService){
        this.fileUploadService = fileUploadService;
        this.notesService = notesService;
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @GetMapping("/files")
    public String fileController(Principal principal, @ModelAttribute("Files") Files files, Notes notes, Credentials credentials, Model model) {
        User user = userService.getUser(principal.getName());

        model.addAttribute("filesUpload", this.fileUploadService.getAllFiles(user.getUserId()));
        model.addAttribute("notesupload", this.notesService.getNotesList(user.getUserId()));
        model.addAttribute("credentials",this.credentialService.getCredentails(user.getUserId()));

        return "home";
    }

    @PostMapping("/files")
    public String postFilesForm(Principal principal, @RequestParam("file") MultipartFile file, Notes notes, @ModelAttribute("Files") Files files, Credentials credentials, Model model) {
        String uploadError = null;
        User user = userService.getUser(principal.getName());

        if (uploadError == null) {
            int rowsAdded = this.fileUploadService.addFile(file,user.getUserId());
            if (rowsAdded < 0) {
                uploadError = "There was an error upload file. Please try again.";
            }
        }

        if (uploadError == null) {
            model.addAttribute("uploadSuccess",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
        } else {
            model.addAttribute("uploadError",
                    "You cannot upload file '" + uploadError + "'");
        }

        model.addAttribute("filesUpload", this.fileUploadService.getAllFiles(user.getUserId()));
        model.addAttribute("notesupload", this.notesService.getNotesList(user.getUserId()));
        model.addAttribute("credentials",this.credentialService.getCredentails(user.getUserId()));

        return "home";
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(Principal principal, @RequestParam String fileName){
        User user = userService.getUser(principal.getName());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(this.fileUploadService.getFileByName(fileName,user.getUserId()).getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + this.fileUploadService.getFileByName(fileName,user.getUserId()).getFileName() + "\"")
                .body(new ByteArrayResource(this.fileUploadService.getFileByName(fileName,user.getUserId()).getFileData()));
    }

    @GetMapping("/files/delete/{fileid}")
    public String deleteFile(Principal principal, @PathVariable("fileid") int fileid, Notes notes, @ModelAttribute("Files") Files files, Credentials credentials, Model model){
        User user = userService.getUser(principal.getName());

        this.fileUploadService.deleteFile(fileid);

        model.addAttribute("filesUpload", this.fileUploadService.getAllFiles(user.getUserId()));
        model.addAttribute("notesupload", this.notesService.getNotesList(user.getUserId()));
        model.addAttribute("credentials",this.credentialService.getCredentails(user.getUserId()));

        return "home";
    }
}
