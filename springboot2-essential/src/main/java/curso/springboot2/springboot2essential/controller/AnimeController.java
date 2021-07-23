package curso.springboot2.springboot2essential.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import curso.springboot2.springboot2essential.domain.Anime;
import curso.springboot2.springboot2essential.service.AnimeService;
import curso.springboot2.springboot2essential.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Classe criada para controlar os endpoints
 */
@RestController // notacao que indica que classe eh um controller e 
// todos os metodos terao @ResponseBody indicando que os retornos serao apenas Strings
// que eh o que queremos quando estamos desenvolvendo APIs (Valor JSON)
@RequestMapping("animes") // indica que todas os metodos deverao passar pelo endereco '/animes'
@Log4j2 // Anotacao do Lombok para registro de log
// @AllArgsConstructor // Notacao que cria contrutor com todos os atributos inicializados
// retira a necessidade de usar o @Autowired
@RequiredArgsConstructor // Cria um construtor com todos os campos que sao finais
// tambem retira necessidade do @Autowired
public class AnimeController {

    // @Autowired //Injecao de dependencia do spring, Classe injetada precisa ser @Component
    private final DateUtil dateUtil;

    private final AnimeService animeService;

    // localhost:8080/animes/list
    // @GetMapping(path = "list") // Indica que ele sera chamado no metodo GET para o endereco '/list'
    @GetMapping // Spring mapeia e joga aqui requisições GET na raiz
    public ResponseEntity<List<Anime>> list() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now())); //Registra um log na tela de output
        return new ResponseEntity<>( // Tipo de saída de uma response
                animeService.listAll(), // Conteudo da resposta que passará no JSON
                HttpStatus.OK // Indica o status da resposta
        );

        // return ResponseEntity.ok(animeService.listAll()); // Outra forma de responder
    }

    @GetMapping(path = "/{id}") // Não pode ser duplicado sem um endpoint distinto;
    // o tempo entre {} será o pathvareables chamado pelo @PathVariable
    public ResponseEntity<Anime> findById(
            @PathVariable // indica que a próxima variavel será preenchida com o id do endpoint
            Long id
    ){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.findById(id));
    }

    @PostMapping // Spring mapeia e joga aqui as requisições post
    // Temos a possibilidade de retornar apenas o cod 201 (created)
    // Ou retornar o ID criado, ou até mesmo o objeto inteiro criado
    // @ResponseStatus(HttpStatus.CREATED) // Indica que esse status será 201 - Created
    public ResponseEntity<Anime> save( // Nesse caso nós iremos retornar o objeto inteiro
            @RequestBody Anime anime // Diz que está esperando um Body e usará o Jackson para 
                                     // Transformar em Anime. Se o Jakson encontrar um JSON exatamente
                                     // igual aos atributos de classe ele mapeia. Caso o nome da var
                                     // seja diferente, o Jackson ignora a propriedade.
    ) { 

        return new ResponseEntity<>(animeService.save(anime), HttpStatus.CREATED);
        
    }
}
