package backend.controllers;
import backend.errorHandler.DjServiceException;
import backend.services.DjService;
import backend.dtos.DjDto;
import backend.dtos.PerformanceDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class DjController {

    final DjService djService;

    String idErrorMessage = "No DJ with ID %s found.";

    public DjController(DjService djService) {
        this.djService = djService;
    }

    //POST
    @PostMapping("/djs/post")
    DjDto create(@Valid @RequestBody DjDto djDto){
            return djService.save(djDto);
    }

    //GET
    @GetMapping("/djs")
    Iterable<DjDto> read() throws DjServiceException {
        if (djService.findAll().iterator().hasNext()){
            return djService.findAll();
        }
        else throw new DjServiceException("No records found.");
    }

    //PUT
    @PutMapping("/djs/put")
    DjDto update(@Valid @RequestBody DjDto djDto) {
            return djService.save(djDto);
    }

    //DELETE
    @DeleteMapping("/djs/delete/{id}")
    void delete(@PathVariable Long id) {
        if (djService.findById(id).isPresent())
        {
            djService.deleteById(id);
        }
        else throw new DjServiceException(String.format(idErrorMessage, id));
    }

    //SEARCH FUNCTIONS

    //GET BY ID
    @GetMapping("/djs/getbyid/{id}")
    Optional<DjDto> findByID(@PathVariable Long id) {
        if (djService.findById(id).isPresent())
        {
            return djService.findById(id);
        }
        else throw new DjServiceException(String.format(idErrorMessage, id));
    }

    //GET DJ'S PERFORMANCES
    @GetMapping("/djs/getperformances/{id}")
    Iterable<PerformanceDto> findPerformances(@PathVariable Long id) {
        if (djService.findById(id).isPresent())
        {
            return djService.findPerformances(id);
        }
        else throw new DjServiceException(String.format(idErrorMessage, id));
    }

    //GET BY NAME AND/OR GENRE
    @GetMapping("/djs/search")
    Iterable<DjDto>findByQuery(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "genre", required = false) String genre) {
        if (name != null && genre != null){

            Iterable<DjDto> djs = djService.findByNameAndGenre(name, genre);

            if (djs.iterator().hasNext()){
                return djs;
            }
            else{
                throw new DjServiceException("DJ with this name and genre not found.");
            }

        }
        else if (name != null){

            Iterable<DjDto> djs = djService.findByName(name);

            if (djs.iterator().hasNext()){
                return djs;
            }
            else{
                throw new DjServiceException("DJ with this name not found.");
            }
        }
        else if (genre != null){

            Iterable<DjDto> djs = djService.findByGenre(genre);

            if (djs.iterator().hasNext()) {
                return djs;
            }
            else{
                throw new DjServiceException("DJ with this genre not found.");
                }
        }

        else {
            throw new DjServiceException("No records found.");
        }
    }

    //Error handling

    @ExceptionHandler(DjServiceException.class)
    ResponseEntity<String>djExceptionHandler(DjServiceException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<String> notValidExceptionHandler(MethodArgumentNotValidException exception){
        return new ResponseEntity<>(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }
}
