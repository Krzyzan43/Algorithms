import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class HashMap<Key, Value> {
    private UniqueList<Key, Value>[] data;
    private int capacity;
    private double loadFactor;
    private Function<Key, Integer> hashFunction;
    private int elementCount;

    public HashMap(int initialSize, double loadFactor, Function<Key, Integer> hashFunction) {
        this.data = (UniqueList<Key, Value>[]) new UniqueList<?, ?>[initialSize];

        for (int i = 0; i < initialSize; i++) {
            data[i] = new UniqueList<>();
        }
        this.capacity = initialSize;
        this.loadFactor = loadFactor;
        this.hashFunction = hashFunction;
        this.elementCount = 0;
    }

    public void add(Key key, Value value) throws DuplicateKeyException {
        int index = hashFunction.apply(key) % capacity;
        data[index].add(key, value);

        elementCount++;
        if (needsResize()) {
            resize(capacity * 2);
        }
    }

    public void clear() {
        for (int i = 0; i < capacity; i++) {
            data[i].clear();
        }
        elementCount = 0;
    }

    public boolean containsKey(Key key) {
        int index = hashFunction.apply(key) % capacity;
        return data[index].containsKey(key);
    }

    public boolean containsValue(Value value) {
        for (int i = 0; i < capacity; i++) {
            if (data[i].containsValue(value))
                return true;
        }
        return false;
    }

    public int elements() {
        return elementCount;
    }

    public Value get(Key key) throws NoSuchElementException {
        int index = hashFunction.apply(key) % capacity;
        Entry<Key, Value> entry = data[index].findByKey(key);
        if (entry == null)
            throw new NoSuchElementException();
        return entry.value;
    }

    public void put(Key key, Value value) {
        int index = hashFunction.apply(key) % capacity;
        if (data[index].put(key, value)) {
            elementCount++;

            if (needsResize()) {
                resize(capacity * 2);
            }
        }

    }

    public Value remove(Key key) {
        int index = hashFunction.apply(key) % capacity;
        Value val = data[index].remove(key);
        if (val != null)
            elementCount--;
        return val;
    }

    public int size() {
        return capacity;
    }

    public void rehash(Function<Key, Integer> newHashFunction) {
        hashFunction = newHashFunction;
        resize(data.length);
    }

    private boolean needsResize() {
        return (double) elementCount / capacity > loadFactor;
    }

    private void resize(int newCapacity) {
        UniqueList<Key, Value>[] newData = (UniqueList<Key, Value>[]) new UniqueList<?, ?>[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newData[i] = new UniqueList<>();
        }

        for (int i = 0; i < capacity; i++) {
            UniqueList<Key, Value> list = data[i];
            for (Entry<Key, Value> entry : list) {
                int index = hashFunction.apply(entry.key) % newCapacity;
                newData[index].addUnchecked(entry);
            }
        }
        data = newData;
        capacity = newCapacity;
    }

    private static class Entry<Key, Value> {
        public Key key;
        public Value value;

        public Entry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    private static class UniqueList<Key, Value> implements Iterable<Entry<Key, Value>> {
        LinkedList<Entry<Key, Value>> list = new LinkedList<>();

        public void addUnchecked(Entry<Key, Value> entry) {
            list.add(entry);
        }

        public void add(Entry<Key, Value> entry) throws DuplicateKeyException {
            if (containsKey(entry.key))
                throw new DuplicateKeyException();
            else
                list.add(entry);
        }

        public void add(Key key, Value value) throws DuplicateKeyException {
            add(new Entry<>(key, value));
        }

        public boolean put(Key key, Value value) {
            Entry<Key, Value> entry = findByKey(key);
            if (entry == null) {
                list.add(new Entry<>(key, value));
                return true;
            } else {
                entry.value = value;
                return false;
            }
        }

        public boolean containsKey(Key key) {
            return findByKey(key) != null;
        }

        public boolean containsValue(Value value) {
            return findValue(value) != null;
        }

        public void clear() {
            list = new LinkedList<>();
        }

        public Entry<Key, Value> findValue(Value val) {
            for (Entry<Key, Value> entry : list) {
                if (entry.value.equals(val))
                    return entry;
            }
            return null;
        }

        public Entry<Key, Value> findByKey(Key key) {
            for (Entry<Key, Value> entry : list) {
                if (entry.key.equals(key))
                    return entry;
            }
            return null;
        }

        public Value remove(Key key) {
            Entry<Key, Value> entry = findByKey(key);
            if (entry != null) {
                list.remove(entry);
                return entry.value;
            } else {
                return null;
            }
        }

        @Override
        public Iterator<Entry<Key, Value>> iterator() {
            return list.iterator();
        }

    }
}