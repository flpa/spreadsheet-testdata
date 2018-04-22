package at.technikum.mse.est.poi;

import at.technikum.mse.est.EstException;

import java.lang.reflect.Field;

public class FieldMappingException extends EstException {
	private static final long serialVersionUID = -3968139834338449737L;

	public FieldMappingException(Field field) {
		super("Error mapping field " + field.getName() + ": No TypeMapper found for type " + field.getType().getName() + " and field was not annotated with the @Flatten annotation.");
	}
}
