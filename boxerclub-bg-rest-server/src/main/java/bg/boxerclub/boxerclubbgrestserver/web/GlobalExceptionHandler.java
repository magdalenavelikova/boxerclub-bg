package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.exeption.*;
import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.DogErrorDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.UserErrorDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    private final MessageSource messageSource;
    private final AppException apiError = new AppException();

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @ExceptionHandler(UserNotUniqueException.class)
    public ResponseEntity<UserErrorDto> onUsernameNotUnique(UserNotUniqueException unue) {
        UserErrorDto userErrorDto = new UserErrorDto(unue.getUsername(), "Username is already exist!");

        return
                ResponseEntity.status(HttpStatus.FORBIDDEN).body(userErrorDto);
    }

    @ExceptionHandler(DogNotFoundException.class)
    public ResponseEntity<DogErrorDto> onDogNotFound(DogNotFoundException dnfe) {
        DogErrorDto dogErrorDto = new DogErrorDto(Long.toString(dnfe.getId()), "Dog not found");

        return
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(dogErrorDto);
    }

    @ExceptionHandler(DogNotUniqueException.class)
    public ResponseEntity<DogErrorDto> onDogNotUnique(DogNotUniqueException dnue) {
        DogErrorDto dogErrorDto = new DogErrorDto(dnue.getRegistrationNum(), dnue.getMessage());

        return
                ResponseEntity.status(HttpStatus.FORBIDDEN).body(dogErrorDto);
    }

    @ExceptionHandler(ParentYoungerThanChildException.class)
    public ResponseEntity<DogErrorDto> onParentYoungerThanChild(ParentYoungerThanChildException pytce) {
        DogErrorDto dogErrorDto = new DogErrorDto(pytce.getRegistrationNum(), pytce.getMessage());

        return
                ResponseEntity.status(HttpStatus.FORBIDDEN).body(dogErrorDto);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status, WebRequest request) {
        AppException apiError = new AppException(
                request.getLocale().getLanguage(),
                HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(),
                getValidationErrors(ex.getBindingResult()));

        return handleExceptionInternal(ex, apiError, headers, status, request);
        // return new ResponseEntity(apiError, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File too large!");
    }

    private Map<String, String> getValidationErrors(BindingResult bindingResult) {
        Map<String, String> fieldErrors = new HashMap<>();

        bindingResult.getFieldErrors()
                .forEach(fieldError -> fieldErrors.put(fieldError.getField(),
                        fieldError.getDefaultMessage()));

        bindingResult.getGlobalErrors()
                .forEach(objectError -> fieldErrors.put(objectError.getObjectName(),
                        objectError.getDefaultMessage()));

        return fieldErrors;
    }


}
