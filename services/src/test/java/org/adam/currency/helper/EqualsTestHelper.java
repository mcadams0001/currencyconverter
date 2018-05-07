package org.adam.currency.helper;

import static org.junit.jupiter.api.Assertions.*;

public class EqualsTestHelper {
    public static <T> void verifyEquals(T obj1, T obj2, T obj3) {
        assertEquals(obj1, obj1, "Object must be equal to itself");
        assertEquals(obj1, obj2, "Two same object must be equal");
        assertNotEquals(obj1, obj3, "Two different objects cannot be equal");
        assertNotNull(obj1, "Object must not be equal to null");
        assertNotEquals(obj1, "", "Two different object types cannot be equal");
    }

    public static <T> void verifyHashCode(T obj1, T obj2, T obj3) {
        assertEquals(obj2.hashCode(), obj1.hashCode(), "Hash code of two same object should be the same");
        assertFalse(obj3.hashCode() == obj1.hashCode(), "Hash code of two different objects must be different");
    }
}
