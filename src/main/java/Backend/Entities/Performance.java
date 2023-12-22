package Backend.Entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Performance {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String genre;

    @NotBlank
    @OneToOne
    private DJ dj;

    protected Performance() {}

    public Performance(String name, String genre) {

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
    public DJ getDJ() { return dj; }
    public void setDJ(DJ dj) { this.dj = dj; }
}