package backend.ErrorHandler;

import org.springframework.http.HttpStatus;

public class PerformanceException extends Exception {

    public PerformanceException(String exception){
        super(exception);
    }
}
