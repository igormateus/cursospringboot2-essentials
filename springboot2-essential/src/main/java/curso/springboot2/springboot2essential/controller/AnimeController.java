package curso.springboot2.springboot2essential.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import curso.springboot2.springboot2essential.domain.Anime;
import curso.springboot2.springboot2essential.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Classe criada para controlar os endpoints
 */
@RestController // notacao que indica que classe eh um controller e 
// todos os metodos terao @ResponseBody indicando que os retornos serao apenas Strings
// que eh o que queremos quando estamos desenvolvendo APIs (Valor JSON)
@RequestMapping("animes") // indica que todas os metodos deverao passar pelo endereco '/animes'
@Log4j2 // Anotacao do Lombok para registro de log
@AllArgsConstructor // Notacao que cria contrutor com todos os atributos inicializados
// retira a necessidade de usar o @Autowired
// @RequiredArgsConstructor // Cria um construtor com todos os campos que sao finais
// tambem retira necessidade do @Autowired
public class AnimeController {

    // @Autowired //Injecao de dependencia do spring, Classe injetada precisa ser @Component
    private DateUtil dateUtil;

    //localhost:8080/animes/list
    @GetMapping(path = "list") // Indica que ele sera chamado no metodo GET para o endereco '/list'
    public List<Anime> list() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now())); //Registra um log na tela de output
        return List.of(new Anime("DBZ"), new Anime("Berseker"));
    }
}
