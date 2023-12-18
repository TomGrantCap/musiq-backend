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

    @GetMapping("/djs/id/{id}")
    Optional<DJ> dj(@PathVariable Long id) {
        return djRepository.findById(id);
}
    @GetMapping("/djs/genre/{genre}")
    Optional<DJ> DJGenre(@PathVariable String genre) {
        return Optional.ofNullable(djRepository.findDJsByGenreEqualsIgnoreCase(genre));
    }

    @GetMapping("/djs/name/{name}")
    Optional<DJ> DJName(@PathVariable String name) {
        return Optional.ofNullable(djRepository.findDJsByNameEqualsIgnoreCase(name));
    }

    @GetMapping("/djs/length/{length}")
    List<DJ> DJLength(@PathVariable int length) {
        return djRepository.findDJIfNameLongerThan(length);
    }

    @PostMapping("/djs")
    DJ dj(@RequestBody DJ dj) {
        return djRepository.save(dj);
    }
}
