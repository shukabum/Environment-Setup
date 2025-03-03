package com.errorrate.validator;

import org.junit.Before;
import org.junit.Test;
import javax.faces.bean.ManagedBean;
import javax.persistence.Entity;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static org.junit.Assert.*;

public class ExtractionValidatorTest {
    
    private ExtractionValidator validator;
    private File validXhtml;
    private File invalidXhtml;
    
    @Before
    public void setUp() throws IOException {
        validator = new ExtractionValidator();
        
        // Create test XHTML files
        validXhtml = File.createTempFile("valid", ".xhtml");
        try (FileWriter writer = new FileWriter(validXhtml)) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" " +
                        "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
                        "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head><title>Test</title></head>\n" +
                        "<body><p>Valid XHTML</p></body>\n" +
                        "</html>");
        }
        
        invalidXhtml = File.createTempFile("invalid", ".xhtml");
        try (FileWriter writer = new FileWriter(invalidXhtml)) {
            writer.write("<html>\n" +
                        "<head><title>Test</title></head>\n" +
                        "<body><p>Invalid XHTML</p>\n" +  // Missing closing tags
                        "</html>");
        }
    }
    
    @Test
    public void testXHTMLValidation() throws IOException {
        validator.validateXHTML(validXhtml);
        validator.validateXHTML(invalidXhtml);
        
        assertEquals(0.5, validator.calculateErrorRate("XHTML"), 0.01);
    }
    
    @Test
    public void testManagedBeanValidation() {
        validator.validateManagedBean(ValidManagedBean.class);
        validator.validateManagedBean(InvalidManagedBean.class);
        
        assertEquals(0.5, validator.calculateErrorRate("ManagedBean"), 0.01);
    }
    
    @Test
    public void testEntityValidation() {
        validator.validateEntity(ValidEntity.class);
        validator.validateEntity(InvalidEntity.class);
        
        assertEquals(0.5, validator.calculateErrorRate("Entity"), 0.01);
    }
    
    @ManagedBean
    private static class ValidManagedBean {}
    
    private static class InvalidManagedBean {}
    
    @Entity
    private static class ValidEntity {}
    
    private static class InvalidEntity {}
} 