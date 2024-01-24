package backend.controllers;
import backend.errorHandler.DjServiceException;
import backend.errorHandler.PerformanceServiceException;
import backend.services.PerformanceService;
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
public class PerformanceController {

    final PerformanceService performanceService;
    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    //POST
    @PostMapping("/performances/post")
    PerformanceDto create(@RequestBody PerformanceDto performanceDTO){
        return performanceService.save(performanceDTO);

    }

    //GET
    @GetMapping("/performances")
    Iterable<PerformanceDto> read(){
        if (performanceService.findAll().iterator().hasNext()){
            return performanceService.findAll();
        }
        else throw new PerformanceServiceException("No records found.");
    }

    //PUT
    @PutMapping("/performances/put")
    PerformanceDto update(@Valid @RequestBody PerformanceDto performanceDTO) {

        return performanceService.save(performanceDTO);
    }

    //DELETE BY ID
    @DeleteMapping("/performances/delete/{id}")
    void delete(@PathVariable Long id){
        if (performanceService.findById(id).isPresent())
        {
            performanceService.deleteById(id);
        }
        else throw new PerformanceServiceException("No performance with id " + id + " found");
    }

    //SEARCH FUNCTIONS

    //FIND BY ID
    @GetMapping("/performances/getbyid/{id}")
    Optional<PerformanceDto> findByID(@PathVariable Long id){
        if (performanceService.findById(id).isPresent())
        {
            return performanceService.findById(id);
        }
        else throw new PerformanceServiceException("No performance with ID " + id + " found.");
    }

    //FIND PERFORMANCE'S DJS
    @GetMapping("/performances/getdjs/{id}")
    Iterable<DjDto> findPerformances(@PathVariable Long id){
        if (performanceService.findById(id).isPresent())
        {
            return performanceService.findDJs(id);
        }
        else throw new DjServiceException("No performance with ID" + id + " found.");
    }

    //FIND BY NAME AND/OR GENRE
    @GetMapping("/performances/search")
    Iterable<PerformanceDto>findByQuery(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "genre", required = false) String genre) {
        if (name != null && genre != null){

            Iterable<PerformanceDto> performances = performanceService.findByNameAndGenre(name, genre);

            if (performances.iterator().hasNext()){
                return performances;
            }
            else{
                throw new PerformanceServiceException("Performance with this name and genre not found.");
            }

        }
        else if (name != null){

            Iterable<PerformanceDto> performances = performanceService.findByName(name);

            if (performances.iterator().hasNext()){
                return performances;
            }
            else{
                throw new PerformanceServiceException("Performance with this name not found.");
            }
        }
        else if (genre != null){

            Iterable<PerformanceDto> performances = performanceService.findByGenre(genre);

            if (performances.iterator().hasNext()) {
                return performances;
            }
            else{
                throw new PerformanceServiceException("Performance with this genre not found.");
            }
        }

        else {
            throw new PerformanceServiceException("No records found.");
        }
    }

    //GET SINGLE PERFORMANCE BY NAME
    @GetMapping("/performances/singleperformancebyname/{name}")
    PerformanceDto findSinglePerformanceByName(@PathVariable String name){
        return performanceService.findSinglePerformanceByName(name);
    }

    //Error handling
    @ExceptionHandler(PerformanceServiceException.class)
    ResponseEntity<String> exceptionHandler(PerformanceServiceException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<String> notValidExceptionHandler(MethodArgumentNotValidException exception){
        return new ResponseEntity<>(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }
}
