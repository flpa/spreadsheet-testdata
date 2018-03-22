package at.technikum.mse.est;

import java.lang.reflect.Field;

public class FieldLabelBuilder {
	public String build(Field f) {
		Label annotation = f.getAnnotation(Label.class);
		if (annotation != null) {
			return annotation.value();
		}
		return capitalizaFirstLetter(f.getName());
	}

	private String capitalizaFirstLetter(String fieldName) {
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
}
