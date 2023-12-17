package Backend.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Performance {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String genre;

    protected Performance() {}

    public Performance(String name, String genre) {

        this.name = name;
        this.genre = genre;
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

//    @Override
//    public String toString() {
//        return "DJ {" + "id=" + this.id + ", name='" + this.name + '\'' + ", genre='" + this.genre + '\'' + '}';
//    }
}