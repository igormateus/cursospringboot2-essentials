package curso.springboot2.springboot2essential.integration;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
// import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import curso.springboot2.springboot2essential.domain.Anime;
import curso.springboot2.springboot2essential.domain.CursoSpringUser;
import curso.springboot2.springboot2essential.repository.AnimeRepository;
import curso.springboot2.springboot2essential.repository.CursoSpringUserRepository;
import curso.springboot2.springboot2essential.requests.AnimePostRequestBody;
import curso.springboot2.springboot2essential.util.AnimeCreator;
import curso.springboot2.springboot2essential.util.AnimePostRequestBodyCreator;
import curso.springboot2.springboot2essential.wrapper.PageableResponse;


/**
 * AnimeControleIT
 * 
 * Criada para testes de integração
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Inicia todo spring
// Faz com que o teste aconteça em alguma porta aleatória para não usar a porta principal
@AutoConfigureTestDatabase // Usa um banco em memória para teste
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
// Faz o spring dar um drop antes de cada teste
public class AnimeControleIT {

    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser") // Indica o Bean que será usado
    private TestRestTemplate testRestTemplateRoleUser;

    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateRoleAdmin;

    // @LocalServerPort // Forma que capturar em que porta o servidor está sendo testado
    // private int port;

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private CursoSpringUserRepository cursoSpringUserRepository;

    private static final CursoSpringUser USER = CursoSpringUser.builder()
            .name("Curso Spring Boot 2 - essentials")
            .password("{bcrypt}$2a$10$vbofvTseimODlaFVon5Aluzg28mdWAxLaQaXiRUM4CCxBU7AmlhJK")
            .username("curso")
            .authorities("ROLE_USER")
            .build();

    private static final CursoSpringUser ADMIN = CursoSpringUser.builder()
            .name("Igor Mateus")
            .password("{bcrypt}$2a$10$vbofvTseimODlaFVon5Aluzg28mdWAxLaQaXiRUM4CCxBU7AmlhJK")
            .username("igor")
            .authorities("ROLE_USER,ROLE_ADMIN")
            .build();

    @TestConfiguration // Classe para configurar usuário e senha do test
    @Lazy // Classes estaticas são carregadas inicialmente. Com o Lazy, ela aguardará o carregamento
          // Para posterior criação e preenchimento
    static class Config {
        @Bean(name = "testRestTemplateRoleUser") // Nomea o Bean
        public TestRestTemplate testRestTemplateRoleUserCreator(
            @Value("${local.server.port}") int port) { // forma de importar a porta do teste
                                                       // Dados do value retirados da classe 
                                                       // @LocalServerPort

            RestTemplateBuilder restTemplateBuilder =  new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port) // Indica a URI do test
                    .basicAuthentication("curso", "curso"); // Indica metodo de acesso e dados
            
            return new TestRestTemplate(restTemplateBuilder);
        }

        @Bean(name = "testRestTemplateRoleAdmin") 
        public TestRestTemplate testRestTemplateRoleAdminCreator(
            @Value("${local.server.port}") int port) { 

            RestTemplateBuilder restTemplateBuilder =  new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port) 
                    .basicAuthentication("igor", "curso"); 
            
            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    @DisplayName("list returns list of anime inside page object when successful")
    void list_ReturnListOfAnimesInsidePageObject_WhenSuccessful() {
        // Esse método vai executar no banco em memória
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        
        cursoSpringUserRepository.save(USER);
        
        String expectedName = savedAnime.getName();

        PageableResponse<Anime> animePage = testRestTemplateRoleUser.exchange(
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

    @Test
    @DisplayName("listAll returns list of anime when successful")
    void listAll_ReturnsListOfAnimes_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        
        cursoSpringUserRepository.save(USER);
        
        String expectedName = savedAnime.getName();

        List<Anime> animePage = testRestTemplateRoleUser.exchange(
            "/animes/all", 
            HttpMethod.GET, 
            null,
            new ParameterizedTypeReference<List<Anime>>(){}).getBody();

        Assertions.assertThat(animePage)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animePage.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        Long expectedAnimeId = savedAnime.getId();

        cursoSpringUserRepository.save(USER);

        Anime anime = testRestTemplateRoleUser.getForObject("/animes/{id}", Anime.class, expectedAnimeId);

        Assertions.assertThat(anime)
                .isNotNull();
                
        Assertions.assertThat(anime.getId())
                .isNotNull()
                .isEqualTo(expectedAnimeId);
    }

    @Test
    @DisplayName("findByName returns a list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        String expectedName = savedAnime.getName();

        cursoSpringUserRepository.save(USER);

        String url = String.format("/animes/find?name=%s", expectedName);

        List<Anime> animes = testRestTemplateRoleUser.exchange(
            url, 
            HttpMethod.GET, 
            null,
            new ParameterizedTypeReference<List<Anime>>(){}).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
                
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns a empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
        
        cursoSpringUserRepository.save(USER);
        
        List<Anime> animes = testRestTemplateRoleUser.exchange(
            "/animes/find?name=any name", 
            HttpMethod.GET, 
            null,
            new ParameterizedTypeReference<List<Anime>>(){}).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {

        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
        
        cursoSpringUserRepository.save(USER);
        
        ResponseEntity<Anime> animeResponseEntity = testRestTemplateRoleUser.postForEntity(
            "/animes", 
            animePostRequestBody, 
            Anime.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("replace updates anime when successful")
    void replace_UpdateAnime_WhenSuccessful() {

        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        
        cursoSpringUserRepository.save(USER);

        savedAnime.setName("new name");

        ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleUser.exchange(
            "/animes", 
            HttpMethod.PUT,
            new HttpEntity<>(savedAnime),
            Void.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful() {

        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        
        cursoSpringUserRepository.save(ADMIN);

        ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleAdmin.exchange(
            "/animes/admin/{id}", 
            HttpMethod.DELETE,
            null,
            Void.class,
            savedAnime.getId());

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete returns 403 when user is not admin")
    void delete_Returns403_WhenUserIsNotAdmin() {

        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        
        cursoSpringUserRepository.save(USER);

        ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleUser.exchange(
            "/animes/admin/{id}", 
            HttpMethod.DELETE,
            null,
            Void.class,
            savedAnime.getId());

        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}