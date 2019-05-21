package spell.dict;

public class DictionaryException extends Exception {
    public DictionaryException(String message) {
        super(message);
    }

    public DictionaryException(Throwable cause) {
        super(cause);
    }
}
