package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.exception.*;
import bg.boxerclub.boxerclubbgrestserver.model.dto.contact.ContactErrorDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.DogErrorDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.event.EventErrorDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.link.LinkErrorDto;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.UserErrorDto;
import bg.boxerclub.boxerclubbgrestserver.model.exception.AppException;
import org.jetbrains.annotations.NotNull;
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
    public GlobalExceptionHandler() {
    }

    @ExceptionHandler(UserNotUniqueException.class)
    public ResponseEntity<UserErrorDto> onUsernameNotUnique(UserNotUniqueException unue) {
        UserErrorDto userErrorDto = new UserErrorDto(unue.getUsername(), "Username is already exist!");

        return
                ResponseEntity.status(HttpStatus.CONFLICT).body(userErrorDto);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserErrorDto> onUserNotFound(UserNotFoundException unfe) {
        UserErrorDto userErrorDto = new UserErrorDto(Long.toString(unfe.getId()), "User not found!");

        return
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(userErrorDto);
    }

    @ExceptionHandler(LinkNotFoundException.class)
    public ResponseEntity<LinkErrorDto> onLinkNotFound(LinkNotFoundException lnfe) {
        LinkErrorDto linkErrorDto = new LinkErrorDto((lnfe.getId()), "Link not found!");

        return
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(linkErrorDto);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<EventErrorDto> onEventNotFound(EventNotFoundException enfe) {
        EventErrorDto eventErrorDto = new EventErrorDto((enfe.getId()), "Event not found!");

        return
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(eventErrorDto);
    }

    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<ContactErrorDto> onContactNotFound(ContactNotFoundException enfe) {
        ContactErrorDto contactErrorDto = new ContactErrorDto((enfe.getId()), "Contact not found!");

        return
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(contactErrorDto);
    }


    @ExceptionHandler(DogNotFoundException.class)
    public ResponseEntity<DogErrorDto> onDogNotFound(DogNotFoundException dnfe) {
        DogErrorDto dogErrorDto = new DogErrorDto(Long.toString(dnfe.getId()), "Dog not found!");

        return
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(dogErrorDto);
    }

    @ExceptionHandler(DogNotUniqueException.class)
    public ResponseEntity<DogErrorDto> onDogNotUnique(DogNotUniqueException dnue) {
        DogErrorDto dogErrorDto = new DogErrorDto(dnue.getRegistrationNum(), dnue.getMessage());

        return
                ResponseEntity.status(HttpStatus.CONFLICT).body(dogErrorDto);
    }

    @ExceptionHandler(ParentYoungerThanChildException.class)
    public ResponseEntity<DogErrorDto> onParentYoungerThanChild(ParentYoungerThanChildException pytce) {
        DogErrorDto dogErrorDto = new DogErrorDto(pytce.getRegistrationNum(), pytce.getMessage());

        return
                ResponseEntity.status(HttpStatus.CONFLICT).body(dogErrorDto);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status, WebRequest request) {
        AppException apiError = new AppException(
                request.getLocale().getLanguage(),
                HttpStatus.CONFLICT,
                ex.getLocalizedMessage(),
                getValidationErrors(ex.getBindingResult()));

        return handleExceptionInternal(ex, apiError, headers, status, request);

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
