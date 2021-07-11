import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test for module 4 homework 3 GeometryCalculator method getSphereVolume
 */

@DisplayName("Тест метода getSphereVolume класса GeometryCalculator")
public class TestGetSphereVolume {

    private static final double DELTA = 0.01;

    @Test
    @DisplayName("Передано положительное целое число 20")
    void testGetSphereVolumeWithPositiveIntegerNumber() {
        double expected = 33510.32163829113;
        assertEquals(expected, GeometryCalculator.getSphereVolume(20), DELTA);
    }

    @Test
    @DisplayName("Передан 0")
    void testGetSphereVolumeWithZero() {
        double expected = 0;
        assertEquals(expected, GeometryCalculator.getSphereVolume(0), DELTA);
    }

    @Test
    @DisplayName("Передано отрицательное целое число -20")
    void testGetSphereVolumeWithNegativeIntegerNumber() {
        double expected = 33510.32163829113;
        assertEquals(expected, GeometryCalculator.getSphereVolume(-20), DELTA);
    }

    @Test
    @DisplayName("Передано положительное дробное число 20.2")
    void testGetSphereVolumeWithPositiveFractionalNumber() {
        double expected = 34525.71789425298;
        assertEquals(expected, GeometryCalculator.getSphereVolume(20.2), DELTA);
    }

}
