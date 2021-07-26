package curso.springboot2.springboot2essential.requests;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

/**
 * Classe criada com a função de validar os dados inseridos antes de seguir pela
 * aplicação (Padrao de projetos DTO)
 */
@Data
public class AnimePostRequestBody {
    @NotEmpty(message= "The anime name cannot be empty") // Usa depedencia validation e indica que não pode ser vazio com essa msg
                                                         // O controller deve receber @Valid para que ele faça a validação automática
                                                         // NotEmpty já atende o NotNull tambem
    // @NotNull(message= "The anime name cannot be null") // Usa depedencia validation e indica que não pode ser null com essa msg
    private String name;
}
