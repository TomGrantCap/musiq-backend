package backend.ErrorHandler;

import org.springframework.http.HttpStatus;

public class DJException extends Exception {

    public DJException(String exception){
        super(exception);
    }
}
