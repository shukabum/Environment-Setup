# Install Java 11:
  1. Download OpenJDK 11 from: https://adoptium.net/temurin/releases/?version=11
  2. Choose Windows x64 MSI installer
  3. Run the installer
  4. During installation, make sure "Add to PATH" is selected
# Install Maven:
  1. Download Maven from: https://maven.apache.org/download.cgi
  2. Get the Binary zip archive (apache-maven-3.9.6-bin.zip)
  3. Extract it to C:\Program Files\Apache\maven
# Set Environment Variables:
  1. Here are the exact steps:
  2. Open System Properties:
  3. Press Windows + R
  4. Type sysdm.cpl and press Enter
  5. Go to "Advanced" tab
  6. Click "Environment Variables"
# Add System Variables
   ```
   JAVA_HOME = C:\Program Files\Eclipse Adoptium\jdk-11.0.xx.xx-hotspot
   M2_HOME = C:\Program Files\Apache\maven
```
# Edit PATH:
  1. Find the Path variable under System Variables
  2. Click Edit
       
    %JAVA_HOME%\bin
    %M2_HOME%\bin
# To deploy and test:
  1. Make sure you have Java 11 installed (as specified in pom.xml)
  2. Install Apache Tomcat (version 9.x recommended for Java EE 8)
  3. Build the project with Maven
   ```
      mvn clean package
```

4. Deploy the generated WAR file (from target directory) to Tomcat
5. Access the application at: http://localhost:8080/xhtml-extraction-validator/
6. The application will allow you to:
   ```
    Upload XHTML files for validation
    View error rates for XHTML validation
    The existing "ExtractionValidator" class is integrated to perform the validation
   ```
