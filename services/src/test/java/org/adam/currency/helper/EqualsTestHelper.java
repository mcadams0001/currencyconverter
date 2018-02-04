package org.adam.currency.helper;

import static org.junit.jupiter.api.Assertions.*;

public class EqualsTestHelper {
    public static <T> void verifyEquals(T obj1, T obj2, T obj3) {
        assertTrue(obj1.equals(obj1), "Object must be equal to itself");
        assertTrue(obj1.equals(obj2), "Two same object must be equal");
        assertFalse(obj1.equals(obj3), "Two different objects cannot be equal");
        assertFalse(obj1.equals(null), "Object must not be equal to null");
        assertFalse(obj1.equals(""), "Two different object types cannot be equal");
    }

    public static <T> void verifyHashCode(T obj1, T obj2, T obj3) {
        assertEquals(obj2.hashCode(), obj1.hashCode(), "Hash code of two same object should be the same");
        assertFalse(obj3.hashCode() == obj1.hashCode(), "Hash code of two different objects must be different");
    }
}
