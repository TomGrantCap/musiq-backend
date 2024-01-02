package backend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name="DJS")
public class DJ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @NotBlank
    private String name;
//    @NotBlank
    private String genre;

    @JsonIgnoreProperties("djs")
    @ManyToMany(mappedBy = "dj")
    private Set<Performance> performance  = new HashSet<>();

    public DJ(String name, String genre) {

        this.name = name;
        this.genre = genre;
    }

    protected DJ() {

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