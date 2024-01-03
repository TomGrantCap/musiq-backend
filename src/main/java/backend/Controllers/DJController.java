package backend.Controllers;

import backend.Entities.DJ;
import backend.ErrorHandler.DJException;
import backend.Services.DJService;
import backend.dtos.DjDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
public class DJController {

    @Autowired
    DJService djService;

    //POST
    @PostMapping("/djs/post")
    DjDTO create(@RequestBody DJ dj){
        return djService.save(dj);
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
    DjDTO update(@Valid @RequestBody DJ dj) {
            return djService.save(dj);
    }

    //DELETE
    @DeleteMapping("/djs/delete/{id}")
    void delete(@PathVariable Long id) throws DJException {
        if (djService.findById(id).isPresent())
        {
            djService.deleteById(id);
        }
        else throw new DJException("ID is invalid");
    }

    //SEARCH FUNCTIONS
    @GetMapping("/djs/getbyid/{id}")
    Optional<DjDTO> findByID(@PathVariable Long id) throws DJException {
        if (djService.findById(id).isPresent())
        {
            return djService.findById(id);
        }
        else throw new DJException("ID is invalid");
    }

    @GetMapping("/djs/search")
    Iterable<DjDTO>findByQuery(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "genre", required = false) String genre) throws DJException {
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
            throw new DJException("No records found.");
        }
    }

    //Error handling

    @ExceptionHandler(DJException.class)
    ResponseEntity<String>exceptionHandler(DJException exception){
        return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    List<FieldErrorMessage>exceptionHandler(MethodArgumentNotValidException e){
//        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
//        List<FieldErrorMessage> fieldErrorMessages = fieldErrors.stream().map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());
//        return fieldErrorMessages;
//    }
}
