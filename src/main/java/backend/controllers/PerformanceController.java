package backend.controllers;
import backend.errorHandler.DJException;
import backend.errorHandler.PerformanceException;
import backend.services.PerformanceService;
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
public class PerformanceController {

    @Autowired
    PerformanceService performanceService;

    //POST
    @PostMapping("/performances/post")
    PerformanceDTO create(@RequestBody PerformanceDTO performanceDTO){
        if (performanceService.findByName(performanceDTO.getName()).iterator().hasNext()){
            throw new PerformanceException("Performance of this name already exists.");
        }
        else{
        return performanceService.save(performanceDTO);
        }
    }

    //GET
    @GetMapping("/performances")
    Iterable<PerformanceDTO> read(){
        if (performanceService.findAll().iterator().hasNext()){
            return performanceService.findAll();
        }
        else throw new PerformanceException("No records found.");
    }

    //PUT
    @PutMapping("/performances/put")
    PerformanceDTO update(@Valid @RequestBody PerformanceDTO performanceDTO) {

        return performanceService.save(performanceDTO);
    }

    //DELETE BY ID
    @DeleteMapping("/performances/delete/{id}")
    void delete(@PathVariable Long id){
        if (performanceService.findById(id).isPresent())
        {
            performanceService.deleteById(id);
        }
        else throw new PerformanceException("No performance with id " + id + " found");
    }

    //SEARCH FUNCTIONS

    //FIND BY ID
    @GetMapping("/performances/getbyid/{id}")
    Optional<PerformanceDTO> findByID(@PathVariable Long id){
        if (performanceService.findById(id).isPresent())
        {
            return performanceService.findById(id);
        }
        else throw new PerformanceException("No performance with ID " + id + " found.");
    }

    //FIND PERFORMANCE'S DJS
    @GetMapping("/performances/getdjs/{id}")
    Iterable<DjDTO> findPerformances(@PathVariable Long id){
        if (performanceService.findById(id).isPresent())
        {
            return performanceService.findDJs(id);
        }
        else throw new DJException("No performance with ID" + id + " found.");
    }

    //FIND BY NAME AND/OR GENRE
    @GetMapping("/performances/search")
    Iterable<PerformanceDTO>findByQuery(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "genre", required = false) String genre) {
        if (name != null && genre != null){

            Iterable<PerformanceDTO> performances = performanceService.findByNameAndGenre(name, genre);

            if (performances.iterator().hasNext()){
                return performances;
            }
            else{
                throw new PerformanceException("Performance with this name and genre not found.");
            }

        }
        else if (name != null){

            Iterable<PerformanceDTO> performances = performanceService.findByName(name);

            if (performances.iterator().hasNext()){
                return performances;
            }
            else{
                throw new PerformanceException("Performance with this name not found.");
            }
        }
        else if (genre != null){

            Iterable<PerformanceDTO> performances = performanceService.findByName(genre);

            if (performances.iterator().hasNext()) {
                return performances;
            }
            else{
                throw new PerformanceException("Performance with this genre not found.");
            }
        }

        else {
            throw new PerformanceException("No records found.");
        }
    }

    //GET SINGLE PERFORMANCE BY NAME
    @GetMapping("/performances/singleperformancebyname/{name}")
    PerformanceDTO findSinglePerformanceByName(@PathVariable String name){
        return performanceService.findSinglePerformanceByName(name);
    }

    //Error handling
    @ExceptionHandler(PerformanceException.class)
    ResponseEntity<String> exceptionHandler(PerformanceException exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<String> notValidExceptionHandler(MethodArgumentNotValidException exception){
        return new ResponseEntity<>(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }
}
