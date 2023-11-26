import java.util.Iterator;
import java.util.NoSuchElementException;

public class OneWayLinkedList<T> implements IList<T> {
    private Node<T> startNode = null;
    private Node<T> endNode = null;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> node = new Node<T>(value);
        if(startNode == null)
            startNode = node;
        else
            endNode.front = node;

        endNode = node;
        size++;
    }

    @Override
    public void addAt(int index, T value) throws NoSuchElementException {
        if(index > size || index < 0)
            throw new NoSuchElementException();

        if(index == size)
            add(value);
        else if(index == 0) {
            Node<T> newNode = new Node<T>(value);
            newNode.front = startNode;
            startNode = newNode;
        } else {
            Node<T> backNode = getNode(index - 1);
            Node<T> frontNode = backNode.front;
            Node<T> insertNode = new Node<T>(value);
            backNode.front = insertNode;
            insertNode.front = frontNode;
        }
        size++;
    }

    @Override
    public void clear() {
        startNode = null;
        endNode = null;
        size = 0;
    }

    @Override
    public boolean contains(T value) {
        return findNodeWithValue(value).node != null;
    }

    @Override
    public T get(int index) throws NoSuchElementException {
        if(index < 0 || index > size)
            throw new NoSuchElementException();
        return getNode(index).value;
    }

    @Override
    public void set(int index, T value) throws NoSuchElementException {
        if(index < 0 || index > size)
            throw new NoSuchElementException();
        Node<T> nodeSet = getNode(index);
        nodeSet.value = value;
    }

    @Override
    public int indexOf(T value) {
        return findNodeWithValue(value).index;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T removeAt(int index) throws NoSuchElementException {
        if(index < 0 || index > size)
            throw new NoSuchElementException();
            
        if(index == 0){
            return removeInFront(null);
        }
        else {
            Node<T> backNode = getNode(index-1);
            return removeInFront(backNode);
        }
    }

    @Override
    public boolean remove(T value) {
        SearchResult result = findNodeWithValue(value);
        if(result.index == -1) 
            return false;
        removeInFront(result.backNode);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new OneWayLinkedListIterator(startNode);
    }

    private Node<T> getNode(int index) throws NoSuchElementException {
        if(index < 0 || index > size)
            throw new NoSuchElementException();
        
        var it = new OneWayLinkedListIterator(startNode);
        for (int i = 0; i < index; i++) {
            it.nextNode();
        }
        return it.nextNode();
    }

    private SearchResult findNodeWithValue(T value) {
        var it = new OneWayLinkedListIterator(startNode);
        int index = 0;
        Node<T> backNode = null;

        while (it.hasNext()) {
            Node<T> node = it.nextNode();
            if(node.value.equals(value)){
                return new SearchResult(node, backNode, index);
            }
            backNode = node;
            index++;
        }

        return new SearchResult(null, null, -1);
    }

    private T removeInFront(Node<T> node) {
        T returnVal;
        if(node == null){
            returnVal = startNode.value;
            startNode = startNode.front;
        } else {
            returnVal = node.front.value;
            Node<T> toRemove = node.front;
            node.front = toRemove.front;
        }
        size--;
        return returnVal;
    }

    private class SearchResult {
        public Node<T> backNode;
        public Node<T> node;
        public int index;

        public SearchResult(Node<T> node, Node<T> backNode,  int index) {
            this.node = node;
            this.index = index;
            this.backNode = backNode;
        }
    }

    private class OneWayLinkedListIterator implements Iterator<T> {
        private Node<T> nextNode;

        public OneWayLinkedListIterator(Node<T> startNode) {
            nextNode = startNode;
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public T next() {
            if(nextNode ==null)
                throw new NoSuchElementException();

            T returnValue = nextNode.value;
            nextNode = nextNode.front;
            return returnValue;
        }

        public Node<T> nextNode() {
            if(nextNode ==null)
                throw new NoSuchElementException();

            Node<T> returnNode = nextNode;
            nextNode = nextNode.front;
            return returnNode;
        }
    }
}
