package backend.repositories;

import backend.entities.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    Performance findPerformanceByNameIgnoreCase(String name);
    Collection<Performance> findPerformancesByGenreContainsIgnoreCase(String genre);
    Collection<Performance> findPerformancesByNameContainsIgnoreCase(String name);
    Collection<Performance> findByNameContainsIgnoreCaseAndGenreContainsIgnoreCase(String name, String genre);

}

