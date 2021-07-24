package curso.springboot2.springboot2essential.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import curso.springboot2.springboot2essential.domain.Anime;
import curso.springboot2.springboot2essential.requests.AnimePostRequestBody;
import curso.springboot2.springboot2essential.requests.AnimePutRequestBody;

/**
 * Classe responsável por fazer a conversão de todos os atributos de um objeto
 * para outro
 */
@Mapper(componentModel = "spring") // ComponentModel possibilita injecao de dependencia
public abstract class AnimeMapper {

    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);
    
    public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);
    public abstract Anime toAnime(AnimePutRequestBody animePutRequestBody);
}
