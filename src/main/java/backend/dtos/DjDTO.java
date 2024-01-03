package backend.dtos;

import java.util.HashSet;
import java.util.Set;

public class DjDTO {
    private Long id;
    private String name;
    private String genre;
    private Set<Long> performanceIDs;
    private Set<String> performanceNames;

    public DjDTO(Long id, String name, String genre) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        performanceIDs = new HashSet<>();
        this.performanceNames = new HashSet<>();

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

    public Set<String> getPerformanceNames() {
        return performanceNames;
    }

    public void setPerformanceNames(Set<String> performanceNames) {
        this.performanceNames = performanceNames;
    }
    public Set<Long> getPerformanceIDs() {
        return performanceIDs;
    }

    public void setPerformanceIDs(Set<Long> performanceIDs) {
        this.performanceIDs = performanceIDs;
    }
}
