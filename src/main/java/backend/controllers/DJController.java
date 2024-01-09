package backend.controllers;
import backend.errorHandler.DJException;
import backend.errorHandler.PerformanceException;
import backend.services.DJService;
import backend.dtos.DjDTO;
import backend.dtos.PerformanceDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
public class DJController {

    @Autowired
    DJService djService;

    //POST
    @PostMapping("/djs/post")
    DjDTO create(@Valid @RequestBody DjDTO djDto){
        if (djService.findByName(djDto.getName()).iterator().hasNext()){
            throw new PerformanceException("DJ of this name already exists.");
        }
        else{
            return djService.save(djDto);
        }
    }

    //GET
    @GetMapping("/djs")
    Iterable<DjDTO> read() throws DJException {
        if (djService.findAll().iterator().hasNext()){
            return djService.findAll();
        }
        else throw new DJException("No records found.");
    }

    //PUT
    @PutMapping("/djs/put")
    DjDTO update(@Valid @RequestBody DjDTO djDto) {
            return djService.save(djDto);
    }

    //DELETE
    @DeleteMapping("/djs/delete/{id}")
    void delete(@PathVariable Long id) {
        if (djService.findById(id).isPresent())
        {
            djService.deleteById(id);
        }
        else throw new DJException("No DJ with id " + id + " found");
    }

    //SEARCH FUNCTIONS

    //GET BY ID
    @GetMapping("/djs/getbyid/{id}")
    Optional<DjDTO> findByID(@PathVariable Long id) {
        if (djService.findById(id).isPresent())
        {
            return djService.findById(id);
        }
        else throw new DJException("No DJ with id " + id + " found");
    }

    //GET DJ'S PERFORMANCES
    @GetMapping("/djs/getperformances/{id}")
    Iterable<PerformanceDTO> findPerformances(@PathVariable Long id) {
        if (djService.findById(id).isPresent())
        {
            return djService.findPerformances(id);
        }
        else throw new DJException("No DJ with id " + id + " found");
    }

    //GET BY NAME AND/OR GENRE
    @GetMapping("/djs/search")
    Iterable<DjDTO>findByQuery(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "genre", required = false) String genre) {
        if (name != null && genre != null){

            Iterable<DjDTO> djs = djService.findByNameAndGenre(name, genre);

            if (djs.iterator().hasNext()){
                return djs;
            }
            else{
                throw new DJException("DJ with this name and genre not found.");
            }

        }
        else if (name != null){

            Iterable<DjDTO> djs = djService.findByName(name);

            if (djs.iterator().hasNext()){
                return djs;
            }
            else{
                throw new DJException("DJ with this name not found.");
            }
        }
        else if (genre != null){

            Iterable<DjDTO> djs = djService.findByGenre(genre);

            if (djs.iterator().hasNext()) {
                return djs;
            }
            else{
                throw new DJException("DJ with this genre not found.");
                }
        }

        else {
            throw new DJException("No records found.");
        }
    }

    //Error handling

    @ExceptionHandler(DJException.class)
    ResponseEntity<String>djExceptionHandler(DJException exception){
        return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<String> notValidExceptionHandler(MethodArgumentNotValidException exception){
        return new ResponseEntity<>(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }
}
