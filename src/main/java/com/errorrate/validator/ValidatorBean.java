package com.errorrate.validator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@ManagedBean
@ViewScoped
public class ValidatorBean {
    
    private Part file;
    private String result;
    private ExtractionValidator validator;
    
    public ValidatorBean() {
        validator = new ExtractionValidator();
    }
    
    public String validate() {
        try {
            File tempFile = File.createTempFile("upload", ".xhtml");
            try (InputStream input = file.getInputStream()) {
                Files.copy(input, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            
            validator.validateXHTML(tempFile);
            double errorRate = validator.calculateErrorRate("XHTML");
            result = String.format("XHTML Error Rate: %.2f%%", errorRate * 100);
            
            tempFile.delete();
        } catch (IOException e) {
            result = "Error processing file: " + e.getMessage();
        }
        return null;
    }
    
    public Part getFile() {
        return file;
    }
    
    public void setFile(Part file) {
        this.file = file;
    }
    
    public String getResult() {
        return result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
} 