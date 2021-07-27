package curso.springboot2.springboot2essential.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import curso.springboot2.springboot2essential.domain.Anime;
import lombok.extern.log4j.Log4j2;

/**
 * Classe de exemplo para mostrar como o RestTemplate pega dados de outro serviço
 */
@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity( // Classe responsável pelo get em outro
                                                                        // serviço. Traz dados da resposta (header, obj, body)
            "http://localhost:8080/animes/{id}", // URL do endereço de busca
            Anime.class, // Tipo da resposta que será recebida (domain)
            2); // Variaveis. Podem ser várias dividias por vírgula

        log.info(entity);

        Anime object = new RestTemplate().getForObject( // Traz apenas o objeto
            "http://localhost:8080/animes/{id}",
            Anime.class, 
            2);

        log.info(object);
    }
}
