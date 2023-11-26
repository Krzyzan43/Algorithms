import java.util.Iterator;

public class Intersector {
    public static OneWayLinkedList<Integer> intersect(
            OneWayLinkedList<Integer> a,
            OneWayLinkedList<Integer> b) {
        if (a.isEmpty() || b.isEmpty())
            return new OneWayLinkedList<>();

        OneWayLinkedList<Integer> result = new OneWayLinkedList<>();
        Iterator<Integer> aIt = a.iterator();
        Iterator<Integer> bIt = b.iterator();
        int aVal = aIt.next(), bVal = bIt.next();

        while (aIt.hasNext() && bIt.hasNext()) {
            if (aVal == bVal) {
                result.add(aVal);
                aVal = aIt.next();
                bVal = bIt.next();
            } else if (aVal < bVal) {
                aVal = aIt.next();
            } else if (aVal > bVal) {
                bVal = bIt.next();
            }
        }
        if (aVal == bVal)
            result.add(aVal);

        return result;
    }
}
