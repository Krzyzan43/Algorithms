public class Node<T> {
    public T value = null;
    public Node<T> front = null;
    public Node<T> back = null;

    public Node() {

    }

    public Node(T val) {
        value = val;
    }

    public void linkBack(Node<T> backNode) {
        backNode.front = this;
        back = backNode;
    }

    public void linkFront(Node<T> frontNode) {
        frontNode.back = this;
        front = frontNode;
    }
}
