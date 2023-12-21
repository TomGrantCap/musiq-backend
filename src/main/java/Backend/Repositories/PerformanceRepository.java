package Backend.Repositories;

import Backend.Entities.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    Iterable<Performance> findPerformancesByGenreEqualsIgnoreCase(String genre);
    Iterable<Performance> findPerformancesByNameEqualsIgnoreCase(String name);
    Iterable<Performance> findByNameEqualsIgnoreCaseAndGenreEqualsIgnoreCase(String name, String genre);

}

