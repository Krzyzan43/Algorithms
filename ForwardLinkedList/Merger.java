import java.util.Iterator;

public class Merger {
    public static OneWayLinkedList<Integer> merge(
            OneWayLinkedList<Integer> a,
            OneWayLinkedList<Integer> b) {
        if(a.isEmpty() && b.isEmpty()) return new OneWayLinkedList<>();
        if(a.isEmpty() && !b.isEmpty()) return copy(b);
        if(!a.isEmpty() && b.isEmpty()) return copy(a);

        OneWayLinkedList<Integer> result = new OneWayLinkedList<>();
        Iterator<Integer> aIt = a.iterator();
        Iterator<Integer> bIt = b.iterator();
        int aVal = aIt.next(), bVal = bIt.next();

        while (aIt.hasNext() || bIt.hasNext()) {
            boolean incrementA;
            if(aVal < bVal) {
                result.add(aVal);
                incrementA = true;
            } else {
                result.add(bVal);
                incrementA = false;
            }

            if(aIt.hasNext() != bIt.hasNext()) {
                result.add(aVal < bVal ? bVal : aVal);
                Iterator<Integer> aliveIterator = aIt.hasNext() ? aIt : bIt;
                while (aliveIterator.hasNext()) {
                    result.add(aliveIterator.next());
                }
                return result;
            }

            if(incrementA) {
                aVal = aIt.next();
            } else 
                bVal = bIt.next();
        }

        return result;
    }

    static OneWayLinkedList<Integer> copy(OneWayLinkedList<Integer> list) {
        OneWayLinkedList<Integer> copy = new OneWayLinkedList<>();
        for (Integer integer : list) {
            copy.add(integer);
        }
        return copy;
    }
}
