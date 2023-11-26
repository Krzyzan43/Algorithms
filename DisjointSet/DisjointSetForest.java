public class DisjointSetForest implements IDisjointSetStructure {
    int[] parents;
    int[] rank;

    public DisjointSetForest(int size) {
        parents = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parents[i] = i;
        }
        for (int i = 0; i < size; i++) {
            rank[i] = 1;
        }
    }

    @Override
    public int findSet(int item) throws ItemOutOfRangeException {
        if (item < 0 || item >= parents.length)
            throw new ItemOutOfRangeException();

        return findSetRecursive(item);
    }

    @Override
    public void union(int item1, int item2) throws ItemOutOfRangeException {
        if (item1 < 0 || item1 >= parents.length || item2 < 0 || item2 >= parents.length)
            throw new ItemOutOfRangeException();

        int set1 = findSetRecursive(item1);
        int set2 = findSetRecursive(item2);

        if (rank[set1] > rank[set2]) {
            parents[set2] = set1;
        } else if (rank[set1] < rank[set2]) {
            parents[set1] = set2;
        } else {
            parents[set2] = set1;
            rank[set1]++;
        }
    }

    private int findSetRecursive(int item) {
        if (parents[item] == item) {
            return item;
        } else {
            int set = findSetRecursive(parents[item]);
            parents[item] = set;
            return set;
        }
    }
}
