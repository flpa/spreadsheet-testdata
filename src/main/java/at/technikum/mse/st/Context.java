package at.technikum.mse.st;

/**
 * Context interface for implementing a custom Context used by a corresponding {@link FileMapper}.
 *
 * Implement this interface when implementing a custom {@link FileMapper}.
 * Use it to pass objects that are needed for writing or reading a testdata file
 * from a {@link FileMapper} to a corresponding {@link TypeMapper}.
 */
public interface Context {
	// This is only a marker interface right now
}
