package curso.springboot2.springboot2essential.controller;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import curso.springboot2.springboot2essential.domain.Anime;
import curso.springboot2.springboot2essential.service.AnimeService;
import curso.springboot2.springboot2essential.util.AnimeCreator;

// @SpringBootTest // Starta a app para realizar o test
@ExtendWith(SpringExtension.class) // Usa o JUnit com o Spring e não precisa startar app
public class AnimeControllerTest {
    @InjectMocks // Usado quando queremos testar a classe em si
    private AnimeController animeController;

    @Mock // Usado para testar as classes que o controller ou a classe testada usa
    private AnimeService animeServiceMock;
    
    @BeforeEach // determina que o metodo abaixo deve ser sempre executado antes
    void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        // indica que sempre que o listAll for lançado com qq argumento ele retornará animePage
        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);
    }

    @Test
    @DisplayName("List returns list of anime inside page object when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeController.list(null).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName())
                .isEqualTo(expectedName);
    }
}
