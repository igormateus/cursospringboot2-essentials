package curso.springboot2.springboot2essential.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Classe que diz os dados que devem ser preenchidos caso esse erro aconteça
 */
@Getter
@SuperBuilder
public class BadRequestExceptionDetails extends ExceptionDeatils {
    
}
