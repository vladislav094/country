package guru.qa.country.exception;

public class CountryNotFoundException extends RuntimeException {

    public CountryNotFoundException(){};

    public CountryNotFoundException(String message) {
        super(message);
    }
}
