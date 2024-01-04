package backend.Entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name="PERFORMANCES")
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotBlank
    private String name;


    //@NotBlank
    private String genre;

    @JsonIgnoreProperties("performances")
    @ManyToMany
    @JoinTable(name = "djs_performances", //Name of 'join table' to be created
            joinColumns = @JoinColumn(name="dj_id"),//Name of column on owner side to connect
            inverseJoinColumns = @JoinColumn(name="performance_id")) //Name of column on other side to connect
    private Set<DJ> dj = new HashSet<>();

    protected Performance() {}

    public Performance(Long id, String name, String genre) {

        this.id = id;
        this.name = name;
        this.genre = genre;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) { this.id = id; }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGenre() {
        return this.genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public Set<DJ> getDJ() { return dj; }
    public void setDJ(Set<DJ> dj) { this.dj = dj; }
}