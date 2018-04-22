package at.technikum.mse.st;

import java.lang.reflect.Field;

public class DuplicateLabelException extends StException {
	private static final long serialVersionUID = -4323138601678051518L;

	public DuplicateLabelException(Field field, String label) {
		super("Error creating label for field " + field.getName() + ": Label '" + label + "' already exists.");
	}
}
