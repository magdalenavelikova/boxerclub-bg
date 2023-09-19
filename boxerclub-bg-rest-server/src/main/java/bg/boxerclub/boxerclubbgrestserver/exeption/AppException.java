package bg.boxerclub.boxerclubbgrestserver.exeption;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;


public class AppException {
    private LocalDateTime timestamp = LocalDateTime.now();
    private String locale;
    private HttpStatus status;

    private String message;
    private Map<String, String> fieldErrors;

    public AppException() {
    }

    public AppException(String locale, HttpStatus status, String message, Map<String, String> fieldErrors) {

        this.locale = locale;
        this.status = status;

        this.message = message;
        this.fieldErrors = fieldErrors;
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public AppException setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getLocale() {
        return locale;
    }

    public AppException setLocale(String locale) {
        this.locale = locale;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public AppException setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }


    public String getMessage() {
        return message;
    }

    public AppException setMessage(String message) {
        this.message = message;
        return this;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public AppException setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
        return this;
    }
}
