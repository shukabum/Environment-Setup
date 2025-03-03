package com.errorrate.validator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import javax.faces.bean.ManagedBean;
import javax.persistence.Entity;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class ExtractionValidator {
    
    private List<ValidationResult> results;
    
    public ExtractionValidator() {
        this.results = new ArrayList<>();
    }
    
    public void validateXHTML(File file) throws IOException {
        Document doc = Jsoup.parse(file, "UTF-8");
        // Validate XHTML structure and collect errors
        boolean isValid = doc.outputSettings().syntax() == Document.OutputSettings.Syntax.xml;
        results.add(new ValidationResult("XHTML", file.getName(), isValid));
    }
    
    public void validateManagedBean(Class<?> clazz) {
        boolean isManagedBean = clazz.isAnnotationPresent(ManagedBean.class);
        results.add(new ValidationResult("ManagedBean", clazz.getName(), isManagedBean));
    }
    
    public void validateEntity(Class<?> clazz) {
        boolean isEntity = clazz.isAnnotationPresent(Entity.class);
        results.add(new ValidationResult("Entity", clazz.getName(), isEntity));
    }
    
    public List<ValidationResult> getResults() {
        return results;
    }
    
    public double calculateErrorRate(String type) {
        long totalChecks = results.stream()
            .filter(r -> r.getType().equals(type))
            .count();
            
        if (totalChecks == 0) return 0.0;
        
        long errors = results.stream()
            .filter(r -> r.getType().equals(type) && !r.isValid())
            .count();
            
        return (double) errors / totalChecks;
    }
    
    public static class ValidationResult {
        private final String type;
        private final String target;
        private final boolean valid;
        
        public ValidationResult(String type, String target, boolean valid) {
            this.type = type;
            this.target = target;
            this.valid = valid;
        }
        
        public String getType() { return type; }
        public String getTarget() { return target; }
        public boolean isValid() { return valid; }
    }
} 