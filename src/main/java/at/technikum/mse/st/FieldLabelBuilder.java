package at.technikum.mse.st;

import java.lang.reflect.Field;

public class FieldLabelBuilder {
	public String build(Field f) {
		Label annotation = f.getAnnotation(Label.class);
		if (annotation != null) {
			return annotation.value();
		}
		return capitalizeFirstLetter(f.getName());
	}

	private String capitalizeFirstLetter(String fieldName) {
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
}
