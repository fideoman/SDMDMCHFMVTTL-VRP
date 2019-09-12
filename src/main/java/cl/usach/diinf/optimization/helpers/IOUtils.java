package cl.usach.diinf.optimization.helpers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math4.linear.MatrixUtils;
import org.apache.commons.math4.linear.RealMatrix;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides file-based IO utilities for matrices.
 */
public class IOUtils {

	/**
	 * Reads a matrix of double values from the filepath provided.
	 *
	 * @param filepath The path of the file which the matrix will be read from
	 * @return A matrix
	 * @throws IOException As thrown by file reader and CSV parser.
	 */
	public static RealMatrix readMatrix(String filepath) throws IOException {
		// Create a file reader and call the actual implementation:
		return IOUtils.readMatrix(new FileReader(filepath));
	}

	/**
	 * Reads a matrix of double values from the reader provided.
	 *
	 * @param reader The reader which the values to be read from.
	 * @return A matrix
	 * @throws IOException As thrown by the CSV parser.
	 */
	public static RealMatrix readMatrix(Reader reader) throws IOException {
		// Initialize the return value:
		List<double[]> retval = new ArrayList<>();

		// Parse and get the iterarable:
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);

		// Iterate over the records and populate return value:
		for (CSVRecord record : records) {
			double[] row = new double[record.size()];
			for (int i = 0; i < record.size(); i++) {
				row[i] = Double.parseDouble(record.get(i));
			}
			retval.add(row);
		}

		// Convert the list to an array:
		double[][] retvalArray = new double[retval.size()][];
		retval.toArray(retvalArray);

		// Done, return the array:
		return MatrixUtils.createRealMatrix(retvalArray);
	}

}