package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
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
    private final FilesMapper filesMapper;
    private final EncryptionService encryptionService;

    public FilesController(FileUploadService fileUploadService, UserService userService, NotesService notesService, CredentialService credentialService, FilesMapper filesMapper, EncryptionService encryptionService){
        this.fileUploadService = fileUploadService;
        this.notesService = notesService;
        this.userService = userService;
        this.encryptionService = encryptionService;
        this.credentialService = credentialService;
        this.filesMapper = filesMapper;
    }

    @GetMapping("/files")
    public String fileController(Principal principal, @ModelAttribute("Files") Files files, @ModelAttribute("NoteForm") NoteForm noteForm, @ModelAttribute("CredentialsForm") CredentialForm credentialForm, Model model) {
        User user = userService.getUser(principal.getName());

        model.addAttribute("filesUpload", this.fileUploadService.getAllFiles(user.getUserId()));
        model.addAttribute("notesupload", this.notesService.getNotesList(user.getUserId()));
        model.addAttribute("credentials",this.credentialService.getCredentails(user.getUserId()));
        model.addAttribute("encryptionService", this.encryptionService);
        return "home";
    }

    @PostMapping("/files")
    public String postFilesForm(Principal principal, @RequestParam("file") MultipartFile file, @ModelAttribute("NoteForm") NoteForm noteForm, @ModelAttribute("Files") Files files, @ModelAttribute("CredentialsForm") CredentialForm credentialForm, Model model) {
        String uploadError = null;
        User user = userService.getUser(principal.getName());
        int rowsAdded =0;

        if (file.isEmpty()) {
            uploadError = "The file is empty. '"+uploadError+" '";
            model.addAttribute("uploadError", "You cannot upload empty file ");
        }

        if (uploadError == null) {
            if (!file.isEmpty()){
                if (filesMapper.getFileName(file.getOriginalFilename()) != null)
                    model.addAttribute("uploadError", "You cannot upload same file name ");
                else
                    rowsAdded = this.fileUploadService.addFile(file,user.getUserId());
                if (rowsAdded < 0) {
                    uploadError = "There was an error upload file. Please try again.";
                }
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
        model.addAttribute("encryptionService", this.encryptionService);

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
    public String deleteFile(Principal principal, @PathVariable("fileid") int fileid, @ModelAttribute("NoteForm") NoteForm noteForm, @ModelAttribute("Files") Files files, @ModelAttribute("CredentialsForm") CredentialForm credentialForm, Model model){
        User user = userService.getUser(principal.getName());

        String uploadError = null;

        if (uploadError == null) {
               int rowsAdded = this.fileUploadService.deleteFile(fileid);
            if (rowsAdded < 0) {
                uploadError = "There was an error upload file. Please try again.";
            }
        }

        if (uploadError == null) {
            model.addAttribute("uploadSuccess",
                    "You successfully delete ");
        } else {
            model.addAttribute("uploadError",
                    "You cannot delete file '" + uploadError + "'");
        }

        model.addAttribute("filesUpload", this.fileUploadService.getAllFiles(user.getUserId()));
        model.addAttribute("notesupload", this.notesService.getNotesList(user.getUserId()));
        model.addAttribute("credentials",this.credentialService.getCredentails(user.getUserId()));
        model.addAttribute("encryptionService", this.encryptionService);

        return "home";
    }
}
