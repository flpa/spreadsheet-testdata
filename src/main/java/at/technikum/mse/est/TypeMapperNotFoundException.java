package at.technikum.mse.est;

public class TypeMapperNotFoundException extends RuntimeException {
    static final long serialVersionUID = -3717824592863609229L;

    public TypeMapperNotFoundException(Class<?> type) {
        super("No TypeMapper found for Type " + type);
    }
}