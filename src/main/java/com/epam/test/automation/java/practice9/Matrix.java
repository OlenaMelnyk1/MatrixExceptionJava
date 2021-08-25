package com.epam.test.automation.java.practice9;

import java.text.DecimalFormat;

public class Matrix {

    private double[][] matrix;
    private static final String INCORRECT_VALUE = "Incompatible matrix sizes";
    private static final String INCORRECT_VALUE_ROWS = "Array passed with zero number of rows";
    private static final String INCORRECT_VALUE_COLUMNS = "Array passed with zero number of columns";
    private static final String INCORRECT_DATA = "Null matrix";

    public void checkMatrix(int row, int column) throws MatrixException {
        if ((row < 1) || (column < 1))
            throw new MatrixException(INCORRECT_VALUE);
    }

    public boolean checkRows(int row) throws MatrixException {
        if (row < 1) throw new MatrixException(INCORRECT_VALUE_ROWS);
        else return true;
    }

    public boolean checkColumns(int columns) throws MatrixException {
        if (columns < 1) throw new MatrixException(INCORRECT_VALUE_COLUMNS);
        else return true;
    }

    private void checkMatrixForAdd(Matrix matrix) throws MatrixException {
        if (this.matrix.length != matrix.rows() || this.matrix[0].length != matrix.columns())
            throw new MatrixException(INCORRECT_VALUE);
    }

    private void checkMatrixForMulti(Matrix matrix) throws MatrixException {
        if (this.matrix[0].length != matrix.rows()) throw new MatrixException(INCORRECT_VALUE);
    }

    public Matrix(int row, int column) throws MatrixException {
        try {
            checkMatrix(row, column);
            this.matrix = new double[row][column];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    this.matrix[i][j] = 0.0;
                }
            }
        } catch (MatrixException e) {
            System.err.println(INCORRECT_VALUE);
        }
    }

    public Matrix(double[][] twoDimensionalArray) throws MatrixException {

        if (checkRows(twoDimensionalArray.length) && checkColumns(twoDimensionalArray[0].length))
            this.matrix = new double[twoDimensionalArray.length][twoDimensionalArray[0].length];
        for (int i = 0; i < twoDimensionalArray.length; i++) {
            System.arraycopy(twoDimensionalArray[i], 0, this.matrix[i], 0, twoDimensionalArray[0].length);
        }
    }

    public final int rows() {
        return this.matrix.length;
    }

    public final int columns() {
        return this.matrix[0].length;
    }

    public double[][] twoDimensionalArrayOutOfMatrix() {
        double[][] twoDim = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, twoDim[i], 0, matrix[i].length);
        }
        return twoDim;
    }

    public double getValue(int row, int column) throws MatrixException {
        try {
            checkMatrix(row, column);
            if ((row>this.matrix.length)|| (column>this.matrix[0].length))
                throw new MatrixException(INCORRECT_VALUE);
                else return this.matrix[row][column];
        }
        catch (MatrixException e) {
            System.err.println(INCORRECT_VALUE);
        }
        return 0.0;
    }

    public void setValue(int row, int column, double newValue) throws MatrixException {
        try {
            checkMatrix(row, column);
            if ((row>this.matrix.length)|| (column>this.matrix[0].length))
                throw new MatrixException(INCORRECT_VALUE);
                else this.matrix[row][column] = newValue;
        }
        catch (MatrixException e) {
            System.err.println(INCORRECT_VALUE);
        }
    }

    public Matrix addition(Matrix matrix) throws MatrixException {
        try {
            matrix.checkMatrix(matrix.rows(), matrix.columns());
            checkMatrixForAdd(matrix);
            Matrix newMatrix = new Matrix(this.matrix.length, this.matrix[0].length);
            if ((matrix == null) && (this.matrix == null)) return newMatrix;
            else if (matrix == null) return new Matrix(this.matrix);
            else if (this.matrix == null) return matrix;
            else {
                for (int i = 0; i < this.matrix.length; i++) {
                    for (int j = 0; j < this.matrix[0].length; j++) {
                        this.matrix[i][j] = this.matrix[i][j] + matrix.getValue(i, j);
                        newMatrix.setValue(i, j, this.matrix[i][j] + matrix.getValue(i, j));
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

    public Matrix subtraction(final Matrix matrix) throws MatrixException {
        try {
            matrix.checkMatrix(matrix.rows(), matrix.columns());
            checkMatrixForAdd(matrix);
            Matrix newMatrix=new Matrix(this.matrix.length,this.matrix[0].length);
            if ((matrix == null) && (this.matrix == null)) return newMatrix;
            else if (matrix == null) return new Matrix(this.matrix);
            else if (this.matrix == null) {
                for (int i = 0; i <matrix.rows() ; i++) {
                    for (int j = 0; j <matrix.columns(); j++) {
                        newMatrix.setValue(i,j,-matrix.getValue(i,j));
                    }
                }
            }
            else {
                for (int i = 0; i < this.matrix.length; i++) {
                    for (int j = 0; j < this.matrix[0].length; j++) {
                        this.matrix[i][j] = this.matrix[i][j] + matrix.getValue(i, j);
                        newMatrix.setValue(i, j, this.matrix[i][j] - matrix.getValue(i, j));
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
                checkMatrix(matrix.rows(), matrix.columns());
                checkMatrixForMulti(matrix);
                if ((matrix == null) || (this.matrix == null)) return new Matrix(this.matrix.length, matrix.columns());
                else {
                    double[][] multi = new double[this.matrix.length][matrix.columns()];
                    for (int i = 0; i < this.matrix.length; i++) {
                        for (int j = 0; j < matrix.columns(); j++) {
                            for (int k = 0; k < this.matrix[0].length; k++) {
                                multi[i][j] += this.matrix[i][k] + matrix.getValue(k, j);
                            }
                        }
                    }
                    return new Matrix(multi);
                }
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