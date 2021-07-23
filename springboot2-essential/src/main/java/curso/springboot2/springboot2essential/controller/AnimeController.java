package curso.springboot2.springboot2essential.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import curso.springboot2.springboot2essential.domain.Anime;

/**
 * Classe criada para controlar os endpoints
 */
@RestController // notacao que indica que classe eh um controller e 
// todos os metodos terao @ResponseBody indicando que os retornos serao apenas Strings
// que eh o que queremos quando estamos desenvolvendo APIs (Valor JSON)
@RequestMapping("animes") // indica que todas os metodos deverao passar pelo endereco '/animes'
public class AnimeController {

    //localhost:8080/animes/list
    @GetMapping(path = "list") // Indica que ele sera chamado no metodo GET para o endereco '/list'
    public List<Anime> list() {
        return List.of(new Anime("DBZ"), new Anime("Berseker"));
    }
}
