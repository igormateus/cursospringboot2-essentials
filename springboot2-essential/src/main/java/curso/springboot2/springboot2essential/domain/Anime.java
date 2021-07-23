package curso.springboot2.springboot2essential.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class que representa o modelo que temos no banco de dados no padrão MVC
 */
@Data // Adiciona gets, sets, equals e hashcode
@AllArgsConstructor // Gera construtor de todos os atributos pelo lombok
public class Anime {
    private Long id;
    private String name;
}
