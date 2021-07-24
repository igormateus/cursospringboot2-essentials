package curso.springboot2.springboot2essential.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.springboot2.springboot2essential.domain.Anime;

/**
 * Classe responsável pela conexao direta com o banco de dados
 */
public interface AnimeRepository extends 
        JpaRepository< //Indica que é uma implementação do JpaRepository para o Spring
            Anime,     // Passa a Classe do domínio
            Long       // Passa o tipo do ID do domínio
        >              // Métodos do Jpa de pesquisa são baseados no nome dele
{
    
}
