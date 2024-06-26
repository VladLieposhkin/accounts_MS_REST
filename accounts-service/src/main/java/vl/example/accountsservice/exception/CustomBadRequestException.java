package vl.example.accountsservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomBadRequestException extends RuntimeException {

    List<String> errors = new ArrayList<>();

    public CustomBadRequestException(List<String> errors) {
        this.errors = errors;
    }

    public CustomBadRequestException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public CustomBadRequestException(String message, Throwable cause, List<String> errors) {
        super(message, cause);
        this.errors = errors;
    }

    public CustomBadRequestException(Throwable cause, List<String> errors) {
        super(cause);
        this.errors = errors;
    }

    public CustomBadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<String> errors) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errors = errors;
    }
}
