package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import matrix.Matrix;

public class Application
{

	static ArrayList<Matrix> matrixList = new ArrayList<>();
	static Scanner input = new Scanner(System.in);

	public static boolean hasMatrix()
	{
		return matrixList.size() != 0;
	}

	//TODO remove this line (test for gitHub)
	public static void createMatrix()
	{
		System.out.println("Choose the height of your matrix");
		int height = input.nextInt();
		System.out.println("Choose the lenght of your matrix");
		int lenght = input.nextInt();

		matrixList.add(new Matrix(height, lenght));
	}

	public static void removeMatrix()
	{
		System.out.println("What matrix do you want to remove?");
		displayAll();
		int toRemove = askMatrix();

		matrixList.remove(toRemove);

	}

	public static int askMatrix()
	{
		int choice = 0;
		boolean retry;
		do
		{
			try
			{
				input.nextLine();
				choice = input.nextInt();
				retry = false;
			}
			catch (InputMismatchException e)
			{
				retry = true;
			}

		}
		while (retry || choice < 1 || choice > matrixList.size());
		return choice - 1;
	}

	public static void askContent(int currentMatrix)
	{
		boolean moreFill = true;

		do
		{

			System.out.println(
					"0: Fill by line   1: Fill by column   2: Fill all   3: individual fill   4:Random fill");
			int fillType = input.nextInt();
			setContent(fillType, currentMatrix);
			System.out.println(
					"Do you want to fill this matrix more\n1:yes\t2:no");
			displayMatrixAt(currentMatrix);
			moreFill = input.nextInt() == 1;
		}
		while (moreFill);
	}

	public static void askModification()
	{
		int moreModification = 1;
		System.out.println("What matrix do you want to modify");
		displayAll();
		int currentMatrix = askMatrix();

		do
		{
			System.out.println(
					"How do you want to modify your matrix\n0:Change Height   1:Change Lenght   2:Change content");
			displayMatrixAt(currentMatrix);
			int modification = input.nextInt();
			modify(modification, currentMatrix);
			System.out.println("Do you want more modification\n1:yes   2:no");
			displayMatrixAt(currentMatrix);
			moreModification = input.nextInt();

		}
		while (moreModification != 2);
	}

	public static void modify(int modification, int currentMatrix)
	{
		switch (modification)
		{
			case 0:
				System.out.println("Enter new height");
				int newHeight = input.nextInt();
				matrixList.get(currentMatrix).modifyHeight(newHeight);
				break;
			case 1:
				System.out.println("Enter new lenght");
				int newLenght = input.nextInt();
				matrixList.get(currentMatrix).modifyLenght(newLenght);
				break;
			case 2:
				askContent(currentMatrix);
				break;

		}
	}

	public static int fillMatrix()
	{
		System.out.println("What number do you want to put in the matrix");
		int fillNumber = input.nextInt();

		return fillNumber;
	}

	public static int randomFill(int min, int max)
	{
		return ((int) (Math.random() * (max - min + 1)) + min);
	}

	public static int lineChanged(boolean isLine)
	{
		System.out.println("What " + (isLine ? "line " : "column ")
				+ "do you want to modify?");
		return input.nextInt();
	}

	public static void setContent(int fillType, int currentMatrix)
	{
		int content;
		int lineChange;
		switch (fillType)
		{
			case 0:
				content = fillMatrix();
				lineChange = lineChanged(true) - 1;
				for (int i = 0; i < matrixList.get(currentMatrix)
						.getLenght(); i++)
				{
					matrixList.get(currentMatrix).setContent(content,
							lineChange, i);
				}
				break;

			case 1:
				content = fillMatrix();
				lineChange = lineChanged(false) - 1;
				for (int i = 0; i < matrixList.get(currentMatrix)
						.getHeight(); i++)
				{
					matrixList.get(currentMatrix).setContent(content, i,
							lineChange);
				}
				break;

			case 2:
				content = fillMatrix();
				for (int i = 0; i < matrixList.get(currentMatrix)
						.getHeight(); i++)
				{
					for (int j = 0; j < matrixList.get(currentMatrix)
							.getLenght(); j++)
					{
						matrixList.get(currentMatrix).setContent(content, j, i);
					}
				}

				break;
			case 3:
				for (int i = 0; i < matrixList.get(currentMatrix)
						.getHeight(); i++)
				{
					for (int j = 0; j < matrixList.get(currentMatrix)
							.getLenght(); j++)
					{
						matrixList.get(currentMatrix).setContent(fillMatrix(),
								i, j);
						displayMatrixAt(currentMatrix);
					}
				}
				break;
			case 4:
				System.out.println("Choose the minimum value");
				int min = input.nextInt();
				System.out.println("Choose the maximum value");
				int max = input.nextInt();
				for (int i = 0; i < matrixList.get(currentMatrix)
						.getHeight(); i++)
				{
					for (int j = 0; j < matrixList.get(currentMatrix)
							.getLenght(); j++)
					{
						matrixList.get(currentMatrix)
								.setContent(randomFill(min, max), i, j);
					}
				}
				break;

		}
	}

	public static void askMath()
	{
		int move;
		System.out.println(
				"What opperation do you want to do\n0:Add Matrix   1:Substract Matrix   2:Multiply Matrix   3:Calculate determinant  4:Invert Matrix");

		do
		{
			move = input.nextInt();
		}
		while (move < 0 && move > 4);
		doMath(move);
	}

	public static void doMath(int opperation)
	{
		Matrix newMatrix = null;
		int firstMatrix;
		int secondMatrix;
		double determinant;
		switch (opperation)
		{
			case 0:
				System.out.println("Choose the two matrix you want to add");
				displayAll();
				firstMatrix = askMatrix();
				secondMatrix = askMatrix();
				newMatrix = matrixList.get(firstMatrix)
						.add(matrixList.get(secondMatrix));
				break;
			case 1:
				System.out
						.println("Choose the two matrix you want to substract");
				displayAll();
				firstMatrix = askMatrix();
				secondMatrix = askMatrix();
				newMatrix = matrixList.get(firstMatrix)
						.substract(matrixList.get(secondMatrix));
				break;
			case 2:
				System.out
						.println("Choose the two matrix you want to multiply");
				displayAll();
				firstMatrix = askMatrix();
				secondMatrix = askMatrix();
				newMatrix = matrixList.get(firstMatrix)
						.Multiply(matrixList.get(secondMatrix));
				break;
			case 3:
				System.out.println(
						"Choose the matrix you want to find the determinant of");
				displayAll();
				firstMatrix = askMatrix();
				determinant = Matrix
						.getDeterminant(matrixList.get(firstMatrix));
				displayMatrixAt(firstMatrix);
				System.out.println(determinant);
				break;
			case 4:
				System.out.println("Choose the matrix that you want to invert");
				displayAll();
				firstMatrix = askMatrix();
				newMatrix = Matrix.invertMatrix(matrixList.get(firstMatrix));
				break;
		}
		if (opperation != 3)
		{
			matrixList.add(newMatrix);
		}
	}

	public static void takeAction(int move)
	{
		boolean canTakeAction = true;
		if ((move == 2 || move == 3 || move == 4) && !hasMatrix())
		{
			canTakeAction = false;
			System.out.println("You must create a matrix to do that");
		}
		if (canTakeAction)
		{
			switch (move)
			{
				case 0:
					// do nothing
					break;
				case 1:
					createMatrix();
					break;
				case 2:
					removeMatrix();
					break;
				case 3:
					askModification();
					break;
				case 4:
					askMath();
					break;
				case 5:
					displayAll();
					break;
			}

		}
	}

	public static int askAction()
	{
		int move;
		do
		{
			System.out.println(
					"0: Close   1:Create new matrix   2:Remove matrix   3:Modify matrix   4:Math   5:Display");
			move = input.nextInt();
		}
		while (move < 0 || move > 5);
		return move;

	}

	public static void displayAll()
	{
		for (int i = 0; i < matrixList.size(); i++)
		{
			System.out.println((i + 1) + "\n" + matrixList.get(i));
		}
	}

	public static void displayMatrixAt(int currentMatrix)
	{
		System.out.println(matrixList.get(currentMatrix));
	}

	public static void main(String[] args)
	{

		System.out.println("Welcome to matrix calculator");
		int action;
		do
		{
			action = askAction();
			takeAction(action);
		}
		while (action != 0);

	}

}
