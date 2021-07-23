package curso.springboot2.springboot2essential.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

// Todos abaixo possibilitam a classe ser autoInjetavel
@Component     // Transforma a classe em um component
// @Service    // Tb eh Component, mas esta relacionado a servico
// @Repository // Possui excessoes para trabalhar com spring data
public class DateUtil {

    public String formatLocalDateTimeToDatabaseStyle(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }

}
