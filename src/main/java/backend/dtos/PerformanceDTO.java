package backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

public class PerformanceDTO {
private Long id;
@NotBlank(message = "MethodArgumentNotValidException: Performance name cannot be blank.")
private String name;
@NotBlank(message = "MethodArgumentNotValidException: Genre cannot be blank.")
private String genre;
private Set<Long> djIDs;

public PerformanceDTO(Long id, String name, String genre) {
    this.id = id;
    this.name = name;
    this.genre = genre;
    djIDs = new HashSet<>();
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

public Set<Long> getDjIDs() {
    return djIDs;
}

public void setDj(Set<Long> djIDs) {
    this.djIDs = djIDs;
}

}
