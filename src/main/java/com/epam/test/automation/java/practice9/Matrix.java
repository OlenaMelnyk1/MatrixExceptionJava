package com.epam.test.automation.java.practice9;

import java.text.DecimalFormat;

public class Matrix {

    private double[][] arrayReal;
    private static final String INCORRECT_VALUE = "Incompatible matrix sizes";
    private static final String INCORRECT_VALUE_ROWS = "Array passed with zero number of rows";
    private static final String INCORRECT_VALUE_COLUMNS = "Array passed with zero number of columns";

    public void checkMatrix(int row, int column) throws MatrixException {
        if ((row < 1) || (column < 1))
            throw new MatrixException(INCORRECT_VALUE);
    }

    public boolean checkRows(int row) throws MatrixException {
        if (row < 1) throw new MatrixException(INCORRECT_VALUE_ROWS);
        return true;
    }

    public boolean checkColumns(int columns) throws MatrixException {
        if (columns < 1) throw new MatrixException(INCORRECT_VALUE_COLUMNS);
        return true;
    }

    private void checkMatrixForAdd(Matrix matrix) throws MatrixException {
        if (this.rows()!= matrix.rows() || this.columns() != matrix.columns())
            throw new MatrixException(INCORRECT_VALUE);
    }

    private void checkMatrixForMulti(Matrix matrix) throws MatrixException {
        if (this.rows()!= matrix.rows()) throw new MatrixException(INCORRECT_VALUE);
    }

    private void checkIndMatrix(int i, int j) throws MatrixException{
        if (i<0 ||j<0 || i>=rows() ||j>=columns()) throw new MatrixException(INCORRECT_VALUE);
    }

    public Matrix(int row, int column) throws MatrixException {
        try {
            checkMatrix(row, column);
            this.arrayReal = new double[row][column];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    this.arrayReal[i][j] = 0.0;
                }
            }
        } catch (MatrixException e) {
            System.err.println(INCORRECT_VALUE);
        }
    }

    public Matrix(double[][] twoDimensionalArray) throws MatrixException {
        int rows=twoDimensionalArray.length;
        int columns=twoDimensionalArray[0].length;
        if (checkRows(rows) && checkColumns(columns))
            this.arrayReal = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(twoDimensionalArray[i], 0, this.arrayReal[i], 0, columns);
        }
    }

    public final int rows() {
        return this.arrayReal.length;
    }

    public final int columns() {
        return this.arrayReal[0].length;
    }

    public double[][] twoDimensionalArrayOutOfMatrix() {
        return this.arrayReal;
    }

    public double getValue(int row, int column) throws MatrixException {
        try {
            checkIndMatrix(row, column);
            if ((row>=this.rows())|| (column>=this.columns()))
                throw new MatrixException(INCORRECT_VALUE);
                else return this.arrayReal[row][column];
        }
        catch (MatrixException e) {
            System.err.println(INCORRECT_VALUE);
        }
        return 0.0;
    }

    public void setValue(int row, int column, double newValue) throws MatrixException {
        try {
            checkIndMatrix(row, column);
            if ((row>=this.rows())|| (column>=this.columns()))
                throw new MatrixException(INCORRECT_VALUE);
                else this.arrayReal[row][column] = newValue;
        }
        catch (MatrixException e) {
            System.err.println(INCORRECT_VALUE);
        }
    }

    public Matrix addition(Matrix matrix) throws MatrixException {
        try {
            if (matrix==null)throw new MatrixException(INCORRECT_VALUE);
            checkMatrix(matrix.columns(),matrix.rows());
            checkMatrixForAdd(matrix);
            Matrix newMatrix = new Matrix(this.rows(), this.columns());
                for (int i = 0; i < this.rows(); i++) {
                    for (int j = 0; j < this.columns(); j++) {
                        this.setValue(i,j,this.getValue(i,j)+matrix.getValue(i, j));
                        newMatrix.setValue(i, j, this.getValue(i,j) + matrix.getValue(i, j));
                    }
                }
                return newMatrix;
        }
        catch (MatrixException e) {
            System.err.println(INCORRECT_VALUE);
            return null;
        }
    }

    public Matrix subtraction(final Matrix matrix) throws MatrixException {

        try {
            if (matrix==null)throw new MatrixException(INCORRECT_VALUE);
            checkMatrix(matrix.columns(),matrix.rows());
            checkMatrixForAdd(matrix);
            Matrix newMatrix=new Matrix(this.arrayReal.length,this.arrayReal[0].length);
            if (this.arrayReal == null) {
                for (int i = 0; i <matrix.rows() ; i++) {
                    for (int j = 0; j <matrix.columns(); j++) {
                        newMatrix.setValue(i,j,-matrix.getValue(i,j));
                    }
                }
            }
            else {
                for (int i = 0; i < this.arrayReal.length; i++) {
                    for (int j = 0; j < this.arrayReal[0].length; j++) {
                        newMatrix.setValue(i, j, this.getValue(i,j) - matrix.getValue(i, j));
                    }
                }
            }
        return newMatrix;
    }
        catch (MatrixException e) {
            System.err.println(INCORRECT_VALUE);
            return null;
        }
    }


    public Matrix multiplication(final Matrix matrix) throws MatrixException {

            try {
                if (matrix==null)throw new MatrixException(INCORRECT_VALUE);
                if ((matrix.rows() < 1) || (matrix.columns() < 1))
                    throw new MatrixException(INCORRECT_VALUE);
                checkMatrixForMulti(matrix);
                    double[][] multi = new double[this.rows()][matrix.columns()];
                    for (int i = 0; i < this.rows(); i++) {
                        for (int j = 0; j < matrix.columns(); j++) {
                            for (int k = 0; k < this.columns(); k++) {
                                multi[i][j] += this.getValue(i,k) + matrix.getValue(k, j);
                            }
                        }
                    }
                    return new Matrix(multi);
            }
            catch (MatrixException e) {
                System.err.println(INCORRECT_VALUE);
                return null;
            }
        }
    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.rows(); i++) {
            for (int j = 0; j < this.columns(); j++) {
                try {
                    if (j != this.columns() - 1) {
                        builder.append(decimalFormat.format(getValue(i, j)) + " ");
                    } else {
                        builder.append(decimalFormat.format(getValue(i, j)));
                    }
                } catch (MatrixException e) {
                    e.getMessage();
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}