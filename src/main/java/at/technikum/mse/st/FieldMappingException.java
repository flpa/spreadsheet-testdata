package at.technikum.mse.st;

import java.lang.reflect.Field;

public class FieldMappingException extends StException {
	private static final long serialVersionUID = -3968139834338449737L;

	public FieldMappingException(Field field) {
		super("Error mapping field " + field.getName() + ": No TypeMapper found for type " + field.getType().getName() + " and field was not annotated with the @Flatten annotation.");
	}
}
