import java.util.*;

public class Graph {

    private static ArrayList<Edge> graph = new ArrayList<>();
    private static int numOfVertices;

    public static void main(String[] args) {
        graph.add(new Edge(1, 2, 1));
        graph.add(new Edge(2, 3, 1));
        graph.add(new Edge(3, 4, 1));
        graph.add(new Edge(1, 2, 3));
        graph.add(new Edge(3, 4, 2));
        graph.add(new Edge(1, 4, 3));
        numOfVertices = findNumberOfVertices();
        shortestSubPathInPath();
    }

    public static void shortestSubPathInPath() {
        PriorityQueue<Edge> distanceFinder = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        int currentNode = 0;
        int[] distances = new int[numOfVertices];
        int[] parents = new int[numOfVertices];
        addToGraph(new Edge(1, 1, 0));
        distanceFinder.addAll(graph);
        for (int i = 0; i < numOfVertices; i++) {
            distances[i] = Integer.MAX_VALUE - 300;
        }
        distances[0] = 0;
        while (distanceFinder.size() != 0) {
            Edge u = distanceFinder.poll();
            currentNode = u.node1;
            for (Edge edge : graph) {
                if (edge.node1 == currentNode) {
                    if (distances[edge.node2 - 1] > distances[u.node1 - 1] + edge.cost) {
                        distances[edge.node2 - 1] = distances[u.node1 - 1] + edge.cost;
                        parents[edge.node2 - 1] = edge.node1;
                        distanceFinder.add(edge);
                        distanceFinder.add(new Edge(edge.node2, edge.node1, edge.cost));
                    }
                }
            }
        }
        parents[0] = -1;
        ArrayList<Integer> path = new ArrayList<>();
        ArrayList<Integer> costs = new ArrayList<>();
        path.add(1);
        extrapolatePath(parents, 4, path);

        for (int c = 0; c < path.size() - 1; c++) {
            for (Edge edge : graph) {
                if (edge.node1 == path.get(c) && edge.node2 == path.get(c + 1)) {
                    costs.add(edge.cost);
                }
            }
        }

        System.out.println(Collections.min(costs));
    }

    public static int findNumberOfVertices() {
        HashSet uniqueNumberFinder = new HashSet();
        for (int i = 0; i < graph.size(); i += 2) {
            uniqueNumberFinder.add(graph.get(i).node1);
            uniqueNumberFinder.add(graph.get(i).node2);
        }
        return uniqueNumberFinder.size();
    }


    public static void extrapolatePath(int[] parent, int j, ArrayList<Integer> path) {
        if (parent[j - 1] == -1)
            return;

        extrapolatePath(parent, parent[j - 1], path);
        path.add(j);
    }

    public static void addToGraph(Edge e) {
        graph.add(e);
        graph.add(new Edge(e.node2, e.node1, e.cost));
    }

    public static class Edge {
        private int node1, node2, cost;

        Edge(int node1, int node2, int cost) {
            this.node1 = node1;
            this.node2 = node2;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "node1=" + node1 +
                    ", node2=" + node2 +
                    ", cost=" + cost +
                    '}';
        }
    }
}
