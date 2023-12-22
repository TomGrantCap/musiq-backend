package Backend.Controllers;

import Backend.Entities.Performance;
import Backend.ErrorHandler.FieldErrorMessage;
import Backend.Repositories.PerformanceRepository;
import Backend.Services.PerformanceService;
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
    Performance create(@Valid @RequestBody Performance performance){
        return performanceService.Save(performance);
    }

    //GET
    @GetMapping("/performances")
    Iterable<Performance> read(){
        return performanceService.FindAll();
    }

    //PUT
    @PutMapping("/performances")
    Performance update(@Valid @RequestBody Performance performance) {
        return performanceService.Save(performance);
    }

    //DELETE
    @DeleteMapping("/performances/{id}")
    void delete(@PathVariable Long id){
        if (performanceService.FindById(id).isPresent())
        {
            performanceService.DeleteById(id);
        }
        else throw new ValidationException("ID is invalid");
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
