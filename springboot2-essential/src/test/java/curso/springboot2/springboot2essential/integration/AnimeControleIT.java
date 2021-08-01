package curso.springboot2.springboot2essential.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import curso.springboot2.springboot2essential.domain.Anime;
import curso.springboot2.springboot2essential.repository.AnimeRepository;
import curso.springboot2.springboot2essential.util.AnimeCreator;
import curso.springboot2.springboot2essential.wrapper.PageableResponse;


/**
 * AnimeControleIT
 * 
 * Criada para testes de integração
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Inicia todo spring
// Faz com que o teste aconteça em alguma porta aleatória para não usar a porta principal
@AutoConfigureTestDatabase // Usa um banco em memória para teste
public class AnimeControleIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort // Forma que capturar em que porta o servidor está sendo testado
    private int port;

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("list returns list of anime inside page object when successful")
    void list_ReturnListOfAnimesInsidePageObject_WhenSuccessful() {
        // Esse método vai executar no banco em memória
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        PageableResponse<Anime> animePage = testRestTemplate.exchange(
            "/animes", 
            HttpMethod.GET, 
            null,
            new ParameterizedTypeReference<PageableResponse<Anime>>(){}).getBody();

        Assertions.assertThat(animePage).isNotNull();
        
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

}