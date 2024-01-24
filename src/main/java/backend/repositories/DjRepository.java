package backend.repositories;

import java.util.Collection;

import backend.entities.Dj;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DjRepository extends JpaRepository<Dj, Long> {

    Collection<Dj> findByGenreContainsIgnoreCase(String genre);
    Collection<Dj> findByNameContainsIgnoreCase(String name);
    Collection<Dj> findByNameContainsIgnoreCaseAndGenreContainsIgnoreCase(String name, String genre);
}