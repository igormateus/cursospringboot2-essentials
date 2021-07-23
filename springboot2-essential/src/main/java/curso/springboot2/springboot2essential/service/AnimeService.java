package curso.springboot2.springboot2essential.service;

import java.util.List;

import org.springframework.stereotype.Service;

import curso.springboot2.springboot2essential.domain.Anime;

/**
 * Classe responsavel pela regra/lógica de negocio
 */
@Service // Para tornar a classe um servico e Bean do Spring
public class AnimeService {

    // private final AnimeRepository animeRepository;

    public List<Anime> listAll() { // Metodo de exemplo mas n queremos dar acesso all na vida real
        return List.of(new Anime(1L, "DBZ"), new Anime(2L, "Berseker"), new Anime(3L, "Boku No Hero"));
    }
}
