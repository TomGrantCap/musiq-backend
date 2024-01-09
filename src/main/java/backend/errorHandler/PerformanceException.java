package backend.errorHandler;

public class PerformanceException extends RuntimeException {

    public PerformanceException(String exception){
        super(exception);
    }
}
