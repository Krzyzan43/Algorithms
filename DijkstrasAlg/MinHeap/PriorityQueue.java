package MinHeap;

import java.util.ArrayList;
import java.util.List;

public class PriorityQueue<T extends QueueElement> {
    private List<T> heap;

    public PriorityQueue() {
        heap = new ArrayList<>();
    }

    public void add(T element) {
        heap.add(element);
        element.setQueueIndex(heap.size() - 1);
        moveUp(heap.size() - 1);
    }

    public T findMin() {
        if (heap.isEmpty()) {
            return null;
        }
        return heap.get(0);
    }

    public T removeMin() {
        if (heap.isEmpty()) {
            return null;
        }

        T min = heap.get(0);
        T lastElement = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, lastElement);
            lastElement.setQueueIndex(0);
            sink(0);
        }

        return min;
    }

    public void decreaseKey(T element, int newValue) {
        if (newValue > element.getKey()) {
            throw new IllegalArgumentException("New value is greater than the current key");
        }

        element.decreaseKey(newValue);
        moveUp(element.getQueueIndex());
    }

    private void moveUp(int index) {
        T element = heap.get(index);

        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            T parent = heap.get(parentIndex);

            if (element.getKey() >= parent.getKey()) {
                break;
            }

            heap.set(index, parent);
            parent.setQueueIndex(index);
            index = parentIndex;
        }

        heap.set(index, element);
        element.setQueueIndex(index);
    }

    private void sink(int index) {
        T element = heap.get(index);
        int currentKey = element.getKey();

        while (index < heap.size()) {
            int leftChild = index * 2 + 1;
            int rightChild = index * 2 + 2;

            int leftKey = leftChild < heap.size() ? heap.get(leftChild).getKey() : Integer.MAX_VALUE;
            int rightKey = rightChild < heap.size() ? heap.get(rightChild).getKey() : Integer.MAX_VALUE;

            if (leftKey < currentKey || rightKey < currentKey) {
                if (leftKey < rightKey) {
                    index = swap(index, leftChild);
                } else {
                    index = swap(index, rightChild);
                }
            } else {
                return;
            }
        }
    }

    private int swap(int a, int b) {
        T aEl = heap.get(a);
        T bEl = heap.get(b);

        heap.set(a, bEl);
        bEl.setQueueIndex(a);

        heap.set(b, aEl);
        aEl.setQueueIndex(b);

        return b;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }
}