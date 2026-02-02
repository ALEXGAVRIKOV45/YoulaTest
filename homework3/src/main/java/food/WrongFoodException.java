package food;

public class WrongFoodException extends Exception {

    private static final long serialVersionUID = -3969774467411238049L;

    private static final String MESSAGE = "The type of food isn't appropriate to the animal!";

    public WrongFoodException() {
        super(MESSAGE);
    }

    public WrongFoodException(String str) {
        super(MESSAGE + " " + str);
    }

}
