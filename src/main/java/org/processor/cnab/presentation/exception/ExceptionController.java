package org.processor.cnab.presentation.exception;


import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CouldNotStoreException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String  handleNoUserFindOnSession(CouldNotStoreException ex) {
        return "Could not store file.";
    }
//GAMBIARA
    @ExceptionHandler(JobExecutionAlreadyRunningException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public String jobOnExecution(JobExecutionAlreadyRunningException ex) {
        return "Job is running.";
    }


    @ExceptionHandler(JobInstanceAlreadyCompleteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleNoUserFindOnSession(JobInstanceAlreadyCompleteException ex) {
        return "Job already completed previously.";
    }

}
