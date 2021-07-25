package curso.springboot2.springboot2essential.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // Ajusta response para todos os erros levantados
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
    
}
