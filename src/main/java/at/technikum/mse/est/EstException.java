package at.technikum.mse.est;

public class EstException extends Exception {
	private static final long serialVersionUID = 5196609971878813133L;

	public EstException(String message) {
		super(message);
	}

	public EstException(String message, Exception e) {
		super(message, e);
	}
}
