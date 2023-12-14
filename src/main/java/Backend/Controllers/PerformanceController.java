package Backend.Controllers;
import Backend.Entities.Performance;
import Backend.Repositories.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PerformanceController {

    @Autowired
    private PerformanceRepository performanceRepository;

    @GetMapping("/performances")    //Get all DJs in DJ repository
    List<Performance> performances(){
        return performanceRepository.findAll();
    }
    @GetMapping("/performances/{id}")
    Optional<Performance> performance(@PathVariable Long id) {

        return performanceRepository.findById(id);
    }
    @PostMapping("/performances")
    Performance performance(@RequestBody Performance performance) {
        return performanceRepository.save(performance);
    }
}
