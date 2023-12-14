package Backend.Controllers;

import Backend.Entities.DJ;
import Backend.Repositories.DJRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DJController {

    @Autowired
    private DJRepository djRepository;

    @GetMapping("/djs")    //Get all DJs in DJ repository
    List<DJ> djs(){
        return djRepository.findAll();
    }
    @GetMapping("/djs/{id}")
    Optional<DJ> dj(@PathVariable Long id) {

        return djRepository.findById(id);
}
    @PostMapping("/djs")
    DJ dj(@RequestBody DJ dj) {
        return djRepository.save(dj);
    }
}
