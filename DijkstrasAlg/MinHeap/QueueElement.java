package MinHeap;

public interface QueueElement {
    public int getQueueIndex();

    public void setQueueIndex(int index);

    public int getKey();

    public void decreaseKey(int newValue);
}
