package matrix;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Matrix
{
	public static final int MIN_SIDE = 0;
	public static final int DEFAULT_SIDE = 5;

	private ArrayList<ArrayList<Double>> matrix = new ArrayList<>();

	public Matrix()
	{
		this(DEFAULT_SIDE, DEFAULT_SIDE);
	}

	public Matrix(int pHeight, int pLenght)
	{
		if (validateSide(pHeight) && validateSide(pLenght))
		{
			createMatrix(pHeight, pLenght);
		}
	}

	public Matrix(Matrix copy)
	{
		// createMatrix(copy.getHeight(), copy.getLenght());
		for (int i = 0; i < copy.getHeight(); i++)
		{
			ArrayList<Double> arrayLenght = new ArrayList<>();
			for (int j = 0; j < copy.getLenght(); j++)
			{
				arrayLenght.add((double) copy.getContent(i, j));
			}
			matrix.add(arrayLenght);
		}
	}

	public int getHeight()
	{
		return matrix.size();
	}

	public int getLenght()
	{
		return matrix.get(0).size();
	}

	public int getSize()
	{
		return isSquare(this) ? getHeight() : 0;
	}

	private ArrayList<ArrayList<Double>> getMatrix()
	{
		return matrix;
	}

	private static boolean validateSide(int side)
	{
		return side > MIN_SIDE;
	}

	public void createMatrix(int height, int lenght)
	{
		for (int i = 0; i < height; i++)
		{
			ArrayList<Double> Matrixlength = new ArrayList<>();
			for (int j = 0; j < lenght; j++)
			{
				Matrixlength.add((double) 0);
			}
			matrix.add(Matrixlength);
		}
	}

	public void modifyHeight(int newHeight)
	{
		if (newHeight > matrix.size())
		{
			addHeight(newHeight);
		}
		else if (validateSide(newHeight))
		{
			removeHeight(newHeight);
		}
	}

	private void addHeight(int newHeight)
	{
		for (int i = matrix.size(); i < newHeight; i++)
		{
			ArrayList<Double> length = new ArrayList<>();
			for (int j = 0; j < matrix.get(0).size(); j++)
			{
				length.add((double) 0);
			}
			matrix.add(length);
		}
	}

	private void removeHeight(int newHeight)
	{
		// TODO can probably be removed if I use removeLine in a loop
		for (int i = matrix.size(); i > newHeight; i--)
		{
			matrix.remove(i - 1);
		}
	}

	private void removeLine(int lineRemove)
	{
		// TODO not tested
		matrix.remove(lineRemove);
	}

	public void modifyLenght(int newLenght)
	{
		if (newLenght > matrix.get(0).size())
		{
			addLenght(newLenght);
		}
		else if (validateSide(newLenght))
		{
			removeLenght(newLenght);
		}
	}

	private void addLenght(int newLenght)
	{
		for (int i = 0; i < matrix.size(); i++)
		{
			for (int j = matrix.get(i).size(); j < newLenght; j++)
			{
				matrix.get(i).add((double) 0);
			}
		}
	}

	private void removeLenght(int newLenght)
	{
		// TODO can probably be removed if I use removeColumn in a loop
		for (int i = 0; i < matrix.size(); i++)
		{
			for (int j = matrix.get(i).size(); j > newLenght; j--)
			{
				matrix.get(i).remove(j - 1);
			}
		}
	}

	private void removeColumn(int columnRemove)
	{
		// TODO not tested
		for (int i = 0; i < matrix.size(); i++)
		{
			matrix.get(i).remove(columnRemove);
		}
	}

	public void setContent(double content, int yPosition, int xPosition)
	{
		matrix.get(yPosition).set(xPosition, content);
	}

	public double getContent(int yPosition, int xPosition)
	{
		return matrix.get(yPosition).get(xPosition);
	}

	public boolean equals(Matrix otherMatrix)
	{
		return this.getHeight() == otherMatrix.getHeight()
				&& this.getLenght() == otherMatrix.getLenght();
	}

	public boolean canMultiply(Matrix otherMatrix)
	{
		return this.getHeight() == otherMatrix.getLenght()
				&& this.getLenght() == otherMatrix.getHeight();
	}

	public Matrix add(Matrix otherMatrix)
	{
		Matrix addedMatrix;
		if (this.equals(otherMatrix))
		{
			addedMatrix = new Matrix(this.getHeight(), this.getLenght());

			for (int i = 0; i < this.getHeight(); i++)
			{
				for (int j = 0; j < this.getLenght(); j++)
				{
					addedMatrix.setContent(this.getContent(i, j)
							+ otherMatrix.getContent(i, j), i, j);
				}
			}
		}
		else
		{
			// TODO
			addedMatrix = null;
		}
		return addedMatrix;
	}

	public Matrix substract(Matrix otherMatrix)
	{
		Matrix substractedMatrix;
		if (this.equals(otherMatrix))
		{
			substractedMatrix = new Matrix(this.getHeight(), this.getLenght());

			for (int i = 0; i < this.getHeight(); i++)
			{
				for (int j = 0; j < this.getLenght(); j++)
				{
					substractedMatrix.setContent(this.getContent(i, j)
							- otherMatrix.getContent(i, j), i, j);
				}
			}
		}
		else
		{
			// TODO
			substractedMatrix = null;
		}
		return substractedMatrix;
	}

	private double Multiply(Matrix otherMatrix, int height, int lenght)
	{
		double content = 0;
		for (int i = 0; i < this.getHeight(); i++)
		{
			content += this.getContent(height, i)
					* otherMatrix.getContent(i, lenght);
		}

		return content;
	}

	public Matrix Multiply(Matrix otherMatrix)
	{
		Matrix multiplayedMatrix;
		if (canMultiply(otherMatrix))
		{
			multiplayedMatrix = new Matrix(this.getHeight(),
					otherMatrix.getLenght());
			for (int i = 0; i < this.getHeight(); i++)
			{
				for (int j = 0; j < otherMatrix.getLenght(); j++)
				{
					multiplayedMatrix.setContent(Multiply(otherMatrix, i, j), i,
							j);
				}
			}
		}
		else
		{
			multiplayedMatrix = null;
		}
		return multiplayedMatrix;
	}

	public static Matrix multiplyByInt(Matrix currentMatrix, double number)
	{
		Matrix newMatrix = new Matrix(currentMatrix);

		for (int i = 0; i < currentMatrix.getHeight(); i++)
		{
			for (int j = 0; j < currentMatrix.getLenght(); j++)
			{
				double content =  (currentMatrix.getContent(i, j)*number);
				newMatrix.setContent(content,
						i, j);
			}
		}
		return newMatrix;
	}

	public static boolean isSquare(Matrix currentMatrix)
	{
		return currentMatrix.getHeight() == currentMatrix.getLenght();
	}

	private static double calculate2x2Determinant(Matrix currentMatrix)
	{
		return currentMatrix.getContent(0, 0) * currentMatrix.getContent(1, 1)
				- currentMatrix.getContent(0, 1)
						* currentMatrix.getContent(1, 0);
	}

	private static Matrix trimMatrix(Matrix currentMatrix, int yPosition,
			int xPosition)
	{
		Matrix trimedMatrix = new Matrix(currentMatrix);
		trimedMatrix.removeColumn(xPosition);
		trimedMatrix.removeLine(yPosition);

		return trimedMatrix;
	}

	private static double calculateDeterminant(ArrayList<Double> multiplier,
			ArrayList<Double> foundDeterminant)
	{
		double determinant = 0;

		for (int i = 0; i < multiplier.size(); i++)
		{
			determinant += multiplier.get(i) * foundDeterminant.get(i);
		}

		return determinant;
	}

	private static double calculateDeterminant(Matrix currentMatrix)
	{
		double determinant = 0;

		ArrayList<Double> multiplier = new ArrayList<>();
		ArrayList<Double> foundDeterminant = new ArrayList<>();
		ArrayList<Matrix> trimedMatrix = new ArrayList<>();

		if (currentMatrix.getSize() == 2)
		{
			determinant = calculate2x2Determinant(currentMatrix);
		}
		else
		{
			for (int i = 0; i < currentMatrix.getSize(); i++)
			{
				double integer = i % 2 == 0 ? currentMatrix.getContent(0, i)
						: -1 * currentMatrix.getContent(0, i);
				multiplier.add(integer);
				trimedMatrix.add(trimMatrix(currentMatrix, 0, i));
				if (trimedMatrix.get(i).getSize() != 1)
				{
					foundDeterminant
							.add(calculateDeterminant(trimedMatrix.get(i)));
				}
			}
			determinant = calculateDeterminant(multiplier, foundDeterminant);
		}
		return determinant;
	}

	public static double getDeterminant(Matrix currentMatrix)
	{
		double determinant = 0;
		if (isSquare(currentMatrix))
		{
			determinant = calculateDeterminant(currentMatrix);
		}

		return determinant;
	}

	public static Matrix getAdjugate(Matrix currentMatrix)
	{
		Matrix adjugate = null;
		if (isSquare(currentMatrix))
		{
			adjugate = new Matrix(currentMatrix.getSize(),
					currentMatrix.getSize());
			for (int i = 0; i < adjugate.getSize(); i++)
			{
				for (int j = 0; j < adjugate.getSize(); j++)
				{
					double content = (i + j) % 2 == 0
							? getDeterminant(trimMatrix(currentMatrix, i, j))
							: -1 * getDeterminant(
									trimMatrix(currentMatrix, i, j));
					adjugate.setContent(content, j, i);
				}
			}

		}

		return adjugate;
	}

	public static Matrix invertMatrix(Matrix currentMatrix)
	{
		Matrix invert = null;
		double determinant = getDeterminant(currentMatrix);
		if (isSquare(currentMatrix) && determinant != 0)
		{
			invert = new Matrix(
					multiplyByInt(getAdjugate(currentMatrix), 1/determinant));
		}
		return invert;

	}

	public String toString()
	{
		DecimalFormat df = new DecimalFormat("#.##");
		String matrixDisplay = "";
		for (int i = 0; i < matrix.size(); i++)
		{
			for (int j = 0; j < matrix.get(0).size(); j++)
			{
				matrixDisplay += df.format(matrix.get(i).get(j)) + ", ";
			}
			matrixDisplay += "\n";
		}
		return matrixDisplay;
	}
}
