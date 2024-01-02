package backend.Controllers;

import backend.Entities.Performance;
import backend.ErrorHandler.FieldErrorMessage;
import backend.Services.PerformanceService;
import backend.dtos.PerformanceDTO;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PerformanceController {

    @Autowired
    PerformanceService performanceService;

    //POST
    @PostMapping("/performances")
    PerformanceDTO create(@RequestBody Performance performance){
        return performanceService.save(performance);
    }

    //GET
    @GetMapping("/performances")
    Iterable<PerformanceDTO> read(){
        return performanceService.findAll();
    }

    //PUT
    @PutMapping("/performances")
    ResponseEntity<PerformanceDTO> update(@Valid @RequestBody Performance performance) {
        return new ResponseEntity<>(performanceService.save(performance), HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/performances/{id}")
    void delete(@PathVariable Long id){
        if (performanceService.findById(id).isPresent())
        {
            performanceService.deleteById(id);
        }
        else throw new ValidationException("ID is invalid");
    }

    //SEARCH FUNCTIONS
    //Find by ID
    @GetMapping("/performances/{id}")
    Optional<PerformanceDTO> findByID(@PathVariable Long id) {
        return performanceService.findById(id);
    }

    //Find by name and/or genre
    @GetMapping("/performances/search")
    Iterable<PerformanceDTO>findByQuery(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "genre", required = false) String genre)
    {
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
            return performanceService.findAll();
        }
    }

    //Error handling
    @ExceptionHandler(ValidationException.class)
    ResponseEntity<String> exceptionHandler(ValidationException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<FieldErrorMessage> fieldErrorMessages = fieldErrors.stream().map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());
        return fieldErrorMessages;
    }
}
