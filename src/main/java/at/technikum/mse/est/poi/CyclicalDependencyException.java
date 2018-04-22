package at.technikum.mse.est.poi;

import at.technikum.mse.est.EstException;

public class CyclicalDependencyException extends EstException {
	private static final long serialVersionUID = -1374137670051418903L;

	public CyclicalDependencyException(String message) {
		super(message);
	}
}
