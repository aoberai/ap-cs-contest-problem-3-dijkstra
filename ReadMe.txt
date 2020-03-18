- Introduction
Objective was to implement an algorithm which parsed a file and created a bi-directional graph data structure linking nodes by given costs and then finding the path with smallest cost
and finally finding which sub link within the path had the largest cost. Refer to Question.txt.
I attempted to solve this problem through dijkstra which would provide me with the path with the minimum cost to the target. I then planned
to store an array of the path and then would find the largest cost within the path.

- How this fulfills / does not fulfill the specification
This does not fulfill the specification as the question was misunderstood by me. I thought you had to find the path with the minimum cost to the goal node and
then would filter out each link's cost finding the largest value representing that link where it was really asking of me to find the minimum path
by evaluating the cost as something completely different; not as the sum of all the links in the path but instead as the maximum link in the path to
the target node. This misunderstanding gave me a different outcome as desired.

The following were stuff I successfully implemented as part of the problem:
-Bidirectional graph data structure
-Dijkstra algorithm
-File parser for graph population

- Explanation of current errors (or anything that falls short of project specifications) and how you might fix them
My main error falls in me misunderstanding the problem which led to all the test cases failing. The only way to truly fix this is to rewrite the cost filtering system
by slightly modifying the dijkstra algorithm. One idea I have incorporates associating a cost of a path not as the summation of previous costs but
as the largest cost found in a given path.


- Overview of the code explaining, in broad strokes, the structure of it
For this problem, everything fits in the Dijkstra class. It stores all the important methods to evaluate the path with the minimum cost and also stores the method to later extrapolate the route
to the target node. In addition, it contains the method used to parse the file for data as well as a static class called Edge which stores 3 attributes:
node1, node2, and cost. That allows me to create an adjacency list where every connection in a graph was stored in the graph arraylist.
I made the graph bidirectional by creating a custom add method that added not only the wanted edge but also it's reversed edge
allowing me to not actually modify the dijkstra algorithm itself. The dijkstra algorithm works by sorting all connections in a priority queue
by cost and then incrementally finding adjacent nodes and adding the summation of the path before it with the adjacent nodes cost and storing that
in the array distance which stores the distance to every node in the graph. Since the paths are constantly reevaluated, more efficient paths overwrite
previous paths leaving you at the end with the most efficient path to the target. Combining this with a parent array which stores the
parent node of each node which was found when finding the optimal path,  you can recursively travel back from the target nodes parent back
to the source node. Given the most optimal path to the target node, you can find the largest link in the path.

- Discussion of major challenges encountered in the process of making the code
Something broad that was a real challenge for me was understanding the dijkstra algorithm and how it works. It took me hours to implement it and
understand the pseudo code sufficiently.
Something smaller that took me a really long time to figure out was incrementing Math.max gives you Math.min. I spent a really long time
until I figured this out as I thought there was an issue with my algorithm.
- Acknowledgements (who helped you, what resources you used etc.)
Understanding of the algorithm:
https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/
https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-using-priority_queue-stl/
https://www.youtube.com/watch?v=GazC3A4OQTE

Used to find how to save the most optimal path to the target node.
https://www.geeksforgeeks.org/printing-paths-dijkstras-shortest-path-algorithm/

Additionally, a friend who has a good understanding of dijkstra, Luca Manolache, aided me in understanding the algorithm as well.