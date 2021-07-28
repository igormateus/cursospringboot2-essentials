package curso.springboot2.springboot2essential.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
        
        log.info("--- RestTemplate().getForEntity ---");
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity( // Classe responsável pelo get em outro
                                                                        // serviço. Traz dados da resposta (header, obj, body)
            "http://localhost:8080/animes/{id}", // URL do endereço de busca
            Anime.class, // Tipo da resposta que será recebida (domain)
            2); // Variaveis. Podem ser várias dividias por vírgula

        log.info(entity);

        log.info("--- RestTemplate().getForObject ---");
        Anime object = new RestTemplate().getForObject( // Traz apenas o objeto
            "http://localhost:8080/animes/{id}",
            Anime.class, 
            2);

        log.info(object);

        log.info("--- RestTemplate().getForObject in array ---");
        Anime[] animes = new RestTemplate().getForObject( // Traz a lista, mas não é usual (arrays)
            "http://localhost:8080/animes/all",
            Anime[].class);

        log.info(Arrays.toString(animes));

        log.info("--- RestTemplate().exchange ---");
        ResponseEntity<List<Anime>> animeList = new RestTemplate().exchange( // Transforma em lista
            "http://localhost:8080/animes/all", 
            HttpMethod.GET, // Método de pedido
            null, // RequestEntity, que pode receber post e método de autenticação
            new ParameterizedTypeReference<>() {}); // Transforma na lista passada no tipo da variável

        log.info(animeList.getBody());

        log.info("--- RestTemplate().postForObject ---");
        Anime kingdom = Anime.builder().name("Kingdom").build();
        Anime kingdomSaved = new RestTemplate().postForObject(
            "http://localhost:8080/animes/", // url, 
            kingdom, // request, 
            Anime.class); // responseType, 
            // uriVariables)

        log.info("Saved anime {}", kingdomSaved);

        log.info("--- RestTemplate().exchange ---"); // Exchange é poderoso por poder passar Headers
        Anime samuraiChamploo = Anime.builder().name("Samurai Champloo").build();
        ResponseEntity<Anime> samuraiChamplooSaved = new RestTemplate().exchange(
            "http://localhost:8080/animes/", // url, 
            HttpMethod.POST, // Method
            new HttpEntity<>(samuraiChamploo, createJsonHeader()), // request Entity, 
            Anime.class); // responseType, 
            // uriVariables)
            // .getBody() para pegar diretamente o valor

        log.info("Saved anime {}", samuraiChamplooSaved);
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        // httpHeaders.setBasicAuth(username, password); // Caso tivessemos token
        return httpHeaders;
    }
}
