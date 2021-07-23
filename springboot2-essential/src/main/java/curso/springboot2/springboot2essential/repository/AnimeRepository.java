package curso.springboot2.springboot2essential.repository;

import java.util.List;

import curso.springboot2.springboot2essential.domain.Anime;

/**
 * Classe responsável pela conexao direta com o banco de dados
 */
public interface AnimeRepository {
    List<Anime> listAll();
    
}
