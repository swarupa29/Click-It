# ClickIt
## Introduction
The project is a submission platform built using the microservices architecture in springboot. We have 6 microservices
 * Student
 * Teacher
 * Classroom
 * Assignment
 * Evaluation
 * Frontend
 Users can signup and login using the unique Id. Teachers can create classes and create assignments with details, assign it to a class and add test cases as well. Teachers can then view resuts of all students who submitted the assignments.
 Student can join classes, view assignments and submit them to view scores.
 The evaluation service will use the test cases and automatically evaulate to return a result.


## Requirements
 * Springboot
 * Postgres
 * Maven
 * JAVA

### Expected Interaction  b/w CLI/frontend and Evaluation-Microservice

**Submission**

* User picks the assignment they want to make a submission to.
* User clicks on upload/add files
* User enters/chooses the file to upload in some way
* CLI sends file to the Server (`POST` to `/upload` as specified in RESTTemplate/UploadTemplate ) which stores the file on a temp dir
* If file upload is successful user can proceed with any other required steps for submission 
* On clicking submit a `submission` (Refer RESTTemplates/SubmissionTemplate.java) object is created and sent over HTTP (POST).
* Evaluation service can now move the uploaded file from the temp directory to an actual directory.

**Auth**

* As the temp files are stored based only the userId, 1 user should only be allowed to login from 1 client to prevent user from uploading 2 conflicting files from 2 different clients.
