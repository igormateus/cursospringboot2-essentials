package curso.springboot2.springboot2essential.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDeatils{
    private final String fields;
    private final String fieldsMessage;
    
}
