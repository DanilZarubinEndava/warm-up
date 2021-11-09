package com.endava.internship.warmup.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.IntPredicate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ArrayProcessorWithForLoopsTest {

    private ArrayProcessor testProcessor;

    @BeforeEach
    void setUp() {
        testProcessor = new ArrayProcessorWithForLoops();
    }

    @Test
    void noneMatch_whenNoElementsDivisibleBy10_returnTrue() {
        final int[] trueInputArray = {1, 31, 49, -33, Integer.MAX_VALUE, Integer.MIN_VALUE};
        assertThat(testProcessor.noneMatch(trueInputArray)).isTrue();
    }

    @Test
    void noneMatch_whenElementsDivisibleBy10_returnFalse() {
        final int[] falseInputArray = {3, -930, 50, -33, Integer.MAX_VALUE, Integer.MIN_VALUE};
        assertThat(testProcessor.noneMatch(falseInputArray)).isFalse();
    }

    @Test
    void someMatch_whenElementsMatchPredicate_returnTrue() {
        final int[] trueInputArray = {352, 3591, 33, 55};
        final IntPredicate numberIsOdd = (k) -> k % 2 == 1;
        assertThat(testProcessor.someMatch(trueInputArray, numberIsOdd)).isTrue();
    }

    @Test
    void someMatch_whenNoElementsMatchPredicate_returnFalse() {
        final int[] falseInputArray = {5512, 418, 930, 990};
        final IntPredicate numberIsOdd = (k) -> k % 2 == 1;
        assertThat(testProcessor.someMatch(falseInputArray, numberIsOdd)).isFalse();
    }

    @Test
    void allMatch_whenAllElementsMatchPredicate_returnTrue() {
        final String[] testStringNumbersTrue = {"214", "991232", "120"};
        final IntPredicate numberIsEven = (nr) -> nr % 2 == 0;

        final boolean actual = testProcessor.allMatch(testStringNumbersTrue, Integer::valueOf, numberIsEven);

        assertThat(actual).isTrue();
    }

    @Test
    void allMatch_whenNotAllElementsMatchPredicate_returnFalse() {
        final String[] testStringNumbersFalse = {"214", "331", "1242"};
        final IntPredicate numberIsEven = (nr) -> nr % 2 == 0;

        final boolean actual = testProcessor.allMatch(testStringNumbersFalse, Integer::valueOf, numberIsEven);

        assertThat(actual).isFalse();
    }

    @Test
    void copyValues_whenCopyElementsWithNormalIndexes_returnArrayWithValuesWithinBounds() {
        final int[] testStringNumbers = {214, 331, 1243, 214, 991232, 120};
        assertThat(testProcessor.copyValues(testStringNumbers, 1, 4)).containsExactly(331, 1243, 214);
    }

    @Test
    void copyValues_whenOutOfBoundsIndexes_throwException() {
        final int[] testStringNumbers = {214, 331, 1243, 214, 991232, 120};

        assertAll(
            () -> assertThatThrownBy(() -> testProcessor.copyValues(testStringNumbers, 4, 1)).isInstanceOf(IllegalArgumentException.class),
            () -> assertThatThrownBy(() -> testProcessor.copyValues(testStringNumbers, -3, 5)).isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void replace() {
        final int[] testStringNumbers = {0, 31, 24, Integer.MAX_VALUE};
        assertThat(testProcessor.replace(testStringNumbers)).containsOnly(0, -31, 48, -Integer.MAX_VALUE);
    }

    @Test
    void findSecondMax() {
        final int[] secMaxArray = {6, 23, 61, 44, 63, 62, 63};
        assertThat(testProcessor.findSecondMax(secMaxArray)).isEqualTo(62);
    }

    @Test
    void rearrange() {
        final int[] initialArray = {3, -5, 4, -7, 2, 9};
        final int[] expectedArray = {-7, -5, 9, 2, 4, 3};

        assertThat(testProcessor.rearrange(initialArray)).containsExactly(expectedArray);
    }

    @Test
    void filter() {
        final int[] input = {3, -5, 4, -7, 2, 9};
        final int[] expectedArray = {3, 4, 2, 9};

        assertThat(testProcessor.filter(input)).containsExactly(expectedArray);
    }

    @Test
    void insertValues() {
        final int[] input1 = {3, 5, 6, 12, 15, 123};
        final int[] input2 = {7, 45, 18};

        assertThat(testProcessor.insertValues(input1, 2, input2)).containsExactly(3, 5, 7, 45, 18, 6, 12, 15, 123);
    }

    @Test
    void insertValuesThrowsException() {
        final int[] input1 = {3, 5, 6, 12, 15, 123};
        final int[] input2 = {7, 45, 18};

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> testProcessor.insertValues(input1, -3, input2));
    }

    @ParameterizedTest
    @MethodSource("provideArraysForMergeSortedArrays")
    void mergeSortedArrays(int[] list1, int[] list2, int[] list3) {
        assertThat(testProcessor.mergeSortedArrays(list1, list2)).containsExactly(list3);
    }

    @ParameterizedTest
    @MethodSource("provideArraysForValidateForMatrixMultiplication")
    void validateForMatrixMultiplication(int[][] matrix1, int[][] matrix2) {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> testProcessor.validateForMatrixMultiplication(matrix1, matrix2));
    }

    @Test
    void matrixMultiplication() {
        int[][] leftMatrix = {
            {1, 2, 3},
            {4, 5, 6}
        };
        int[][] rightMatrix = {
            {7, 8},
            {9, 10},
            {11, 12}
        };
        int[][] expectedMatrix = {
            {58, 64},
            {139, 154}
        };

        assertThat(testProcessor.matrixMultiplication(leftMatrix, rightMatrix)).isEqualTo(expectedMatrix);
    }

    @Test
    void distinct() {
        int[] initialArray = {12, 53, 22, 76, 12, 54, 53, 76, 12};
        int[] expectedArray = {12, 53, 22, 76, 54};

        assertThat(testProcessor.distinct(initialArray)).containsOnly(expectedArray);
    }

    private static Stream<Arguments> provideArraysForMergeSortedArrays() {
        int[] listMixed_1 = {-8, 5, 6, 12, 15, 54};
        int[] listMixed_2 = {-86, -5, -2, 7, 8, 8, 45, 123};
        int[] expectedList_1 = {-86, -8, -5, -2, 5, 6, 7, 8, 8, 12, 15, 45, 54, 123};

        int[] listAbsolute_1 = {-4, -2, 3, 5, 6};
        int[] listAbsolute_2 = {12, 15, 123, 125};
        int[] expectedList_2 = {-4, -2, 3, 5, 6, 12, 15, 123, 125};

        return Stream.of(
            Arguments.of(listMixed_1, listMixed_2, expectedList_1),
            Arguments.of(listMixed_2, listMixed_1, expectedList_1),
            Arguments.of(listAbsolute_1, listAbsolute_2, expectedList_2)
        );
    }

    private static Stream<Arguments> provideArraysForValidateForMatrixMultiplication() {
        int[][] leftMatrix1 = {
            new int[3],
            new int[1],
            new int[3],
            new int[2]
        };
        int[][] leftMatrix2 = {
            new int[3],
            new int[3],
            new int[3]
        };
        int[][] rightMatrix1 = {
            new int[5],
            new int[5],
            new int[5],
            new int[5]
        };

        return Stream.of(
            Arguments.of(new int[0][0], new int[0][0]),
            Arguments.of(leftMatrix1, rightMatrix1),
            Arguments.of(leftMatrix2, rightMatrix1)
        );
    }
}