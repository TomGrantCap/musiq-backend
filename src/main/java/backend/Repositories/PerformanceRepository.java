package backend.Repositories;

import backend.Entities.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    Collection<Performance> findPerformancesByGenreEqualsIgnoreCase(String genre);
    Collection<Performance> findPerformancesByNameEqualsIgnoreCase(String name);
    Collection<Performance> findByNameEqualsIgnoreCaseAndGenreEqualsIgnoreCase(String name, String genre);

}

