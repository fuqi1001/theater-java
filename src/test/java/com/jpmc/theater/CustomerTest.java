package com.jpmc.theater;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CustomerTest {
    @Test
    public void testEqualsAndHashCode() {
        Customer customer1 = new Customer("John", "123");
        Customer customer2 = new Customer("John", "123");
        Customer customer3 = new Customer("Jane", "456");

        assertEquals(customer1, customer2);
        assertNotEquals(customer1, customer3);

        assertEquals(customer1.hashCode(), customer2.hashCode());
        assertNotEquals(customer1.hashCode(), customer3.hashCode());
    }
}
