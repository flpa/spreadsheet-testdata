package at.technikum.mse.st;

/**
 * TypeMapper interface for implementing custom mapping functionality for different data types.
 *
 * In order to use a custom TypeMapper implement this interface ans register the TypeMapper
 * by calling {@link SpreadsheetTestdata#registerTypeMapper(TypeMapper, Class[])}.
 *
 * @param <T>	type that is handled by the TypeMapper.
 * @param <C>	{@link Context} used by the TypeMapper and its corresponding {@link FileMapper}.
 */
public interface TypeMapper<T, C extends Context> {

	/**
	 * Creates a column in the given {@link Context}.
	 *
	 * Creates a column in the given {@link Context} at the given index.
	 * If applicable any formatting or validation functionality should be implemented here.
	 *
	 * @param context	{@link Context} to create the column in.
	 * @param index		index to create the column at.
	 */
	void createColumn(C context, int index);

	/**
	 * Reads a value from the given {@link Context}.
	 *
	 * Reads a value from the given {@link Context} at the given row and column.
	 *
	 * @param context	{@link Context} to read the value from.
	 * @param row		row to read the value from.
	 * @param column	column to read the value from.
	 * @return the read value.
	 */
	T readValue(C context, int row, int column);

}
