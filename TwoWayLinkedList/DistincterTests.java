import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DistincterTests {

    @org.junit.jupiter.api.Test
    void distinctEmptyList() {
        var list = new TwoWayLinkedList<Integer>();

        var distinctList = Distincter.distinct(list);

        assertListContent(distinctList, new Integer[] {});
    }

    @org.junit.jupiter.api.Test
    void distinctAlreadyDistinctList() {
        var list = new TwoWayLinkedList<Integer>();

        list.add(1);
        list.add(2);
        list.add(3);

        var distinctList = Distincter.distinct(list);

        assertListContent(distinctList, new Integer[] {1, 2, 3});
    }

    @org.junit.jupiter.api.Test
    void distinct3Values() {
        var list = new TwoWayLinkedList<Integer>();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(3);

        var distinctList = Distincter.distinct(list);

        assertListContent(distinctList, new Integer[] {1, 2, 3});
    }

    private <T> void assertListContent(TwoWayLinkedList<T> list, T[] expectedContent) {
        var iterator = list.iterator();

        for (var i = 0; i < expectedContent.length; i++) {
            if (iterator.hasNext() == false) {
                fail("Didn't expect the list to end.");
            }

            assertEquals(expectedContent[i], iterator.next());
        }

        assertFalse(iterator.hasNext());
    }
}
