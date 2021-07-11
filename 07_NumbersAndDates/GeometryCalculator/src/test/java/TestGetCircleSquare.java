import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test for module 4 homework 3 GeometryCalculator method getCircleSquare
 */

@DisplayName("Тест метода getCircleSquare класса GeometryCalculator")
public class TestGetCircleSquare {

    private static final double DELTA = 0.01;

    @Test
    @DisplayName("Передано положительное целое число 20")
    void testGetCircleSquareWithPositiveIntegerNumber() {
        double expected = 1256.6370614359173;
        assertEquals(expected, GeometryCalculator.getCircleSquare(20), DELTA);
    }

    @Test
    @DisplayName("Передан 0")
    void testGetCircleSquareWithZero() {
        double expected = 0;
        assertEquals(expected, GeometryCalculator.getCircleSquare(0), DELTA);
    }

    @Test
    @DisplayName("Передано отрицательное целое число -20")
    void testGetCircleSquareWithNegativeIntegerNumber() {
        double expected = 1256.6370614359173;
        assertEquals(expected, GeometryCalculator.getCircleSquare(-20), DELTA);
    }

    @Test
    @DisplayName("Передано положительное дробное число 20.2")
    void testGetCircleSquareWithPositiveFractionalNumber() {
        double expected = 1281.895466370779;
        assertEquals(expected, GeometryCalculator.getCircleSquare(20.2), DELTA);
    }

}
