package curso.springboot2.springboot2essential.repository;

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
    @DisplayName("Save creates anime when Successful")
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

    private Anime createAnime() {
        return Anime.builder()
                .name("Hajime no Ippo")
                .build();
    }
}
