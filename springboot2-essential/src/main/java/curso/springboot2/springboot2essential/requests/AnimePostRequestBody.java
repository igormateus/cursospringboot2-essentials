package curso.springboot2.springboot2essential.requests;

import lombok.Data;

/**
 * Classe criada com a função de validar os dados inseridos antes de seguir pela
 * aplicação (Padrao de projetos DTO)
 */
@Data
public class AnimePostRequestBody {
    private String name;
}
