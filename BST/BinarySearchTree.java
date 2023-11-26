import java.util.ArrayList;
import java.util.Arrays;

public class BinarySearchTree<T extends Comparable<T>> {
    Node<T> root;

    public void add(T value) throws DuplicateElementException {
        if (root == null) {
            root = new Node<T>(value);
        } else {
            add(root, value);
        }
    }

    private void add(Node<T> node, T value) throws DuplicateElementException {
        int result = value.compareTo(node.value);
        if (result < 0) {
            if (node.left == null)
                node.left = new Node<T>(value);
            else
                add(node.left, value);
        } else if (result > 0) {
            if (node.right == null)
                node.right = new Node<T>(value);
            else
                add(node.right, value);
        } else
            throw new DuplicateElementException();

    }

    public boolean contains(T value) {
        return contains(root, value);
    }

    private boolean contains(Node<T> node, T value) {
        if (node == null)
            return false;

        int result = value.compareTo(node.value);
        if (result == 0)
            return true;
        else if (result < 0)
            return contains(node.left, value);
        else
            return contains(node.right, value);
    }

    public void delete(T value) {
        root = delete(root, value);
    }

    // Returns replacement for current node
    // If there's nothing to be deleted, returns current node
    private Node<T> delete(Node<T> current, T value) {
        if (current == null)
            return null;

        int result = value.compareTo(current.value);

        // Delete current node
        if (result == 0) {
            if (current.left == null && current.right == null) {
                return null;
            } else if (current.left == null) {
                return current.right;
            } else if (current.right == null) {
                return current.left;
            }

            // Has both children
            Node<T> smallestRightParent = current; // Find smallest node on the right
            Node<T> smallestRight = current.right;
            while (smallestRight.left != null) {
                smallestRightParent = smallestRight;
                smallestRight = smallestRight.left;
            }

            current.value = smallestRight.value; // Replace smallest node with current and delete current node
            if (smallestRightParent.left == smallestRight) {
                smallestRightParent.left = null;
            } else
                smallestRightParent.right = null;
            return current;

        } else if (result < 0) {
            current.left = delete(current.left, value);
            return current;
        } else {
            current.right = delete(current.right, value);
            return current;
        }
    }

    public String toStringPreOrder() {
        return toString(Order.PreOrder);
    }

    public String toStringInOrder() {
        return toString(Order.InOrder);
    }

    public String toStringPostOrder() {
        return toString(Order.PostOrder);
    }

    public ArrayList<T> getInOrder() {
        ArrayList<T> list = new ArrayList<>();
        walkInOrder(root, list);
        return list;
    }

    private String toString(Order order) {
        ArrayList<T> list = new ArrayList<>();
        switch (order) {
            case PreOrder:
                walkPreOrder(root, list);
                break;
            case InOrder:
                walkInOrder(root, list);
                break;
            case PostOrder:
                walkPostOrder(root, list);
                break;
        }

        String str = Arrays.toString(list.toArray());
        return str.substring(1, str.length() - 1);
    }

    private void walkPreOrder(Node<T> current, ArrayList<T> array) {
        if (current == null)
            return;

        array.add(current.value);
        walkPreOrder(current.left, array);
        walkPreOrder(current.right, array);
    }

    private void walkInOrder(Node<T> current, ArrayList<T> array) {
        if (current == null)
            return;

        walkInOrder(current.left, array);
        array.add(current.value);
        walkInOrder(current.right, array);
    }

    private void walkPostOrder(Node<T> current, ArrayList<T> array) {
        if (current == null)
            return;

        walkPostOrder(current.left, array);
        walkPostOrder(current.right, array);
        array.add(current.value);
    }
}

class Node<T extends Comparable<T>> {
    public Node<T> left;
    public Node<T> right;
    public T value;

    public Node(T value) {
        this.value = value;
    }
}

enum Order {
    PreOrder,
    InOrder,
    PostOrder
}