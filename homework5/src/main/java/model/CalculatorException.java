package model;

public class CalculatorException extends RuntimeException {

    private static final long serialVersionUID = -1135355618665392597L;
    private static final String MESSAGE = "Ошибка вычисления!";

    public CalculatorException() {
        super(MESSAGE);
    }

    public CalculatorException(String str) {
        super(MESSAGE + " " + str);
    }

    public CalculatorException(Throwable e) {
        super(MESSAGE, e);
    }

    public CalculatorException(String str, Throwable e) {
        super(MESSAGE + " " + str, e);
    }
}
