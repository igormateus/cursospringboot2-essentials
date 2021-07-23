package curso.springboot2.springboot2essential;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe responsavel por executar a aplicacao.
 * Inicialmente configuraca com o @SpringBootApplication
 */
//@EnableAutoConfiguration //Configura para que aplicacao funcione e transforma Class abaixo em Bean do Spring
@SpringBootApplication
//@ComponentScan --- Caso eu quisesse que ele iniciasse a partir de outro pacote
public class Springboot2EssentialApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot2EssentialApplication.class, args);
	}

}
