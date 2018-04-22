package at.technikum.mse.st;

public class CyclicalDependencyException extends StException {
	private static final long serialVersionUID = -1374137670051418903L;

	public CyclicalDependencyException(String message) {
		super(message);
	}
}
