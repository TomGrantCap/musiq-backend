package Backend.Repositories;

import Backend.Entities.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    Performance findPerformancesByGenreEqualsIgnoreCase(String genre);
    Performance findPerformancesByNameEqualsIgnoreCase(String name);
}

