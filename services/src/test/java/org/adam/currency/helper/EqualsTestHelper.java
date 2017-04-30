package org.adam.currency.helper;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class EqualsTestHelper {
     public static <T> void verifyEquals(T obj1, T obj2, T obj3) {
         assertThat("Object must be equal to itself", obj1.equals(obj1), equalTo(true));
         assertThat("Two same object must be equal", obj1.equals(obj2), equalTo(true));
         assertThat("Two different objects cannot be equal", obj1.equals(obj3), equalTo(false));
         assertThat("Object must not be equal to null", obj1.equals(null), equalTo(false));
         assertThat("Two different object types cannot be equal", obj1.equals(""), equalTo(false));
     }

     public static <T> void verifyHashCode(T obj1, T obj2, T obj3) {
         assertThat("Hash code of two same object should be the same", obj1.hashCode(), equalTo(obj2.hashCode()));
         assertThat("Hash code of two different objects must be different", obj1.hashCode(), not(equalTo(obj3.hashCode())));
     }
}
