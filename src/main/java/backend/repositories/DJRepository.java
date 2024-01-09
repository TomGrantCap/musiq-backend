package backend.repositories;

import java.util.Collection;

import backend.entities.DJ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DJRepository extends JpaRepository<DJ, Long> {


    Collection<DJ> findByGenreContainsIgnoreCase(String genre);
    Collection<DJ> findByNameContainsIgnoreCase(String name);
    Collection<DJ> findByNameContainsIgnoreCaseAndGenreContainsIgnoreCase(String name, String genre);
}