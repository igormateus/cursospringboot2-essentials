package curso.springboot2.springboot2essential.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import curso.springboot2.springboot2essential.exception.BadRequestException;
import curso.springboot2.springboot2essential.exception.BadRequestExceptionDetails;
import curso.springboot2.springboot2essential.exception.ValidationExceptionDetails;
import lombok.extern.log4j.Log4j2;

/**
 * Classe que indica ao Spring o que deve ser feito quando um erro acontecer
 */
@ControllerAdvice // Transforma em spring bean e avisa aos controllers que podem usar o que ha nessa classe
                  // Como uma flag
@Log4j2  
public class RestExceptionHandler {
    
    @ExceptionHandler(BadRequestException.class) // Flag indicando que o método abaixo deve ser usado
                                                 // em caso de erro BadRequest
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(
            BadRequestException badRequestException) {
        return new ResponseEntity<>(
            BadRequestExceptionDetails.builder()
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .title("Bad Request Exception, Check the Documentation")
                    .details(badRequestException.getMessage())
                    .developerMessage(badRequestException.getClass().getName())
                    .build(), 
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> handlerMethodArgumentNotValidException (
            MethodArgumentNotValidException methodArgumentNotValidException) {

        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        // Pega inicialmente os campos de erro em uma lista
        
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String FieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
        
        return new ResponseEntity<>(
            ValidationExceptionDetails.builder()
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .title("Bad Request Exception, Invalid Fields")
                    .details("Check the field(s) error(s)") //methodArgumentNotValidException.getMessage())
                    .developerMessage(methodArgumentNotValidException.getClass().getName())
                    .fields(fields)
                    .fieldsMessage(FieldsMessage)
                    .build(),
            HttpStatus.BAD_REQUEST
        );
    }

}
