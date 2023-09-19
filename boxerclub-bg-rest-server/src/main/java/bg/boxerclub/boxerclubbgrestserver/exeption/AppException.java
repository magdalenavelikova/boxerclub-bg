package bg.boxerclub.boxerclubbgrestserver.exeption;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;


public class ApExeption {
    private LocalDateTime timestamp = LocalDateTime.now();
    private String locale;
    private HttpStatus status;
    private String title;
    private String message;
    private List<FieldError> fieldErrors;

    public ApExeption() {
    }

    public ApExeption(LocalDateTime timestamp, String locale, HttpStatus status, String title, String message, List<FieldError> fieldErrors) {
        this.timestamp = timestamp;
        this.locale = locale;
        this.status = status;
        this.title = title;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public ApExeption(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public ApExeption setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getLocale() {
        return locale;
    }

    public ApExeption setLocale(String locale) {
        this.locale = locale;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ApExeption setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ApExeption setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApExeption setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public ApExeption setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
        return this;
    }
}
