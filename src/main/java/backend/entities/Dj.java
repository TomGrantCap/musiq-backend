package backend.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

//TO DO: ADD VALIDATIONS
@Entity(name="DJS")
public class Dj {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String genre;

    @ManyToMany(mappedBy = "dj")
    private Set<Performance> performance  = new HashSet<>();

    public Dj(Long id, String name, String genre) {
        this.id = id;
        this.name = name;
        this.genre = genre;
    }

    protected Dj() {

    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Set<Performance> getPerformance() {
        return this.performance;
    }

    public void setPerformance(Set<Performance> performance) {
        this.performance = performance;
    }

}