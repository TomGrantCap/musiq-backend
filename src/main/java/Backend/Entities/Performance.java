package Backend.Entities;

import jakarta.persistence.*;
import Backend.Entities.DJ;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Entity
public class Performance {

    @Id
    @GeneratedValue

    private Long id;

    private String name;


    private String genre;

    @OneToOne
    @JoinColumn()
    private DJ dj;


    protected Performance() {}

    public Performance(DJ dj, String name, String genre) {

        this.name = name;
        this.genre = genre;
        this.dj = dj;
    }

    public Long getId() {
        return this.id;
    }

    public DJ getDJ(){ return dj; }

    public String getName() {
        return this.name;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(DJ dj) {
        this.dj = dj;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}