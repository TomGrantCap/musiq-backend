package Backend.Repositories;

import Backend.Entities.DJ;
import Backend.Entities.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DJRepository extends JpaRepository<DJ, Long> {

    DJ findDJsByGenreEqualsIgnoreCase(String genre);
    DJ findDJsByNameEqualsIgnoreCase(String name);

    DJ findDJByNameIsGreaterThan(String string);

}