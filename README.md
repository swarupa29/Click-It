# YetAnotherEdmodo

## Requirements

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