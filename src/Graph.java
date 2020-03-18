import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Class that runs dijkstra on a bidirectional graph, finds the shortest path, and prints the maximum cost link within the path.
 * @author adityaoberai
 */
public class Graph {

    private static ArrayList<Edge> graph = new ArrayList<>();
    private static int[] distances, parents;
    private static int goalNode;

    /**
     * Tester method for contest problem
     * @param args Required for java main method.
     */
    public static void main(String[] args) throws FileNotFoundException {
        //Population of graph
        parseFile();

        distances = new int[goalNode];
        parents = new int[goalNode];

        //Finds distance to every node
        dijkstra();

        //Finds maximum cost link in path to goal node.
        printMaximumLinkInPath(goalNode);
    }

    /**
     * takes data from file and adds it to array
     *
     * @throws FileNotFoundException
     */
    public static void parseFile() throws FileNotFoundException {
        String filename = "resources/Input.txt";
        //parses file and puts numbers in array
        try (Scanner s = new Scanner(new File(filename))) {
            goalNode = s.nextInt();
            int halfNumOfEdges = s.nextInt();
            for (int i = 0; i < halfNumOfEdges; i++) {
                addToGraph(s.nextInt(), s.nextInt(), s.nextInt());
            }
        }
    }

    /**
     * Adds edge to graph and the edge's conjugate making the graph bidirectional.
     * @param node1 First node in edge connection
     * @param node2 Second node in edge connection
     * @param cost Cost to cross this edge.
     */
    public static void addToGraph(int node1, int node2, int cost) {
        graph.add(new Edge(node1, node2, cost));
        graph.add(new Edge(node2, node1, cost));
    }

    /**
     * Finds the distance from the source vertex, node 1, to every other node in the graph and
     * populates the data into the distances array.
     */
    public static void dijkstra() {
        PriorityQueue<Edge> distanceFinder = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        int currentNode = 0;
        addToGraph(1, 1, 0);
        distanceFinder.addAll(graph);
        for (int i = 0; i < goalNode; i++) {
            distances[i] = parents[i] = Integer.MAX_VALUE - 300;
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
        System.out.println(Arrays.toString(distances));
    }

//    /**
//     * Finds the number of vertices in the graph by adding every vertex into a unique hash set
//     * and finding the total number of objects in the hash set.
//     * @return Number of unique vertices in graph.
//     */
//    public static int findNumberOfVertices() {
//        HashSet<Integer> uniqueNumberFinder = new HashSet<Integer>();
//        for (int i = 0; i < graph.size(); i += 2) {
//            uniqueNumberFinder.add(graph.get(i).node1);
//            uniqueNumberFinder.add(graph.get(i).node2);
//        }
//        return uniqueNumberFinder.size();
//    }

    /**
     * Prints maximum link in path to goal node by finding cost of each link in minimum path and finding the maximum of those.
     * @param goalNode Destination node to get path for.
     */
    public static void printMaximumLinkInPath(int goalNode) {
        ArrayList<Integer> path = new ArrayList<>();
        ArrayList<Integer> costs = new ArrayList<>();
        path.add(1);
        extrapolatePath(parents, goalNode, path);

        for (int c = 0; c < path.size() - 1; c++) {
            for (Edge edge : graph) {
                if (edge.node1 == path.get(c) && edge.node2 == path.get(c + 1)) {
                    costs.add(edge.cost);
                }
            }
        }
        System.out.println(Collections.max(costs));
    }

    /**
     * Recursively finds node path to goal node. Method credit to: https://www.geeksforgeeks.org/printing-paths-dijkstras-shortest-path-algorithm/
     * @param parent Array that stores the each nodes parent node.
     * @param j Node for which to find path.
     * @param path ArrayList to store path.
     */
    public static void extrapolatePath(int[] parent, int j, ArrayList<Integer> path) {
        if (parent[j - 1] == -1)
            return;

        extrapolatePath(parent, parent[j - 1], path);
        path.add(j);
    }

    /**
     * Edge class that represents a connection between 2 nodes and the cost to travel between them.
     */
    public static class Edge {
        private int node1, node2, cost;

        /**
         * Constructor initializing edge instance with given parameters.
         * @param node1,  @param node2 2 nodes used create a a path.
         * @param cost Cost to travel path.
         */
        Edge(int node1, int node2, int cost) {
            this.node1 = node1;
            this.node2 = node2;
            this.cost = cost;
        }

        /**
         * Represents edge class by showing all attributes of object.
         * @return String representing edge class.
         */
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
