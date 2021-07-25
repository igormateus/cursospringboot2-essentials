package curso.springboot2.springboot2essential.exception;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

/**
 * Classe que diz os dados que devem ser preenchidos caso esse erro aconteça
 */
@Data
@Builder
public class BadRequestExceptionDetails {
    private String title;
    private int status;
    private String details;
    private String developerMessage;
    private LocalDateTime timestamp;
}
