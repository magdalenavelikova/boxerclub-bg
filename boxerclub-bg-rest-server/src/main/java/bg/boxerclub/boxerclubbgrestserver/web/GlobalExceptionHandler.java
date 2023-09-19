package bg.boxerclub.boxerclubbgrestserver.web;

import bg.boxerclub.boxerclubbgrestserver.exeption.AppException;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
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

    // 400 Request Body

    //    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers,
//                                                                  HttpStatus status,
//                                                                  WebRequest request) {
//
//        apiError.setStatus(HttpStatus.BAD_REQUEST);
//        apiError.setMessage(ex.getLocalizedMessage());
//        apiError.setLocale(request.getLocale().getLanguage());
//        apiError.setFieldErrors(getValidationErrors(ex.getBindingResult()));
//        return new ResponseEntity<>(apiError, headers, apiError.getStatus());
//    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, WebRequest request) {
        AppException apiError = new AppException(
                request.getLocale().getLanguage(),
                HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(),
                getValidationErrors(ex.getBindingResult()));

        return handleExceptionInternal(ex, apiError, headers, status, request);
        // return new ResponseEntity(apiError, headers, HttpStatus.BAD_REQUEST);
    }
    // 400 Request Body
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(value = BindException.class)
//    protected ResponseEntity<Object> handleBindException(BindException ex,
//                                                         HttpHeaders headers,
//
//                                                         WebRequest request) {
//
//        apiError.setStatus(HttpStatus.BAD_REQUEST);
//        apiError.setMessage(ex.getLocalizedMessage());
//        apiError.setLocale(request.getLocale().getLanguage());
//        apiError.setFieldErrors(getValidationErrors(ex.getBindingResult()));
//
//
//        return new ResponseEntity<>(apiError, headers, apiError.getStatus());
//    }

    // 400 request param, path variable
//    @ExceptionHandler({ConstraintViolationException.class})
//    public ResponseEntity<Object> handleConstraintViolation(
//            ConstraintViolationException ex,
//            WebRequest request,
//            Locale locale) {
//        String title = messageSource.getMessage(
//                ErrorType.CONSTRAINT_VIOLATION.getDescription(), null, request.getLocale());
//
//        List<FieldError> fieldErrors = new ArrayList<>();
//
//        ex.getConstraintViolations()
//                .forEach(constraintViolation -> fieldErrors.add(
//                        new FieldError(
//                                constraintViolation.getPropertyPath().toString(),
//                                constraintViolation.getMessage())));
//
//
//        ApiError apiError = ApiError.builder()
//                .status(HttpStatus.BAD_REQUEST)
//                .title(title)
//                .message(ex.getLocalizedMessage())
//                .locale(locale.getLanguage())
//                .fieldErrors(fieldErrors)
//                .build();
//
//        return new ResponseEntity<>(
//                apiError, new HttpHeaders(), apiError.getStatus());
//    }
//
//    // 400  request body is missing or it is unreadable
//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
//                                                                  HttpHeaders headers, HttpStatus status,
//                                                                  WebRequest request) {
//        String title = messageSource.getMessage(
//                ErrorType.HTTP_MESSAGE_NOT_READABLE.getDescription(), null, request.getLocale());
//        ApiError apiError = ApiError.builder()
//                .status(HttpStatus.BAD_REQUEST)
//                .title(title)
//                .message(ex.getMessage())
//                .locale(request.getLocale().getLanguage())
//                .build();
//        return new ResponseEntity<>(apiError, apiError.getStatus());
//    }
//
//    // 400 request is missing a parameter
//    @Override
//    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
//                                                                          HttpHeaders headers,
//                                                                          HttpStatus status,
//                                                                          WebRequest request) {
//        String errorMessage = messageSource.getMessage(
//                ErrorConstants.MISSING_SERVLET_REQUEST_PARAMETER_MESSAGE, new Object[]{ex.getParameterName()}, request.getLocale());
//
//        String title = messageSource.getMessage(
//                ErrorType.MISSING_SERVLET_REQUEST_PARAMETER.getDescription(), null, request.getLocale());
//
//        ApiError apiError = ApiError.builder()
//                .status(HttpStatus.BAD_REQUEST)
//                .title(title)
//                .message(errorMessage)
//                .locale(request.getLocale().getLanguage())
//                .build();
//
//        return new ResponseEntity<>(apiError, headers, apiError.getStatus());
//    }
//
//    // 400 method argument is not the expected type
//    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
//    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
//                                                                   WebRequest request,
//                                                                   HttpHeaders headers,
//                                                                   Locale locale) {
//        String errorMessage = messageSource.getMessage(
//                ErrorConstants.METHOD_ARGUMENT_TYPE_MISMATCH_MESSAGE,
//                new Object[]{ex.getName(), Objects.requireNonNull(ex.getRequiredType()).getName()}
//                , request.getLocale());
//
//        String title = messageSource.getMessage(
//                ErrorType.METHOD_ARGUMENT_TYPE_MISMATCH.getDescription(), null, locale);
//
//        ApiError apiError = ApiError.builder()
//                .status(HttpStatus.BAD_REQUEST)
//                .title(title)
//                .message(errorMessage)
//                .locale(locale.getLanguage())
//                .build();
//
//        return new ResponseEntity<>(apiError, headers, apiError.getStatus());
//    }
//
//    // 404 No Handler not found
//    @Override
//    protected ResponseEntity<Object> handleNoHandlerFoundException(
//            NoHandlerFoundException ex,
//            HttpHeaders headers,
//            HttpStatus status,
//            WebRequest request) {
//
//        String title = messageSource.getMessage(
//                ErrorType.RESOURCE_NOT_FOUND.getDescription(), null, request.getLocale());
//
//        String errorMessage = messageSource.getMessage(
//                ErrorConstants.NO_HANDLER_FOUND_MESSAGE,
//                new Object[]{ex.getHttpMethod(), ex.getRequestURL()},
//                request.getLocale());
//
//        ApiError apiError = ApiError.builder()
//                .status(HttpStatus.BAD_REQUEST)
//                .title(title)
//                .message(errorMessage)
//                .locale(request.getLocale().getLanguage())
//                .build();
//
//        return new ResponseEntity<>(apiError, headers, apiError.getStatus());
//
//    }
//
//    // 404 Resource Not Found
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public final ResponseEntity<Object> handleResourceNotFound(
//            ResourceNotFoundException ex, Locale locale) {
//        String title = messageSource.getMessage(
//                ErrorType.RESOURCE_NOT_FOUND.getDescription(), null, locale);
//
//        String errorMessage = messageSource.getMessage(ex.getMessage(), ex.getArgs(), locale);
//        ApiError apiError = ApiError.builder()
//                .status(HttpStatus.NOT_FOUND)
//                .title(title)
//                .message(errorMessage)
//                .locale(locale.getLanguage())
//                .build();
//        return new ResponseEntity<>(apiError, apiError.getStatus());
//    }
//
//    // 415
//    @Override
//    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
//            HttpMediaTypeNotSupportedException ex,
//            HttpHeaders headers,
//            HttpStatus status,
//            WebRequest request) {
//
//        String title = messageSource.getMessage(
//                ErrorType.HTTP_MEDIA_TYPE_NOT_SUPPORTED.getDescription(), null, request.getLocale());
//
//        String supportedMediaTypes = ex.getSupportedMediaTypes().stream()
//                .map(String::valueOf)
//                .collect(Collectors.joining(", "));
//
//        String errorMessage = messageSource.getMessage(
//                ErrorConstants.HTTP_MEDIA_TYPE_NOT_SUPPORTED_MESSAGE,
//                new Object[]{ex.getContentType(), supportedMediaTypes}
//                , request.getLocale());
//
//        ApiError apiError = ApiError.builder()
//                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//                .title(title)
//                .message(errorMessage)
//                .locale(request.getLocale().getLanguage())
//                .build();
//
//        return new ResponseEntity<>(apiError, headers, apiError.getStatus());
//    }
//
//
//    // 500
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleAll(Exception ex,
//                                            WebRequest request,
//                                            Locale locale) {
//        String errorMessage = messageSource.getMessage(ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE, null, locale);
//        ApiError apiError = ApiError.builder()
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .message(errorMessage)
//                .locale(locale.getLanguage())
//                .build();
//        return new ResponseEntity<>(apiError, apiError.getStatus());
//    }

    //    private List<FieldError> getValidationErrors(BindingResult bindingResult) {
//        List<FieldError> fieldErrors = new ArrayList<>();
//
//        bindingResult.getFieldErrors()
//                .forEach(fieldError -> fieldErrors.add(new FieldError(fieldError.getField(),
//                        fieldError.getDefaultMessage())));
//
//        bindingResult.getGlobalErrors()
//                .forEach(objectError -> fieldErrors.add(new FieldError(objectError.getObjectName(),
//                        objectError.getDefaultMessage())));
//
//        return fieldErrors;
//    }
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
