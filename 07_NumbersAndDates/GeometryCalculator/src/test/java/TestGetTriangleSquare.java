import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test for module 4 homework 3 GeometryCalculator method getTriangleSquare
 */

@DisplayName("Тест метода getTriangleSquare класса GeometryCalculator")
public class TestGetTriangleSquare {

    private static final double DELTA = 0.01;
    
    @Test
    @DisplayName("Передан неверно построенный треугольник. Сторона 'a' равна 1, сторона 'b' равна 20, сторона 'c' равна 40")
    void testGetTriangleSquareWithWrongPositiveSides() {
        double expected = -1;
        assertEquals(expected, GeometryCalculator.getTriangleSquare(1, 20, 40), DELTA);
    }

    @Test
    @DisplayName("Передан верно построенный треугольник. Сторона 'a' равна 3, сторона 'b' равна 4, сторона 'c' равна 5")
    void testGetTriangleSquareWithRightPositiveSides() {
        double expected = 6.0;
        assertEquals(expected, GeometryCalculator.getTriangleSquare(3, 4, 5), DELTA);
    }

    @Test
    @DisplayName("Передан неверно построенный треугольник. Сторона 'a' равна -1, сторона 'b' равна -20, сторона 'c' равна -40")
    void testGetTriangleSquareWithNegativeSides() {
        double expected = -1;
        assertEquals(expected, GeometryCalculator.getTriangleSquare(-1, -20, -40), DELTA);
    }

    @Test
    @DisplayName("Передан неверно построенный треугольник. Сторона 'a' равна -3, сторона 'b' равна 4, сторона 'c' равна 5")
    void testGetTriangleSquareWithOneNegativeSideAndTwoPositiveSides() {
        double expected = -1;
        assertEquals(expected, GeometryCalculator.getTriangleSquare(-3, 4, 5), DELTA);
    }

    @Test
    @DisplayName("Передан неверно построенный треугольник. Сторона 'a' равна 0, сторона 'b' равна 0, сторона 'c' равна 0")
    void testGetTriangleSquareWithZeroSides() {
        double expected = -1;
        assertEquals(expected, GeometryCalculator.getTriangleSquare(0, 0, 0), DELTA);
    }

    @Test
    @DisplayName("Передан неверно построенный треугольник. Сторона 'a' равна 3, сторона 'b' равна 0, сторона 'c' равна 5")
    void testGetTriangleSquareWithOneZeroSideAndTwoPositiveSides() {
        double expected = -1;
        assertEquals(expected, GeometryCalculator.getTriangleSquare(3, 0, 5), DELTA);
    }

    @Test
    @DisplayName("Передан верно построенный треугольник. Сторона 'a' равна 1.5, сторона 'b' равна 2, сторона 'c' равна 3")
    void testGetTriangleSquareWithOneFractionalSideAndTwoPositiveSides() {
        double expected = 1.3331705629813464;
        assertEquals(expected, GeometryCalculator.getTriangleSquare(1.5, 2, 3), DELTA);
    }

    @Test
    @DisplayName("Передан верно построенный треугольник. Сторона 'a' равна 1.5, сторона 'b' равна 2.6, сторона 'c' равна 3.7")
    void testGetTriangleSquareWithOneFractionalSides() {
        double expected = 1.5599999999999987;
        assertEquals(expected, GeometryCalculator.getTriangleSquare(1.5, 2.6, 3.7), DELTA);
    }
    
}
