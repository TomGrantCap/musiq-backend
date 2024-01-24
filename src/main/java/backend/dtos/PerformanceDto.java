package backend.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

public class PerformanceDto {
private Long id;
@NotBlank(message = "MethodArgumentNotValidException: Performance name cannot be blank.")
private String name;
@NotBlank(message = "MethodArgumentNotValidException: Genre cannot be blank.")
private String genre;
private Set<Long> djIds;

public PerformanceDto(Long id, String name, String genre) {
    this.id = id;
    this.name = name;
    this.genre = genre;
    djIds = new HashSet<>();
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

public Set<Long> getDjIds() {
    return djIds;
}

public void setDj(Set<Long> djIds) {
    this.djIds = djIds;
}

}
