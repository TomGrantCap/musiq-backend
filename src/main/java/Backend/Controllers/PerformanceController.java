package Backend.Controllers;

import Backend.Entities.Performance;
import Backend.Repositories.PerformanceRepository;
import Backend.Services.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
public class PerformanceController {

    @Autowired
    PerformanceService performanceService;

    //POST
    @PostMapping("/performances")
    Performance create(@RequestBody Performance performance){
        return performanceService.Save(performance);
    }

    //GET
    @GetMapping("/performances")
    Iterable<Performance> read(){
        return performanceService.FindAll();
    }

    //PUT
    @PutMapping("/performances")
    Performance update(@RequestBody Performance performance) {
        return performanceService.Save(performance);
    }

    //DELETE
    @DeleteMapping("/performances/{id}")
    void delete(@PathVariable Long id){
        performanceService.DeleteById(id);
    }

    //SEARCH FUNCTIONS
    //Find by ID
    @GetMapping("/performances/{id}")
    Optional<Performance> findByID(@PathVariable Long id) {
        return performanceService.FindById(id);
    }

    //Find by name and/or genre
    @GetMapping("/performances/search")
    Iterable<Performance>findByQuery(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "genre", required = false) String genre)
    {
        if (name != null && genre != null){
            return performanceService.FindByNameAndGenre(name, genre);
        }
        else if (name != null){
            return performanceService.FindByName(name);
        }
        else if (genre != null){
            return performanceService.FindByGenre(genre);
        }
        else{
            return performanceService.FindAll();
        }
    }
}
