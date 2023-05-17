package kodlama.io.ecommerce.core.configuration.exceptions;

import jakarta.validation.ValidationException;
import kodlama.io.ecommerce.common.constants.ExceptionTypes;
import kodlama.io.ecommerce.core.exceptions.BusinessException;
import kodlama.io.ecommerce.core.utils.results.ExceptionResult;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    /*Map<String, String> validationErrors = exception.getBindingResult().getFieldErrors().stream()
            .collect(
                    java.util.stream.Collectors.toMap(
                            fieldError -> fieldError.getField(),
                            fieldError -> fieldError.getDefaultMessage()
                    )
            );*/

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST) //200
    public ExceptionResult<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception)
    {
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError: exception.getBindingResult().getFieldErrors())
        {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ExceptionResult<>(ExceptionTypes.Exception.Validation, exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ExceptionResult<Object> handleValidationException(ValidationException exception)
    {
        return new ExceptionResult<>(ExceptionTypes.Exception.Validation, exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) //422
    public ExceptionResult<Object> handleBusinessException(BusinessException exception)
    {
        return new ExceptionResult<>("", exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT) //409
    public ExceptionResult<Object> handleDataIntegrityViolation(DataIntegrityViolationException exception)
    {
        return new ExceptionResult<>(ExceptionTypes.Exception.DataIntegrityViolation, exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //500
    public ExceptionResult<Object> handleRuntimeException(RuntimeException exception)
    {
        return new ExceptionResult<>("", exception.getMessage());
    }
}