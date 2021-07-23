package curso.springboot2.springboot2essential.domain;

/**
 * Class que representa o modelo da aplicacao no MVC
 */
public class Anime {
    private String name;

    public Anime(String name) {
        this.setName(name);
    }

    public Anime() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
