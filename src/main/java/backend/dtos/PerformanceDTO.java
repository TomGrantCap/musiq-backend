package backend.dtos;

import java.util.HashSet;
import java.util.Set;

public class PerformanceDTO {

private Long id;
private String name;
private String genre;

private Set<Long> dj;

public PerformanceDTO(Long id, String name, String genre) {
    this.id = id;
    this.name = name;
    this.genre = genre;
    dj = new HashSet<>();
}

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getGenre() {
    return genre;
}

public void setGenre(String genre) {
    this.genre = genre;
}

public Set<Long> getDj() {
    return dj;
}

public void setDj(Set<Long> dj) {
    this.dj = dj;
}

}
