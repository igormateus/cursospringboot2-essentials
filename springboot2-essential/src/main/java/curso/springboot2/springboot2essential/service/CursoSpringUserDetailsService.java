package curso.springboot2.springboot2essential.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import curso.springboot2.springboot2essential.repository.CursoSpringUserRepository;
import lombok.RequiredArgsConstructor;

/**
 * Classe responsável pela pesquisa do usuário no banco de dados ou retorno de erro
 */
@Service
@RequiredArgsConstructor
public class CursoSpringUserDetailsService implements UserDetailsService {

    private final CursoSpringUserRepository cursoSpringUserRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        return Optional.ofNullable(cursoSpringUserRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("CursoSpring User not found"));
    }


    
}
