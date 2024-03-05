package hh.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class MainTest {
    @Test
    void ExampleTest() {
        List<Integer> intsA = new ArrayList<>(Arrays.asList(5, 2, 4, 6, 1, 3, 2, 6));
        Main.sort(intsA, 1, intsA.size());
        assertEquals(1, intsA.get(0));
        assertEquals(2, intsA.get(1));
        assertEquals(2, intsA.get(2));
        assertEquals(3, intsA.get(3));
        assertEquals(4, intsA.get(4));
        assertEquals(5, intsA.get(5));
        assertEquals(6, intsA.get(6));
        assertEquals(6, intsA.get(7));
    }

    @Test
    void twoNumbers() {
        List<Integer> intsA = new ArrayList<>(Arrays.asList(5, 2));
        Main.sort(intsA, 1, intsA.size());
        assertEquals(2, intsA.get(0));
        assertEquals(5, intsA.get(1));
    }

    @Test
    void oneNumbers() {
        List<Integer> intsA = new ArrayList<>(Arrays.asList(5));
        Main.sort(intsA, 1, intsA.size());
        assertEquals(5, intsA.get(0));
    }

    @Test
    void initSortedList() {
        List<Integer> intsA = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        Main.sort(intsA, 1, intsA.size());
        assertEquals(1, intsA.get(0));
        assertEquals(2, intsA.get(1));
        assertEquals(3, intsA.get(2));
        assertEquals(4, intsA.get(3));
    }

}
