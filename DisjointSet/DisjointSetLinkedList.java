public class DisjointSetLinkedList implements IDisjointSetStructure {
    Node[] arr;

    public DisjointSetLinkedList(int size) {
        arr = new Node[size];
        for (int i = 0; i < size; i++) {
            arr[i] = new Node(i);
        }
    }

    @Override
    public int findSet(int item) throws ItemOutOfRangeException {
        if (item < 0 || item >= arr.length)
            throw new ItemOutOfRangeException();

        return arr[item].rep;
    }

    @Override
    public void union(int itemA, int itemB) throws ItemOutOfRangeException {
        if (itemA < 0 || itemA >= arr.length || itemB < 0 || itemB >= arr.length)
            throw new ItemOutOfRangeException();

        int setA = arr[itemA].rep;
        int setB = arr[itemB].rep;
        if (arr[setA].rank < arr[setB].rank) {
            int buffer = setA;
            setA = setB;
            setB = buffer;
        }

        int item = arr[setB].last;
        do {
            arr[item].rep = setA;
            item = arr[item].next;
        } while (item != -1);

        arr[setB].next = arr[setA].last;
        arr[setA].last = arr[setB].last;
        arr[setA].rank += arr[setB].rank;
    }
}

class Node {
    public int rep;
    public int last;
    public int next;
    public int rank;

    public Node(int index) {
        rep = index;
        last = index;
        next = -1;
        rank = 1;
    }
}