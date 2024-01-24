package backend.errorHandler;

public class PerformanceServiceException extends RuntimeException {

    public PerformanceServiceException(String exception){
        super(exception);
    }
}
