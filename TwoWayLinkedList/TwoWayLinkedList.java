import java.util.Iterator;
import java.util.NoSuchElementException;

public class TwoWayLinkedList<T> implements Iterable<T> {
    private Node<T> startNode = null;
    private Node<T> endNode = null;
    private int size = 0;

    public void add(T value) {
        Node<T> node = new Node<T>(value);
        if(startNode == null)
            startNode = node;
        else
            node.linkBack(endNode);
        endNode = node;
        size++;
    }

    public void addAt(int index, T value) throws NoSuchElementException {
        if(index > size || index < 0)
            throw new NoSuchElementException();

        if(index == size)
            add(value);
        else if(index == 0) {
            Node<T> newNode = new Node<T>(value);
            newNode.linkFront(startNode);
            startNode = newNode;
            size++;
        } else {
            Node<T> frontNode = getNode(index);
            Node<T> backNode = frontNode.back;
            Node<T> insertNode = new Node<T>(value);
            insertNode.linkBack(backNode);
            insertNode.linkFront(frontNode);
            size++;
        }
    }

    public void clear() {
        startNode = null;
        endNode = null;
        size = 0;
    }

    public boolean contains(T value) {
        return findNodeByValue(value).index != -1;
    }

    public T get(int index) throws NoSuchElementException {
        if(index < 0 || index >= size)
            throw new NoSuchElementException();

        return getNode(index).value;
    }

    public void set(int index, T value) throws NoSuchElementException {
        if(index < 0 || index >= size)
            throw new NoSuchElementException();

        getNode(index).value = value;
    }

    public int indexOf(T value) {
        return findNodeByValue(value).index;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T removeAt(int index) throws NoSuchElementException {
        if(index < 0 || index >= size)
            throw new NoSuchElementException();

        return removeNode(getNode(index));
    }

    public boolean remove(T value) {
        SearchResult result = findNodeByValue(value);
        if(result.index != -1){
            removeNode(result.node);
            return true;
        }
        else {
            return false;
        }
    }

    public int size() {
        return size;
    }

    public Iterator<T> iterator() {
        return new TwoWayLinkedListIterator(startNode);
    }

    private T removeNode(Node<T> node) {
        if(size == 1) {
            startNode = null;
            endNode = null;
        }
        else if(node == startNode) {
            startNode = startNode.front;
            startNode.back = null;
        } 
        else if(node == endNode) {
            endNode = endNode.back;
            endNode.front = null;
        } else {
            Node<T> backNode = node.back;
            Node<T> frontNode = node.front;
            backNode.linkFront(frontNode);
        }
        size--;
        return node.value;
    }

    private SearchResult findNodeByValue(T value) {
        TwoWayLinkedListIterator it = new TwoWayLinkedListIterator(startNode); 
        int index = -1;
        while (it.hasNext()) {
            index++;
            Node<T> node = it.nextNode();
            if(node.value.equals(value)) {
                return new SearchResult(index, node);
            }
        }
        return new SearchResult(-1, null);
    }

    private Node<T> getNode(int index) {
        TwoWayLinkedListIterator it = new TwoWayLinkedListIterator(startNode); 
        for (int i = 0; i < index; i++) {
            it.next();
        }
        return it.nextNode;
    }

    private class SearchResult {
        public int index;
        public Node<T> node;

        public SearchResult(int index, Node<T> node) {
            this.index = index;
            this.node = node;
        }
    }

    private class TwoWayLinkedListIterator implements Iterator<T> {
        Node<T> nextNode = null;

        public TwoWayLinkedListIterator(Node<T> startNode) {
            this.nextNode = startNode;
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public T next() {
            return nextNode().value;
        }

        public Node<T> nextNode() {
            if(nextNode == null)
                throw new NoSuchElementException();
            
            Node<T> returnNode = nextNode;
            nextNode = nextNode.front;
            return returnNode;
        }
    }

    public void insert(
            TwoWayLinkedList<T> anotherList,
            int beforeIndex) throws NoSuchElementException {
        if(beforeIndex < 0 || beforeIndex > size)
            throw new NoSuchElementException();

        if(beforeIndex == size)
            insert(anotherList, (Node<T>)null);
        else
            insert(anotherList, getNode(beforeIndex));
    }

    public void insert(
            TwoWayLinkedList<T> anotherList,
            T beforeElement) throws NoSuchElementException {
        SearchResult result = findNodeByValue(beforeElement);
        if(result.index == -1)
            throw new NoSuchElementException();
        insert(anotherList, result.node);    
    }

    private void insert(TwoWayLinkedList<T> anotherList, Node<T> frontNode) {
        if(anotherList.size == 0)
            return;

        TwoWayLinkedList<T> copy = new TwoWayLinkedList<>();
        for (T val : anotherList)
            copy.add(val);
        Node<T> insertStart = copy.startNode;
        Node<T> insertEnd = copy.endNode;

        if(size == 0) { 
            startNode = insertStart;
            endNode = insertEnd;
        } else if(frontNode == null) {
            endNode.linkBack(insertStart);
            endNode = insertEnd;
        } else if(frontNode.back == null) {
            startNode.linkBack(insertEnd);
            startNode = insertStart;
        } else {
            Node<T> backNode = frontNode.back;
            backNode.linkFront(insertStart);
            frontNode.linkBack(insertEnd);
        }
        size+= anotherList.size;
    }
}
