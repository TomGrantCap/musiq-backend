package backend.Controllers;

import backend.Entities.DJ;
import backend.ErrorHandler.FieldErrorMessage;
import backend.Services.DJService;
import backend.dtos.DjDTO;
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

//Controller should only speak to service, not to repository.
@RestController
public class DJController {

    @Autowired
    DJService djService;

    //POST
    @PostMapping("/djs")
    DjDTO create(@RequestBody DJ dj){
        return djService.save(dj);
    }

    //GET
    @GetMapping("/djs")
    Iterable<DjDTO> read(){
        if (djService.findAll().iterator().hasNext()){
            return djService.findAll();
        }
        else throw new ValidationException("No records found.");
    }


    //PUT
    @PutMapping("/djs")
    ResponseEntity<DjDTO> update(@Valid @RequestBody DJ dj) {
            return new ResponseEntity<>(djService.save(dj), HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/djs/{id}")
    void delete(@PathVariable Long id){
        if (djService.findById(id).isPresent())
        {
            djService.deleteById(id);
        }
        else throw new ValidationException("ID is invalid");
    }

    //SEARCH FUNCTIONS
    @GetMapping("/djs/{id}")
    Optional<DjDTO> findByID(@PathVariable Long id) {
        if (djService.findById(id).isPresent())
        {
            return djService.findById(id);
        }
        else throw new ValidationException("ID is invalid");
    }


    @GetMapping("/djs/search")
    Iterable<DjDTO>findByQuery(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "genre", required = false) String genre)
    {
        if (name != null && genre != null){
            return djService.findByNameAndGenre(name, genre);
        }
        else if (name != null){
            return djService.findByName(name);
        }
        else if (genre != null){
            return djService.findByGenre(genre);
        }
        else {
            throw new ValidationException("No records found.");
        }
    }

    //Error handling
    @ExceptionHandler(ValidationException.class)
    ResponseEntity<String> exceptionHandler(ValidationException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    List<FieldErrorMessage>exceptionHandler(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<FieldErrorMessage> fieldErrorMessages = fieldErrors.stream().map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());
        return fieldErrorMessages;
    }
}
