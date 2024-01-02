package backend.Repositories;

import java.util.ArrayList;
import java.util.Collection;
import  java.util.List;
import backend.Entities.DJ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DJRepository extends JpaRepository<DJ, Long> {

    Collection<DJ> findDJsByGenreEqualsIgnoreCase(String genre);
    Collection<DJ> findDJsByNameEqualsIgnoreCase(String name);
    Collection<DJ> findByNameEqualsIgnoreCaseAndGenreEqualsIgnoreCase(String name, String genre);
}