package curso.springboot2.springboot2essential.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class que representa o modelo que temos no banco de dados no padrão MVC
 */
@Data // Adiciona gets, sets, equals e hashcode
@AllArgsConstructor // Gera construtor de todos os atributos pelo lombok
@NoArgsConstructor // Cria construtor sem argumentos padrões
@Entity // Transforma domínio em entidade do BD
@Builder // Cria um construtor
public class Anime {

    @Id // Indica que esse é o id da aplicação
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que o banco gera esse campo
    private Long id;

    // @Column(nullable = false) // indica que não é nullable, mas não atualiza automaticamente o banco de dados
                                 // Não precisa do validation
    // @JsonProperty("name_wanted") // Indica para o Jackson que essa propriedade se chamará name_wanted no json
    @NotEmpty(message= "The anime name cannot be empty")
    private String name;
}
