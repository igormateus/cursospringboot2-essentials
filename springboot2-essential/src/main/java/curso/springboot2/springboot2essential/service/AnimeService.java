package curso.springboot2.springboot2essential.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import curso.springboot2.springboot2essential.domain.Anime;

/**
 * Classe responsavel pela regra/lógica de negocio
 */
@Service // Para tornar a classe um servico e Bean do Spring
public class AnimeService {

    private static List<Anime> animes;
    static {
        animes = new ArrayList<>(List.of(new Anime(1L, "DBZ"), new Anime(2L, "Berseker"), new Anime(3L, "Boku No Hero")));
    } 

    // private final AnimeRepository animeRepository;

    public List<Anime> listAll() { // Metodo de exemplo mas n queremos dar acesso all na vida real
        return animes;
    }

    public Anime findById(Long id) { // Metodo de exemplo mas n queremos dar acesso all na vida real
        return animes
                .stream() // Retorna um Stream sequencial com a coleção
                .filter(anime -> anime.getId().equals(id)) // executa função anonima em todos os elementos 
                                                           // e retorna os que passam como verdadeiros
                .findFirst() // Retorna um Optional do primeiro elemento ou vazio
                .orElseThrow(() -> new ResponseStatusException( // Caso não encontre nada levanta erro
                        HttpStatus.BAD_REQUEST, // Determina tipo de saída, definido no padrão de projeto
                        "Anime not found" // Determina mensagem de retorno
        ));
    }

    public Anime save(Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(4, 100000)); // Pega um número aleatório
        animes.add(anime);
        return anime;
    }
}
