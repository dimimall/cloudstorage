package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.ErrorMessage.StorageException;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

@Service
public class CredentialService {

    private final CredentialsMapper credentialsMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialsMapper credentialsMapper, EncryptionService encryptionService){
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating Credentials Service bean");
    }

    public int createCredentials(CredentialForm credentialForm, Integer userid){
        if (credentialForm == null){
            throw new StorageException("Failed to store empty credentials");
        }
        String key = encryptionService.genrateKey();
        String encryptionPass =  encryptionService.encryptValue(credentialForm.getCredentials_password(),key);
        Credentials credentials = new Credentials();
        credentials.setCredentialId(null);
        credentials.setUserId(userid);
        credentials.setUrl(credentialForm.getCredential_url());
        credentials.setPassword(encryptionPass);
        credentials.setKey(key);

        return this.credentialsMapper.insert(credentials);
    }

    public int deleteCredentials(int credentialid){
        return this.credentialsMapper.delete(credentialid);
    }

    public int updateCredentials(CredentialForm credentialForm,Integer userid){

        String key = encryptionService.genrateKey();
        String encryptionPass =  encryptionService.encryptValue(credentialForm.getCredentials_password(),key);

        Credentials credentials = new Credentials();
        credentials.setCredentialId(Integer.parseInt(credentialForm.getCredentialid()));
        credentials.setUserId(userid);
        credentials.setUrl(credentials.getUrl());
        credentials.setPassword(encryptionPass);
        credentials.setKey(key);

        return this.credentialsMapper.update(credentials);
    }

    public List<Credentials> getCredentails(int userid){
        return this.credentialsMapper.getCredentials(userid);
    }

    public Credentials getCredentialsByKey(int credentialid,int userid){
        Credentials credentials = this.credentialsMapper.getCredentialsById(credentialid,userid);
        credentials.setPassword(encryptionService.decryptValue(credentials.getPassword(),credentials.getKey()));
        return credentials;
    }

    public Boolean existCredential(int credentialid,int userid){
        Credentials credentials = this.credentialsMapper.getCredentialsById(credentialid,userid);
        if (credentials != null){
            return true;
        }
        else {
            return false;
        }
    }
}
