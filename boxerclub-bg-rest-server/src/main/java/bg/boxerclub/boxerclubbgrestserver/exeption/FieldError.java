package bg.boxerclub.boxerclubbgrestserver.exeption;

public class FieldError {


    private String field;
    private String message;

    public FieldError() {
    }

    public FieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public FieldError setField(String field) {
        this.field = field;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public FieldError setMessage(String message) {
        this.message = message;
        return this;
    }
}
