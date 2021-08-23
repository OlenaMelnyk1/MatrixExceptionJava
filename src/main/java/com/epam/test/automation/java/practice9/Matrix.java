package com.epam.test.automation.java.practice9;

import java.text.DecimalFormat;

public class Matrix {

    private double [][]matrix;
    private static final String INCORRECT_VALUE = "Incompatible matrix sizes";
    private static final String INCORRECT_VALUE_ROWS = "Array passed with zero number of rows";
    private static final String INCORRECT_VALUE_COLUMNS = "Array passed with zero number of columns";
    
    public void checkMatrix(int row, int column) throws MatrixException{
        if ((row<1)|| (column<1))
            throw new MatrixException(INCORRECT_VALUE);
    }
    public boolean checkRows(int row) throws MatrixException{
        if (row < 1) throw new MatrixException(INCORRECT_VALUE_ROWS);
        return true;
    }
    public boolean checkColumns(int columns) throws MatrixException{
        if (columns < 1) throw new MatrixException(INCORRECT_VALUE_COLUMNS);
        return true;
    }
    private void checkMatrixForAdd(Matrix matrix) throws MatrixException {
        if (this.matrix.length!=matrix.rows()||this.matrix[0].length!=matrix.columns())
            throw new MatrixException(INCORRECT_VALUE);
    }
    private void checkMatrixForMulti(Matrix matrix) throws MatrixException {
        if (this.matrix[0].length!= matrix.rows()) throw new MatrixException(INCORRECT_VALUE);
    }
    public Matrix(int row, int column) throws MatrixException {
        checkMatrix(row,column);
         for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++)
                this.matrix[i][j]=0.0;
           }
    }

    public Matrix(double[][] twoDimensionalArray) throws MatrixException {
       if (checkRows(twoDimensionalArray.length) && checkColumns(twoDimensionalArray[0].length))
        for (int i = 0; i < twoDimensionalArray.length; i++) {
            for (int j = 0; j < twoDimensionalArray[0].length; j++)
                this.matrix[i][j]=twoDimensionalArray[i][j];
        }
    }

    public final int rows() {
       return this.matrix.length;
    }

    /**
     * @return Returns the number of columns in a matrix
     */
    public final int columns() {
        return this.matrix[0].length;
    }

    /**
     * Receiving of standard two-dimensional array out of matrix.
     *
     * @return Standard two-dimensional array
     */
    public double[][] twoDimensionalArrayOutOfMatrix() {
        double [][]twoDim=new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                twoDim[i][j]=matrix[i][j];
            }
        }
        return twoDim;
    }

    /**
     * Reading of elements via predetermined correct index
     *
     * @param row    number of rows
     * @param column number of columns
     * @return Returns the value of a matrix element <code>[row,column]</code>
     * @throws MatrixException if index incorrect, returns message "Incompatible matrix sizes"
     */
    public double getValue(int row, int column) throws MatrixException {
        checkMatrix(row,column);
        return this.matrix[row][column];
    }

    /**
     * Recording value <code>newValue</code> of elements via predetermined correct index <code>[row,column]</code>     *
     *
     * @param row      number of rows
     * @param column   number of columns
     * @param newValue new value of a matrix element
     * @throws MatrixException if index incorrect, returns message "Incompatible matrix sizes"
     */
    public void setValue(int row, int column, double newValue) throws MatrixException {
        checkMatrix(row,column);
        this.matrix[row][column]=newValue;
    }

    /**
     * Method of matrix's addition  <code>matrix</code>.
     * Result in the original matrix
     *
     * @param matrix matrix corresponding to the second term
     * @return Returns a new resulting matrix
     * @throws MatrixException if incompatible matrix sizes, returns message "Incompatible matrix sizes"
     */
    public Matrix addition(Matrix matrix) throws MatrixException {
        checkMatrixForAdd(matrix);
        Matrix newMatrix=new Matrix(this.matrix.length,this.matrix[0].length);
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j <this.matrix[0].length; j++) {
                this.matrix[i][j]=this.matrix[i][j]+matrix.getValue(i,j);
                newMatrix.setValue(i,j,this.matrix[i][j]+matrix.getValue(i,j));
            }
        }
        return newMatrix;
    }
    /**
     * Method of matrix's deduction <code>matrix</code> from original.
     * Result in the original matrix
     *
     * @param matrix matrix corresponding to the subtracted
     * @return Returns a new resulting matrix
     * @throws MatrixException if incompatible matrix sizes, returns message "Incompatible matrix sizes"
     */
    public Matrix subtraction(final Matrix matrix) throws MatrixException {
        checkMatrixForAdd(matrix);
         Matrix newMatrix=new Matrix(this.matrix.length,this.matrix[0].length);
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j <this.matrix[0].length; j++) {
                this.matrix[i][j]=this.matrix[i][j]+matrix.getValue(i,j);
                newMatrix.setValue(i,j,this.matrix[i][j]-matrix.getValue(i,j));
            }
        }
        return newMatrix;
    }

    /**
     * Method of matrix's multiplication <code>matrix</code>
     * Result in the original matrix
     *
     * @param matrix matrix corresponding to the second factor
     * @return Returns a new resulting matrix
     * @throws MatrixException if incompatible matrix sizes, returns message "Incompatible matrix sizes"
     */
    public Matrix multiplication(final Matrix matrix) throws MatrixException {
        checkMatrix(matrix.rows(),matrix.columns());
        checkMatrixForMulti(matrix);
        //Matrix multiM=new Matrix(this.matrix.length, matrix.columns());
        double[][] multi=new double[this.matrix.length][matrix.columns()];
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j <matrix.columns() ; j++) {
                for (int k = 0; k <this.matrix[0].length ; k++) {
                    multi[i][j]+=this.matrix[i][k]+matrix.getValue(k,j);
                }
            }
        }
       return new Matrix(multi);
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