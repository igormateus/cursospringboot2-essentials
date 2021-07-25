package curso.springboot2.springboot2essential.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import curso.springboot2.springboot2essential.exception.BadRequestException;
import curso.springboot2.springboot2essential.exception.BadRequestExceptionDetails;

/**
 * Classe que indica ao Spring o que deve ser feito quando um erro acontecer
 */
@ControllerAdvice // Transforma em spring bean e avisa aos controllers que podem usar o que ha nessa classe
                  // Como uma flag  
public class RestExceptionHandler {
    
    @ExceptionHandler(BadRequestException.class) // Flag indicando que o método abaixo deve ser usado
                                                 // em caso de erro BadRequest
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException badRequestException) {
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

}
