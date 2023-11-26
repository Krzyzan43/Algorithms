import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import MinHeap.*;

public class Graph<T> {
    Map<T, Vertex> vertices = new HashMap<>();
    PriorityQueue<Vertex> queue = new PriorityQueue<>();

    public Graph(List<Edge<T>> edges) {

        for (Edge<T> edge : edges) {
            T node1 = edge.getNode1();
            T node2 = edge.getNode2();

            if (!vertices.containsKey(node1)) {
                vertices.put(node1, new Vertex());
            }
            if (!vertices.containsKey(node2)) {
                vertices.put(node2, new Vertex());
            }
        }

        for (Edge<T> edge : edges) {
            Vertex vert1 = vertices.get(edge.getNode1());
            Vertex vert2 = vertices.get(edge.getNode2());

            vert1.connected.add(new Connection(vert2, edge.getDistance()));
            vert2.connected.add(new Connection(vert1, edge.getDistance()));
        }
    }

    public Map<T, Integer> calculateShortestPaths(T startNode) throws NoSuchElementException {
        if (!vertices.containsKey(startNode))
            throw new NoSuchElementException();

        Vertex start = vertices.get(startNode);
        start.decreaseKey(0);
        queue.add(start);

        while (queue.isEmpty() == false) {
            Vertex current = queue.removeMin();
            calcConnectedPaths(current, queue);
            current.isDead = true;
        }

        for (Vertex vertex : vertices.values()) {
            vertex.isDead = false;
        }

        return getDistancesWithoutNode(startNode);
    }

    private void calcConnectedPaths(Vertex current, PriorityQueue<Vertex> queue) {
        for (Connection connection : current.connected) {
            Vertex other = connection.other;
            if (other.isDead)
                continue;

            int pathSize = current.getKey() + connection.weight;

            if (pathSize < other.getKey()) {
                if (other.getKey() == Integer.MAX_VALUE) {
                    other.decreaseKey(pathSize);
                    queue.add(other);
                } else {
                    queue.decreaseKey(other, pathSize);
                }
            }
        }
    }

    private Map<T, Integer> getDistancesWithoutNode(T node) {
        Map<T, Integer> distances = new HashMap<>();

        for (var entry : vertices.entrySet()) {
            distances.put(entry.getKey(), entry.getValue().getKey());
        }

        distances.remove(node);
        return distances;
    }

    public Integer calculateShortestPath(T startNode, T endNode) throws NoSuchElementException {
        if (!vertices.containsKey(startNode) || !vertices.containsKey(endNode))
            throw new NoSuchElementException();

        Vertex start = vertices.get(startNode);
        Vertex end = vertices.get(endNode);
        start.decreaseKey(0);
        queue.add(start);

        while (queue.isEmpty() == false) {
            Vertex current = queue.removeMin();
            if (current == end) {
                for (Vertex vertex : vertices.values()) {
                    vertex.isDead = false;
                }
                return end.getKey();
            }

            calcConnectedPaths(current, queue);
            current.isDead = true;
        }

        return Integer.MAX_VALUE;
    }
}

class Connection {
    public int weight;
    public Vertex other;

    public Connection(Vertex other, int weight) {
        this.other = other;
        this.weight = weight;
    }
}

class Vertex implements QueueElement {
    public List<Connection> connected = new LinkedList<>();
    public boolean isDead = false;

    private int queueIndex = 0;
    private int shortestPath = Integer.MAX_VALUE;

    public int getKey() {
        return shortestPath;
    }

    public void decreaseKey(int newValue) {
        shortestPath = newValue;
    }

    @Override
    public String toString() {
        return "" + shortestPath;
    }

    @Override
    public int getQueueIndex() {
        return queueIndex;
    }

    @Override
    public void setQueueIndex(int index) {
        queueIndex = index;
    }
}