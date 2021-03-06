package curso.springboot2.springboot2essential.controller;

// import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import curso.springboot2.springboot2essential.domain.Anime;
import curso.springboot2.springboot2essential.requests.AnimePostRequestBody;
import curso.springboot2.springboot2essential.requests.AnimePutRequestBody;
import curso.springboot2.springboot2essential.service.AnimeService;
// import curso.springboot2.springboot2essential.util.DateUtil;
import lombok.RequiredArgsConstructor;
// import lombok.extern.log4j.Log4j2;

/**
 * Classe criada para controlar os endpoints
 */
@RestController // notacao que indica que classe eh um controller e 
// todos os metodos terao @ResponseBody indicando que os retornos serao apenas Strings
// que eh o que queremos quando estamos desenvolvendo APIs (Valor JSON)
@RequestMapping("animes") // indica que todas os metodos deverao passar pelo endereco '/animes'
// @Log4j2 // Anotacao do Lombok para registro de log
// @AllArgsConstructor // Notacao que cria contrutor com todos os atributos inicializados
// retira a necessidade de usar o @Autowired
@RequiredArgsConstructor // Cria um construtor com todos os campos que sao finais
// tambem retira necessidade do @Autowired
public class AnimeController {

    // @Autowired //Injecao de dependencia do spring, Classe injetada precisa ser @Component
    // private final DateUtil dateUtil;

    private final AnimeService animeService;

    // localhost:8080/animes/list
    // @GetMapping(path = "list") // Indica que ele sera chamado no metodo GET para o endereco '/list'
    @GetMapping // Spring mapeia e joga aqui requisi????es GET na raiz
    public ResponseEntity<Page<Anime>> list(Pageable pageable) {
        // log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now())); //Registra um log na tela de output
        return new ResponseEntity<>( // Tipo de sa??da de uma response
                animeService.listAll(pageable), // Conteudo da resposta que passar?? no JSON
                HttpStatus.OK // Indica o status da resposta
        );

        // return ResponseEntity.ok(animeService.listAll()); // Outra forma de responder
    }

    // Adicionado para testes no restTemplate Exchange
    @GetMapping(path = "/all")
    public ResponseEntity<List<Anime>> listAll() {
        // log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.listAllNonPageable());
    }

    @GetMapping(path = "/{id}") // N??o pode ser duplicado sem um endpoint distinto;
    // o tempo entre {} ser?? o pathvareables chamado pelo @PathVariable
    public ResponseEntity<Anime> findById(
            @PathVariable // indica que a pr??xima variavel ser?? preenchida com o id do endpoint
            Long id
    ){
        // log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
    }

    @GetMapping(path = "by-id/{id}")
    public ResponseEntity<Anime> findByIdAuthenticationPrincipal(@PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails){ //Forma de pegar o usu??rio logado
        return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
    }
    
    @GetMapping(path = "/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam String name){ // Entende o par??metro na URL (depois da ?)
        // @RequestParam(required = false) - Parametro n??o ?? mais obrigat??rio
        // @RequestParam(defaultValue = "") - Insere padr??o default, normalmente ?? null
        // V??rios Requests podem ser inseridos com & para dividir na URL
        // log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.findByName(name));
    }

    @PostMapping // Spring mapeia e joga aqui as requisi????es post
    // Temos a possibilidade de retornar apenas o cod 201 (created)
    // Ou retornar o ID criado, ou at?? mesmo o objeto inteiro criado
    // @ResponseStatus(HttpStatus.CREATED) // Indica que esse status ser?? 201 - Created
    // @PreAuthorize("hasRole('ADMIN')") // Pr?? autorize atrav??s de alguma Role. Nesse caso ADMIN
    public ResponseEntity<Anime> save( // Nesse caso n??s iremos retornar o objeto inteiro
            @RequestBody @Valid AnimePostRequestBody animePostRequestBody // Diz que est?? esperando um Body e usar?? o Jackson para 
                                     // Transformar em Anime. Se o Jakson encontrar um JSON exatamente
                                     // igual aos atributos de classe ele mapeia. Caso o nome da var
                                     // seja diferente, o Jackson ignora a propriedade.
    ) { 
        return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/admin/{id}") // Cria um caminho de metodo delete para o path
    public ResponseEntity<Void> delete(@PathVariable Long id) { // Void pq n tem retorno
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping // Cria um caminho para m??todo put
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody) {
        animeService.replace(animePutRequestBody); 
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // N??o retorna nada e vazio
    }
}
