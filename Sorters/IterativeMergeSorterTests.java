import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

// Proszę nie modyfikować tego pliku!
public class IterativeMergeSorterTests {
    @org.junit.jupiter.api.Test
    void test() {
        var expectedSteps = new ArrayList<int[]>();
        expectedSteps.add(new int[] {270, 980, 130, 654, 211, 548, 836, 990, 103, 272});
        expectedSteps.add(new int[] {130, 270, 654, 980, 211, 548, 836, 990, 103, 272});
        expectedSteps.add(new int[] {130, 211, 270, 548, 654, 836, 980, 990, 103, 272});
        expectedSteps.add(new int[] {103, 130, 211, 270, 272, 548, 654, 836, 980, 990});
        var checker = new Checker(expectedSteps);
        var sorter = new IterativeMergeSorter(checker);
        sorter.sort(new int[] {270, 980, 654, 130, 211, 548, 990, 836, 272, 103});
        assertNotEquals(0, checker.getChecksInvoked());
    }
}
