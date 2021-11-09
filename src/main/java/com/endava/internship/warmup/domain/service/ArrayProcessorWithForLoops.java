package com.endava.internship.warmup.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.ToIntFunction;
import static java.util.Objects.isNull;

public class ArrayProcessorWithForLoops implements ArrayProcessor {

    /**
     * Return true if there are no numbers that divide by 10
     * @param input non-null immutable array of ints
     */
    @Override
    public boolean noneMatch(final int[] input) {

        if (input.length == 0) {
            throw new IllegalArgumentException("Input array must not be null!");
        }

        boolean match = true;

        for (int i : input) {
            if (i % 10 == 0) {
                match = false;
                break;
            }
        }

        return match;
    }

    /**
     * Return true if at least one value in input matches the predicate
     * @param input non-null immutable array of ints
     * @param predicate invoke the predicate.test(int value) on each input element
     */
    @Override
    public boolean someMatch(final int[] input, IntPredicate predicate) {

        if (input.length == 0) {
            throw new IllegalArgumentException("Input array must not be null!");
        }

        boolean match = false;

        for (int i : input) {
            if (predicate.test(i)) {
                match = true;
                break;
            }
        }

        return match;
    }

    /**
     * Return true if all values processed by function, matches the predicate
     * @param input non-null immutable array of Strings. No element is null
     * @param function invoke function.applyAsInt(String value) to transform all the input elements into an int value
     * @param predicate invoke predicate.test(int value) to test the int value obtained from the function
     */
    @Override
    public boolean allMatch(final String[] input,
            ToIntFunction<String> function,
            IntPredicate predicate) {

        if (input.length == 0) {
            throw new IllegalArgumentException("Input array must not be null!");
        }

        boolean match = true;

        for (String s : input) {
            if (!predicate.test(function.applyAsInt(s))) {
                match = false;
            }
        }

        return match;
    }

    /**
     * Copy values into a separate array from specific index to stopindex
     * @param input non-null array of ints
     * @param startInclusive the first index of the element from input to be included in the new array
     * @param endExclusive the last index prior to which the elements are to be included in the new array
     * @throws IllegalArgumentException when parameters are outside of input index bounds
     */
    @Override
    public int[] copyValues(int[] input, int startInclusive, int endExclusive) throws IllegalArgumentException {

        if (input.length == 0 || startInclusive < 0 || endExclusive > input.length || endExclusive < startInclusive) {
            throw new IllegalArgumentException(
                    String.format("Input array length is %s, start index %s, end index %s", input.length, startInclusive, endExclusive));
        }

        return copyOfRange(input, startInclusive, endExclusive);
    }

    /**
     * Replace even index values with their doubles and odd indexed elements with their negative
     * @param input non-null immutable array of ints
     * @return new array with changed elements
     */
    @Override
    public int[] replace(final int[] input) {

        if (input.length == 0) {
            throw new IllegalArgumentException("Input array must not be null!");
        }

        for (int i = 0; i < input.length; i++) {
            input[i] = (input[i] % 2 == 0) ? input[i] * 2 : -input[i];
        }

        return input;
    }

    /**
     * Find the second max value in the array
     * @param input non-null immutable array of ints
     */
    @Override
    public int findSecondMax(final int[] input) {

        if (input.length == 0) {
            throw new IllegalArgumentException("Input array must not be null!");
        }

        sort(input);

        int secondMax = input[0];

        for (int i = input.length - 2; i >= 0; i--) {
            if (input[i] != input[input.length - 1]) {
                return input[i];
            }
        }

        return secondMax;
    }

    /**
     * Return in reverse first negative numbers, then positive numbers from array
     * @param input non-null immutable array of ints.
     * @return example: input {3, -5, 4, -7, 2 , 9}
     *                  result: {-7, -5, 9, 2, 4, 3}
     */
    @Override
    public int[] rearrange(final int[] input) {

        if (input.length == 0) {
            throw new IllegalArgumentException("Input array must not be null!");
        }

        ArrayList<Integer> arr = new ArrayList<>();
        int initialPivot = input.length - 1;

        for (int i = initialPivot; i >= 0; i--) {
            int pivotalPoint = arr.indexOf(input[initialPivot]);
            arr.add(input[i] >= 0 ? arr.size() : pivotalPoint, input[i]);
        }

        return listToArray(arr);
    }

    /**
     * Remove (filter) all values which are smaller than (input max element - 10)
     * @param input non-null immutable array of ints
     * @return The result array should not contain empty cells!
     */
    @Override
    public int[] filter(final int[] input) {

        if (input.length == 0) {
            throw new IllegalArgumentException("Input array must not be null!");
        }

        int[] arr = new int[1];

        int max = maxOfArray(input);

        for (int i : input) {
            if (i > max - 10) {
                arr = copyOf(arr, arr.length + 1);
                arr[arr.length - 2] = i;
            }
        }
        return copyOf(arr, arr.length - 1);
    }

    /**
     * Insert values into input array at a specific index.
     * @param input non-null immutable array of ints.
     * @param startInclusive the index of input at which the first element from values array should be inserted
     * @param values the values to be inserted from startInclusive index
     * @return new array containing the combined elements of input and values
     * @throws IllegalArgumentException when startInclusive is out of bounds for input
     */
    @Override
    public int[] insertValues(final int[] input, int startInclusive, int[] values) throws IllegalArgumentException {

        if (input.length == 0 || startInclusive < 0 || startInclusive >= input.length) {
            throw new
                    IllegalArgumentException(String.format("Input array length is %s, and start index is %s", input.length, startInclusive));
        }

        int[] arr = new int[input.length + values.length];
        int index = 0;
        int from = startInclusive - 1;
        int to = startInclusive + values.length;

        for (; index <= from; index++) {
            arr[index] = input[index];
        }

        for (; index < to; index++) {
            arr[index] = values[index - startInclusive];
        }

        for (; index < arr.length; index++) {
            arr[index] = input[index - values.length];
        }

        return arr;
    }

    /**
     * Merge two sorted input and input2 arrays so that the return values are also sorted
     * @param input first non-null array
     * @param input2 second non-null array
     * @return new array containing all elements sorted from input and input2
     * @throws IllegalArgumentException if either input or input are not sorted ascending
     */
    @Override
    public int[] mergeSortedArrays(int[] input, int[] input2) {
        int length = input.length;
        int length2 = input2.length;
        int index = 0;
        int index2 = 0;
        int indexResult = 0;
        int[] result = new int[length + length2];

        while (index < length && index2 < length2) {
            if (input[index] < input2[index2]) {
                result[indexResult++] = input[index++];
            } else {
                result[indexResult++] = input2[index2++];
            }
        }
        while (index < length) {
            result[indexResult++] = input[index++];
        }
        while (index2 < length2) {
            result[indexResult++] = input2[index2++];
        }

        return result;
    }

    /**
     * In order to execute a matrix multiplication, in this method, please validate the input data throwing exceptions for invalid input. If the the
     * input params are satisfactory, do not throw any exception.
     *
     * Please review the matrix multiplication https://www.mathsisfun.com/algebra/matrix-multiplying.html
     * @param leftMatrix the left matrix represented by array indexes [row][column]
     * @param rightMatrix the right matrix represented by array indexes [row][column]
     * @throws NullPointerException when any of the inputs are null. (arrays, rows and columns)
     * @throws IllegalArgumentException when any array dimensions are not appropriate for matrix multiplication
     */
    @Override
    public void validateForMatrixMultiplication(int[][] leftMatrix, int[][] rightMatrix) throws NullPointerException, IllegalArgumentException {
        if (isNull(leftMatrix) || isNull(rightMatrix)) {
            throw new NullPointerException("Matrices are of zero size or null!");
        }
        if (matrixDimensionsAreIllegal(leftMatrix)
                || matrixDimensionsAreIllegal(rightMatrix)
                || leftMatrix[0].length != rightMatrix.length) {
            throw new IllegalArgumentException("Matrices are of illegal dimensions!");
        }
    }

    /**
     * Perform the matrix multiplication as described in previous example Please review the matrix multiplication
     * https://www.mathsisfun.com/algebra/matrix-multiplying.html
     * @param leftMatrix the left matrix represented by array indexes [row][column]
     * @param rightMatrix the right matrix represented by array indexes [row][column]
     * @throws NullPointerException when any of the inputs are null. (arrays, rows and columns)
     * @throws IllegalArgumentException when any array dimensions are not appropriate for matrix multiplication
     */
    @Override
    public int[][] matrixMultiplication(final int[][] leftMatrix, final int[][] rightMatrix) throws NullPointerException, IllegalArgumentException {
        validateForMatrixMultiplication(leftMatrix, rightMatrix);
        int[][] result = new int[leftMatrix.length][rightMatrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyCell(leftMatrix, rightMatrix, row, col);
            }
        }

        return result;
    }

    /**
     * Return only distinct values in an array.
     * @param input non-null immutable array of ints.
     */
    @Override
    public int[] distinct(final int[] input) {

        if (input.length == 0) {
            throw new IllegalArgumentException("Input array must not be null!");
        }

        List<Integer> list = new ArrayList<>(0);

        for (int i : input) {
            if (!list.contains(i)) {
                list.add(i);
            }
        }

        return listToArray(list);
    }

    private int[] listToArray(final List<Integer> arr) {
        return arr.stream().mapToInt(i -> i).toArray();
    }

    private int maxOfArray(final int[] arr) {
        int[] temp = arr.clone();
        sort(temp);

        return temp[temp.length - 1];
    }

    private boolean matrixDimensionsAreIllegal(final int[][] matrix) {
        if (matrix.length == 0) {
            return true;
        }

        for (int[] ints : matrix) {
            if (ints.length != matrix[0].length) {
                return true;
            }
        }

        return false;
    }

    private int multiplyCell(int[][] firstMatrix, int[][] secondMatrix, int row, int col) {
        int cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }

    private int[] sort(int[] arrayToSort) {
        for (int i = 0; i < arrayToSort.length; i++) {
            for (int j = i + 1; j < arrayToSort.length; j++) {
                int temp;
                if (arrayToSort[i] > arrayToSort[j]) {
                    temp = arrayToSort[i];
                    arrayToSort[i] = arrayToSort[j];
                    arrayToSort[j] = temp;
                }
            }
        }
        return arrayToSort;
    }

    public static int[] copyOfRange(int[] input, int from, int to) {
        int newLength = to - from;
        int[] arr = new int[newLength];
        System.arraycopy(input, from, arr, 0, Math.min(input.length - from, newLength));

        return arr;
    }

    public static int[] copyOf(int[] input, int newLength) {
        int[] arr = new int[newLength];
        System.arraycopy(input, 0, arr, 0, Math.min(input.length, newLength));
        return arr;
    }
}
