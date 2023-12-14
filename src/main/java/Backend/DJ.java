package Backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
class DJ {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String genre;

    protected DJ() {}

    public DJ(String name, String genre) {

        this.name = name;
        this.genre = genre;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getRole() {
        return this.genre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.genre = role;
    }

    @Override
    public String toString() {
        return "DJ {" + "id=" + this.id + ", name='" + this.name + '\'' + ", genre='" + this.genre + '\'' + '}';
    }
}