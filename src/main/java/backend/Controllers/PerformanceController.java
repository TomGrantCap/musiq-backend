package backend.Controllers;

import backend.Entities.Performance;
import backend.ErrorHandler.PerformanceException;
import backend.Services.PerformanceService;
import backend.dtos.PerformanceDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
public class PerformanceController {

    @Autowired
    PerformanceService performanceService;

    //POST
    @PostMapping("/performances/post")
    PerformanceDTO create(@RequestBody Performance performance){
        return performanceService.save(performance);
    }

    //GET
    @GetMapping("/performances")
    Iterable<PerformanceDTO> read() throws PerformanceException {
        if (performanceService.findAll().iterator().hasNext()){
            return performanceService.findAll();
        }
        else throw new PerformanceException("No records found.");
    }

    //PUT
    @PutMapping("/performances/put")
    PerformanceDTO update(@Valid @RequestBody Performance performance) {
        return performanceService.save(performance);
    }

    //DELETE
    @DeleteMapping("/performances/delete/{id}")
    void delete(@PathVariable Long id) throws PerformanceException {
        if (performanceService.findById(id).isPresent())
        {
            performanceService.deleteById(id);
        }
        else throw new PerformanceException("ID is invalid");
    }

    //SEARCH FUNCTIONS
    //Find by ID
    @GetMapping("/performances/getbyid/{id}")
    Optional<PerformanceDTO> findByID(@PathVariable Long id) throws PerformanceException {
        if (performanceService.findById(id).isPresent())
        {
            return performanceService.findById(id);
        }
        else throw new PerformanceException("ID is invalid");
    }

    //Find by name and/or genre
    @GetMapping("/performances/search")
    Iterable<PerformanceDTO>findByQuery(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "genre", required = false) String genre) throws PerformanceException {
        if (name != null && genre != null){
            return performanceService.findByNameAndGenre(name, genre);
        }
        else if (name != null){
            return performanceService.findByName(name);
        }
        else if (genre != null){
            return performanceService.findByGenre(genre);
        }
        else{
            throw new PerformanceException("No records found.");
        }
    }

    //Error handling
    @ExceptionHandler(PerformanceException.class)
    ResponseEntity<String> exceptionHandler(PerformanceException exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException e){
//        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
//        List<FieldErrorMessage> fieldErrorMessages = fieldErrors.stream().map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());
//        return fieldErrorMessages;
//    }
}
