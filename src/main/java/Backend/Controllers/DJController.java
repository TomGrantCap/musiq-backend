package Backend.Controllers;

import Backend.Entities.DJ;
import Backend.ErrorHandler.FieldErrorMessage;
import Backend.Services.DJService;
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
public class DJController {

    @Autowired
    DJService djService;

    //POST
    @PostMapping("/djs")
    DJ create(@Valid @RequestBody DJ dj){
        return djService.Save(dj);
    }

    //GET
    @GetMapping("/djs")
    Iterable<DJ> read(){
        return djService.FindAll();
    }

    //PUT
    @PutMapping("/djs")
    ResponseEntity<DJ> update(@Valid @RequestBody DJ dj) {
            return new ResponseEntity<>(djService.Save(dj), HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/djs/{id}")
    void delete(@PathVariable Long id){
        if (djService.FindById(id).isPresent())
        {
            djService.DeleteById(id);
        }
        else throw new ValidationException("ID is invalid");
    }

    //SEARCH FUNCTIONS
    @GetMapping("/djs/{id}")
    Optional<DJ> findByID(@PathVariable Long id) {
        return djService.FindById(id);
}

    @GetMapping("/djs/search")
    Iterable<DJ>findByQuery(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "genre", required = false) String genre)
    {
        if (name != null && genre != null){
            return djService.FindByNameAndGenre(name, genre);
        }
        else if (name != null){
            return djService.FindByName(name);
        }
        else if (genre != null){
            return djService.FindByGenre(genre);
        }
        else {
            return djService.FindAll();
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
