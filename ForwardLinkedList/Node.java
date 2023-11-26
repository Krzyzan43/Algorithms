public class Node<T> {
    public T value = null;
    public Node<T> front = null;

    public Node() {
    }

    public Node(T val) {
        value = val;
    }

    public void linkFront(Node<T> front) {
        this.front = front;
    }
}
