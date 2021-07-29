package curso.springboot2.springboot2essential.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import curso.springboot2.springboot2essential.domain.Anime;

/**
 * Classe serve para testar funções do Repository
 * Vamos usar o H2 para testar em memória, mas o ideal é testar no banco original
 */ 
@DataJpaTest // Test forcado em components JPA
@DisplayName("Tests for Anime Repository") // Dá nome pelo Junit para o teste
public class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;
    
    // Testes podem ser nomeados como desejado. Particularmeste é usado o 
    // nome_oQueOMetodoPrecisaFazer_quando
    @Test // Notação para indicar função de teste
    @DisplayName("Save persists anime when Successful")
    public void save_PersistAnime_WhenSuccessful() {
        Anime animeToBeSaved = createAnime(); // Crio anime
        Anime animeSaved = this.animeRepository.save(animeToBeSaved); // Salvo em repositório

        // Asserções para validar se anime foi salvo
        // Nesse exercício estamos usando a biblioteca AssertJ, mas o Junit poderia ser usado
        // Checa se é nulo
        Assertions.assertThat(animeSaved).isNotNull();
        // Verifica o id para saber se banco criou
        Assertions.assertThat(animeSaved.getId()).isNotNull(); 
        // Checa se banco salvou a mesma coisa que foi enviada
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());

    }

    @Test
    @DisplayName("Save update anime when Sucessful")
    public void save_UpdatesAnime_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        animeSaved.setName("Overlord");
        Anime animeUpdated = this.animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
    }

    @Test
    @DisplayName("Delete removes anime when Sucessful")
    public void delete_RemovesAnime_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        this.animeRepository.delete(animeSaved);

        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By Name returns list of anime when Sucessful")
    public void findByName_ReturnsListOfAnime_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        String name = animeSaved.getName();
        List<Anime> animes = this.animeRepository.findByName(name);

        Assertions.assertThat(animes).isNotEmpty();
        Assertions.assertThat(animes).contains(animeSaved);
    }

    // Trabalhando com casos não visíveis
    @Test
    @DisplayName("Find By Name returns empty list when no anime is found")
    public void findByName_ReturnsEmprtyList_WhenAnimeIsNotFound() {
        List<Anime> animes = this.animeRepository.findByName("Inexistente");

        Assertions.assertThat(animes).isEmpty();
    }

    private Anime createAnime() {
        return Anime.builder()
                .name("Hajime no Ippo")
                .build();
    }
}
