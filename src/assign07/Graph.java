package assign07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * An implementation of Graph, contains a nested class Vertex, provided methods
 * to create, add edge, read dot files, etc. Also implements DFS, BFS, and
 * topological sort algorithms.
 *
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-03-05
 */
public class Graph<Type> {
    private HashMap<Type, Vertex<Type>> allVerties;
    private HashMap<Type, Vertex<Type>> checkCyclic;

    /**
     * A nested vertex class, contains information of each vertex and the
     * relationship between current vertex and other vertices. Provides
     * addNeighbor, addParent methods.
     */
    private static class Vertex<Type> {
        public Type data;
        public ArrayList<Vertex<Type>> neighbors, parents;
        public boolean isVisited;
        public Vertex<Type> from;
        public int inDegree;
        public Vertex(Type data) {
            this.data = data;
            neighbors = new ArrayList<>();
            parents = new ArrayList<>();
            isVisited = false;
            inDegree = 0;
            from = null;
        }

        /**
         * to add a newNeighbor to the neighbors list of current vertex
         *
         * @param newNeighbor the new neighbor's vertex
         */
        public void addNeighbor(Vertex<Type> newNeighbor) {
            neighbors.add(newNeighbor);
        }

        /**
         * to add a newParent to the parents list of current vertex
         *
         * @param newParent the new parent's vertex
         */
        public void addParent(Vertex<Type> newParent) {
            parents.add(newParent);
        }

        public String toString() {
            StringBuilder info = new StringBuilder();
            for (Vertex<Type> neighbor : neighbors) {
                info.append("  ").append(data).append(" -> ").append(neighbor.data).append(";\n");
            }
            return info.toString();
        }
    }

    /**
     * Default constructor of Graph class.
     */
    public Graph() {
        allVerties = new HashMap<>();
        checkCyclic = new HashMap<>();
    }

    /**
     * Constructor that can read a dot file and turn to graph.
     *
     * @param filepath the file path in the computer
     */
    public Graph(String filepath) {
        allVerties = new HashMap<>();
        checkCyclic = new HashMap<>();
        readGraph(filepath);
    }

    /**
     * to add a new vertex into the graph.
     *
     * @param data the data that to be stored in the new vertex
     */
    public void addVertex(Type data) {
        allVerties.put(data, new Vertex<>(data));
    }

    /**
     * to add a relationship between target and neighbor vertices.
     * if target or neighbor vertex wasn't exist in the graph, then
     * create them first.
     *
     * @param target the data that store in the target vertex
     * @param neighbor the data that store in the neighbor vertex
     */
    public void addEdge(Type target, Type neighbor) {
        if (!allVerties.containsKey(target)) {
            addVertex(target);
        }
        Vertex<Type> cur = allVerties.get(target);
        Vertex<Type> neighborNode;
        if (!allVerties.containsKey(neighbor)) {
            neighborNode = new Vertex<>(neighbor);
            allVerties.put(neighbor, neighborNode);
        } else {
            neighborNode = allVerties.get(neighbor);
        }
        cur.addNeighbor(neighborNode);
        neighborNode.addParent(cur);
        neighborNode.inDegree++;
    }

    /**
     * to detect weather this graph contains target vertex.
     *
     * @param target the data store in the target vertex
     * @return true if found, false if it doesn't
     */
    public boolean contains(Type target) {
        for (Type data : allVerties.keySet()) {
            if (target.equals(data)) return true;
        }
        return false;
    }

    /**
     * read a dot file and extract the information in that, turn it into a graph.
     *
     * @param filepath the file path on this computer
     */
    public void readGraph(String filepath) {
        Scanner sc;
        try {
            File file = new File(filepath);
            sc = new Scanner(file);
            boolean hasReadHead = false;
            while (sc.hasNextLine()) {
                if (!hasReadHead && sc.nextLine().split(" ")[0].equals("digraph")) {
                    hasReadHead = true;
                }
                String[] relationship = sc.nextLine().split("->");
                if (relationship[0].equals("}")) break;
                Type target = (Type)relationship[0].replace("\t", "");
                Type neighbor = (Type)relationship[1];
                addEdge(target, neighbor);
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
    }

    public String toString() {
        StringBuilder graphInfo = new StringBuilder();
        graphInfo.append("digraph G {\n");
        for (Type nodeName : allVerties.keySet()) {
            Vertex<Type> node = allVerties.get(nodeName);
            graphInfo.append(node.toString());
        }
        graphInfo.append("}");
        return graphInfo.toString();
    }

    /**
     * the driver method to detect if there are cyclic structure in this graph.
     *
     * @return true if found a circle, false if it not found
     */
    public boolean isCyclic() {
        boolean isCyclic = false;
        for (Type data : allVerties.keySet()) {
            Vertex<Type> cur = allVerties.get(data);
            checkCyclic = new HashMap<>();
            isCyclic = (isCyclicRecur(cur));
            if (isCyclic) break;
        }
        return isCyclic;
    }

    /**
     * the recursive part of checking cyclic method.
     *
     * @param cur the current Vertex
     * @return true if found a circle, false if it not found
     */
    private boolean isCyclicRecur(Vertex<Type> cur) {
        boolean detectedCycle = false;
        // reach the end leaf
        if (cur.neighbors.isEmpty()) {
            checkCyclic.put(cur.data, cur);
            return false;
        }
        for (Vertex<Type> neighbor : cur.neighbors) {
            // if found neighbor in the hashmap
            if (checkCyclic.containsKey(neighbor.data) && !neighbor.neighbors.isEmpty()) return true;
            checkCyclic.put(neighbor.data, neighbor);
            if (isCyclicRecur(neighbor)) return true;
        }
        return detectedCycle;
    }

    /**
     * a private helper method to reset all the vertices to its original situation,
     * so that we can conduce all the algorithm below again successfully.
     */
    private void reset() {
        for (Type data : allVerties.keySet()) {
            Vertex<Type> cur = allVerties.get(data);
            cur.isVisited = false;
            cur.from = null;
            cur.inDegree = cur.parents.size();
        }
    }

    /**
     * A recursive DepthFirstSearch algorithm, trying to find a path from the source
     * to the destination.
     *
     * @param source the data that store in the source vertex
     * @param destination the data that store in the destination vertex
     * @return true if found a path, false if it doesn't.
     */
    public boolean depthFirstSearch(Type source, Type destination) {
        if (!allVerties.containsKey(destination) || !allVerties.containsKey(source)) return false;
        if (source.equals(destination)) return true;
//        reset();
        return dfsRecur(source, destination);
    }

    /**
     * The recursive part of dfs algorithm.
     *
     * @param source the data that store in the source vertex
     * @param destination the data that store in the destination vertex
     * @return true if found a path, false if it doesn't
     */
    private boolean dfsRecur(Type source, Type destination) {
        Vertex<Type> cur = allVerties.get(source);
        // base case1: already visited
        if (cur.isVisited) return false;
        cur.isVisited = true;
        // base case2: found
        if (cur.data.equals(destination)) return true;
        // base case3: reaching leaf node
        if (cur.neighbors.isEmpty()) return false;
        // recursive call
        for (Vertex<Type> neighbor : cur.neighbors) {
            // if found, return true, otherwise keep searching
            if (dfsRecur(neighbor.data, destination)) return true;
        }
        return false;
    }

    /**
     * A BreathFirstSearch method, to find the shortest path from source vertex to
     * destination vertex.
     *
     * @param source the data that store in the source vertex
     * @param destination the data that store in the destination vertex
     * @return a List of data, shows the shortest path from source to destination
     */
    public List<Type> breadthFirstSearch(Type source, Type destination) {
        List<Type> returnList = new ArrayList<>();
        Queue<Vertex<Type>> myQueue = new LinkedList<>();
        if (source.equals(destination)) {
            returnList.add(source);
            return returnList;
        }
        reset();
        // add the source vertex into the queue
        Vertex<Type> root = allVerties.get(source);
        root.isVisited = true;
        myQueue.offer(root);
        // while the queue is not empty
        while (!myQueue.isEmpty()) {
            // poll a vertex
            Vertex<Type> cur = myQueue.poll();
            // check each neighbor of that vertex
            for (Vertex<Type> neighbor : cur.neighbors) {
                if (!neighbor.isVisited) {
                    neighbor.isVisited = true;
                    // set the "from" variable in neighbor to current vertex
                    neighbor.from = cur;
                    myQueue.offer(neighbor);
                    // if found destination, then add that neighbor into the list
                    if (neighbor.data.equals(destination)) {
                        returnList.add(neighbor.data);
                        myQueue.clear();
                        break;
                    }
                }
            }
        }
        // if the list is not empty, means we found the destination
        if (!returnList.isEmpty()) {
            // get all the "from" information and find the "from" vertex insert it to the List
            while (allVerties.get(returnList.get(0)).from != null) {
                returnList.add(0, allVerties.get(returnList.get(0)).from.data);
            }
        }
        return returnList;
    }

    /**
     * A topological sort algorithm, gives a list of data that sorted according to
     * topological idea from a graph.
     *
     * @return a list of sorted data.
     */
    public List<Type> topoSort() {
//        if (isCyclic()) throw new IllegalArgumentException();
        List<Type> returnList = new ArrayList<>();
        Queue<Vertex<Type>> myQueue = new LinkedList<>();
//        reset();
        for (Vertex<Type> vertex : allVerties.values()) {
            if (vertex.inDegree == 0) myQueue.add(vertex);
        }
        while (!myQueue.isEmpty()) {
            Vertex<Type> cur = myQueue.poll();
            returnList.add(cur.data);
            for (Vertex<Type> neighbor : cur.neighbors) {
                neighbor.inDegree--;
                if (neighbor.inDegree == 0) myQueue.add(neighbor);
            }
        }
        return returnList;
    }
}
