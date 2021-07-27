package curso.springboot2.springboot2essential.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot2.springboot2essential.domain.Anime;
import curso.springboot2.springboot2essential.exception.BadRequestException;
import curso.springboot2.springboot2essential.mapper.AnimeMapper;
import curso.springboot2.springboot2essential.repository.AnimeRepository;
import curso.springboot2.springboot2essential.requests.AnimePostRequestBody;
import curso.springboot2.springboot2essential.requests.AnimePutRequestBody;

import lombok.RequiredArgsConstructor;

/**
 * Classe responsavel pela regra/lógica de negocio
 */
@Service // Para tornar a classe um servico e Bean do Spring
@RequiredArgsConstructor // Diz ao Spring para preencher os campos automaticamente
public class AnimeService {

    // private static List<Anime> animes;
    // static {
    //     animes = new ArrayList<>(List.of(new Anime(1L, "DBZ"), new Anime(2L, "Berseker"), new Anime(3L, "Boku No Hero")));
    // } 

    private final AnimeRepository animeRepository;

    public Page<Anime> listAll(Pageable pageable) { // Metodo de exemplo mas n queremos dar acesso all na vida real
        return animeRepository.findAll(pageable);
    }

    public List<Anime> findByName(String name) {
        return animeRepository.findByName(name);
    }

    public Anime findByIdOrThrowBadRequestException(Long id) { // Metodo de exemplo mas n queremos dar acesso all na vida real
        
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not found"));

        // return animes
        //         .stream() // Retorna um Stream sequencial com a coleção
        //         .filter(anime -> anime.getId().equals(id)) // executa função anonima em todos os elementos 
        //                                                    // e retorna os que passam como verdadeiros
        //         .findFirst() // Retorna um Optional do primeiro elemento ou vazio
        //         .orElseThrow(() -> new ResponseStatusException( // Caso não encontre nada levanta erro
        //                 HttpStatus.BAD_REQUEST, // Determina tipo de saída, definido no padrão de projeto
        //                 "Anime not found" // Determina mensagem de retorno
        // ));
    }

    @Transactional // Indica para o spring que é uma transação e acontecerá rollback em caso de erro.
                   // Em caso de erros thows Erro, é necessário usar @Transaction(rollBackFor = Exception.class)
                   // isto para que ele leve em conta tambem as transações do tipo Checked. Não é aconselhado
                   // usar desta forma (rollBackFor)
    public Anime save(AnimePostRequestBody animePostRequestBody) {

        return animeRepository.save(
            AnimeMapper.INSTANCE.toAnime(animePostRequestBody) // Mapper fará a transformacao do obj
        );

        // V2.0
        // return animeRepository.save( // Chama o repository para salvar
        //     Anime.builder() // Construtor estático criado no modelo pelo Lombok
        //             .name(animePostRequestBody.getName()) // indica o dado ao construtor
        //             .build() // Constrói
        // );
        
        // V1.0
        // anime.setId(ThreadLocalRandom.current().nextLong(4, 100000)); // Pega um número aleatório
        // animes.add(anime);
        // return anime;
    }

    public void delete(Long id) { // RFC7231 - Indepotência
        
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
        
        // animes.remove(findById(id)); // Caso encontre, ele remove, caso não, ele retorna bad_request
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {

        Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId()); // Procura o anime
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody); // Transforma em anime
        anime.setId(savedAnime.getId());  // Recupera o id e adiciona no anime

        animeRepository.save(anime);



        // V2.0
        // findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        // animeRepository.save(
        //     Anime.builder()
        //             .id(animePutRequestBody.getId())
        //             .name(animePutRequestBody.getName())
        //             .build()
        // );

        // V1.0
        // delete(anime.getId()); // Procura e deleta. Se não achar, Erro Bad_request
        // animes.add(anime); // Adiciona Anime à lista
    }
}
