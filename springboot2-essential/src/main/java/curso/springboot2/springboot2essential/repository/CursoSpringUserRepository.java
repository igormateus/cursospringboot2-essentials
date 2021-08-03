package curso.springboot2.springboot2essential.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import curso.springboot2.springboot2essential.domain.CursoSpringUser;

public interface CursoSpringUserRepository extends JpaRepository<CursoSpringUser, Long> {
    
    // Alterado para indicar o tipo de retorno do usuario encontrado
    CursoSpringUser findByUsername(String username); 
    
}
