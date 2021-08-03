package curso.springboot2.springboot2essential.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import curso.springboot2.springboot2essential.service.CursoSpringUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Classe criada para configurar usuário que será usado durante a aplicação
 */
@EnableWebSecurity // É Configuration e Component, por isso é carregado com a aplicação
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true) // Autoriza @PreAuthorize dos controllers
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CursoSpringUserDetailsService cursoSpringUserDetailsService;

    //Usado para configurar o usuário em memória
    /**
     * BasicAuthenticationFilter - Autenticação básica com Base64 encoder (usuario.senha)
     * UsernamePasswordAuthenticationFilter
     * DefaultLoginPageGeneratingFilter - Página que gera o login
     * DefaultLogoutPageGeneratingFilter
     * FilterSecurityInterceptor - Classe que checa se vc está logado
     * Authentication - Authorization <=> Autentica (é quem diz que é) - Autoriza (Tem acesso à funções)
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("Password encoded {}", passwordEncoder.encode("curso"));

        // A configuração do spring aceita multipla auteticação com diferentes providers
        // Ex Archive, directory, in memory, Bd
        // Nesse caso ele busca na ordem em que os dados são colocados abaixo

        // Buscando em memória
        auth.inMemoryAuthentication()                       // Autenticação em memória
                .withUser("igor2")                          // Adiciona usuário
                .password(passwordEncoder.encode("curso"))  // Adiciona senha
                .roles("USER", "ADMIN")                     // Adiciona regras de acesso padrões do usuário
            .and()
                .withUser("curso2")                         // Adiciona novo usuário
                .password(passwordEncoder.encode("curso"))  // Adiciona senha
                .roles("USER");                             // Adiciona regras de acesso do usuário

        // Buscando do banco de dados
        auth.userDetailsService(cursoSpringUserDetailsService)  // Indica o usuário para autenticação
                .passwordEncoder(passwordEncoder);              // Indica tipo de codificação da senha
    }

    // Nesse método indicamos o que está sendo protegido com o método Http.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()   // Desabilita o CSRF TOKEN
                // .csrf() // Adiciona o CSRF TOKEN
                // .csrfTokenRepository( // Tipo do token
                    // CookieCsrfTokenRepository.withHttpOnlyFalse()) // Indica que será salvo no cooke
                    // Aplicações de front não conseguiram pegar o valor do cookie
                    // com o WithHttpOnly, eles passam a conseguir
            // .and()
                .authorizeRequests()// Autenticação para toda URL adicionando ADMIN e USER in ROLEs
                .anyRequest()       // Qualquer requisição
                .authenticated()    // Deve ser autenticada
            .and()
                .formLogin()        // Adiciona página de login na aplicação
            .and()
                .httpBasic();        // Deve ser na forma HTTP Basic

    }
    
}
