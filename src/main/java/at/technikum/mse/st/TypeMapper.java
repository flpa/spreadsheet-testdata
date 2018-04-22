package at.technikum.mse.st;

public interface TypeMapper<T, C extends Context> {

	void createColumn(C context, int index);

	T readValue(C context, int row, int column);

}
