import java.util.List;

public class BinarySearchTreeSorter {
    public static <T extends Comparable<T>> void sort(List<T> list) throws DuplicateElementException {
        BinarySearchTree<T> bst = new BinarySearchTree<T>();
        for (T element : list) {
            bst.add(element);
        }
        list.clear();
        list.addAll(bst.getInOrder());
    }
}
