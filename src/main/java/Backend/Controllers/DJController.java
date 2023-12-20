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

//    @GetMapping("/djs")    //Get all DJs in DJ repository
//    List<DJ> djs(){
//        return djRepository.findAll();
//    }

    @PostMapping("/djs")
    DJ create(@RequestBody DJ dj){
        return djRepository.save(dj);
    }
    @GetMapping("/djs")
    Iterable<DJ> read(){
        return djRepository.findAll();
    }

    @PutMapping("/djs")
    DJ update(@RequestBody DJ dj) {
        return djRepository.save(dj);
    }
    @DeleteMapping("/djs/{id}")
    void delete(@PathVariable Long id){
        djRepository.deleteById(id);
    }
    @GetMapping("/djs/{id}")
    Optional<DJ> findByID(@PathVariable Long id) {
        return djRepository.findById(id);
}

    @GetMapping("/djs/search")
    Iterable<DJ>findByQuery(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "genre", required = false) String genre)
    {
        if (name != null && genre != null){
            return djRepository.findByNameEqualsIgnoreCaseAndGenreEqualsIgnoreCase(name, genre);
        }
        else if (name != null){
            return djRepository.findDJsByNameEqualsIgnoreCase(name);
        }
        else if (genre != null){
            return djRepository.findDJsByGenreEqualsIgnoreCase(genre);
        }
        else{
            return djRepository.findAll();
    }

    }

//    @GetMapping("/djs/genre/{genre}")
//    Optional<DJ> DJGenre(@PathVariable String genre) {
//        return Optional.ofNullable(djRepository.findDJsByGenreEqualsIgnoreCase(genre));
//    }
//
//    @GetMapping("/djs/name/{name}")
//    Optional<DJ> DJName(@PathVariable String name) {
//        return Optional.ofNullable(djRepository.findDJsByNameEqualsIgnoreCase(name));
//    }
//
//    @GetMapping("/djs/length/{length}")
//    List<DJ> DJLength(@PathVariable int length) {
//        return djRepository.findDJIfNameLongerThan(length);
//    }

}
