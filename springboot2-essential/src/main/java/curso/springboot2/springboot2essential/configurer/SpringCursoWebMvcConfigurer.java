package curso.springboot2.springboot2essential.configurer;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe com função de configurar o MVC do Spring
 */
@Configuration // Usado globalmente no spring
public class SpringCursoWebMvcConfigurer implements WebMvcConfigurer {
    
    // Ajustando o número inicial da página apresentada e qual o tamanho de itens por página
    // por default é 0 e 20
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver pageHandler = new PageableHandlerMethodArgumentResolver();
        pageHandler.setFallbackPageable(PageRequest.of(
            1, // Pagina inicialmente apresentada 0 = primeira página, 1 = segunda página...
            5)); // Tamanho das paginas exibidas
        resolvers.add(pageHandler);
    }

}
