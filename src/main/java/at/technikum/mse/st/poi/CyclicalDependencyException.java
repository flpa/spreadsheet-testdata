package at.technikum.mse.st.poi;

import at.technikum.mse.st.StException;

public class CyclicalDependencyException extends StException {
	private static final long serialVersionUID = -1374137670051418903L;

	public CyclicalDependencyException(String message) {
		super(message);
	}
}
