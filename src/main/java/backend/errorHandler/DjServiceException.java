package backend.errorHandler;

public class DjServiceException extends RuntimeException {

    public DjServiceException(String exception){
        super(exception);
    }
}
