import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

// Proszę nie modyfikować tego pliku!
public class RadixSorterTests {
    @org.junit.jupiter.api.Test
    void test() {
        var expectedSteps = new ArrayList<int[]>();
        expectedSteps.add(new int[] {270, 980, 130, 990, 211, 272, 103, 654, 836, 548});
        expectedSteps.add(new int[] {103, 211, 130, 836, 548, 654, 270, 272, 980, 990});
        expectedSteps.add(new int[] {103, 130, 211, 270, 272, 548, 654, 836, 980, 990});
        var checker = new Checker(expectedSteps);
        var sorter = new RadixSorter(checker);
        sorter.sort(new int[] {270, 980, 654, 130, 211, 548, 990, 836, 272, 103});
        assertNotEquals(0, checker.getChecksInvoked());
    }
}
