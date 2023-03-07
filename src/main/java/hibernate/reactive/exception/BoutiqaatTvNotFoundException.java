package hibernate.reactive.exception;

public class BoutiqaatTvNotFoundException extends RuntimeException{

    private String message;

    public BoutiqaatTvNotFoundException(String message){
        super(message);
        this.message = message;
    }
}
