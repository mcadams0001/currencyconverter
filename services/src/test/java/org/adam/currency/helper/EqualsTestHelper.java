package org.adam.currency.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EqualsTestHelper {
    public static <T> void verifyEquals(T obj1, T obj2, T obj3) {
        assertEquals("Object must be equal to itself", true, obj1.equals(obj1));
        assertEquals("Two same object must be equal", true, obj1.equals(obj2));
        assertEquals("Two different objects cannot be equal", false, obj1.equals(obj3));
        assertEquals("Object must not be equal to null", false, obj1.equals(null));
        assertEquals("Two different object types cannot be equal", false, obj1.equals(""));
    }

    public static <T> void verifyHashCode(T obj1, T obj2, T obj3) {
        assertEquals("Hash code of two same object should be the same", obj2.hashCode(), obj1.hashCode());
        assertFalse(obj3.hashCode() == obj1.hashCode(), "Hash code of two different objects must be different");
    }
}
