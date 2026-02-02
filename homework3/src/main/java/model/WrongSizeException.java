package model;

public class WrongSizeException extends RuntimeException {

    private static final long serialVersionUID = -8666069778172658681L;

    private static final String MESSAGE = "The size of aviary isn't appropriate to the animal!";

    public WrongSizeException() {
        super(MESSAGE);
    }

    public WrongSizeException(String str) {
        super(MESSAGE + " " + str);
    }

}
