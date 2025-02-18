package timing;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayListGeneratorTester {
    @Test
    void testArrayListSize() {
        int problemSize = 1000;
        ArrayList<Integer> array = ArrayListGenerator.generateNearlyAscendingArray(problemSize);
        assertEquals(problemSize, array.size());
    }
}