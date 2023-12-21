package Backend.Entities;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import java.util.List;

@Entity
public class Performance {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String genre;

    @OneToMany
    @JoinColumn
    private List<DJ> djs;

    protected Performance() {}

    public Performance(List<DJ>djs, String name, String genre) {

        this.name = name;
        this.genre = genre;
        this.djs = djs;
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
    public List<DJ> getDjs() { return djs; }
    public void setDjs(List<DJ> djs) { this.djs = djs; }
}