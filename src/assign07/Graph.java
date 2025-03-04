package assign07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph<Type> {
    private HashMap<Type, Vertex<Type>> allVerties;
    private HashMap<Type, Vertex<Type>> checkCyclic;

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

        public void addNeighbor(Vertex<Type> newNeighbor) {
            neighbors.add(newNeighbor);
        }

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

    public Graph() {
        allVerties = new HashMap<>();
        checkCyclic = new HashMap<>();
    }

    public Graph(String filepath) {
        allVerties = new HashMap<>();
        checkCyclic = new HashMap<>();
        readGraph(filepath);
    }

    public void addVertex(Type data) {
        allVerties.put(data, new Vertex<>(data));
    }

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

    public boolean contains(Type target) {
        for (Type data : allVerties.keySet()) {
            if (target.equals(data)) return true;
        }
        return false;
    }

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

    private void reset() {
        for (Type data : allVerties.keySet()) {
            Vertex<Type> cur = allVerties.get(data);
            cur.isVisited = false;
            cur.from = null;
            cur.inDegree = cur.parents.size();
        }
    }

    public boolean depthFirstSearch(Type source, Type destination) {
        if (!allVerties.containsKey(destination) || !allVerties.containsKey(source)) return false;
        if (source.equals(destination)) return true;
        reset();
        return dfsRecur(source, destination);
    }

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

    public List<Type> topoSort() {
        if (isCyclic()) throw new IllegalArgumentException();
        List<Type> returnList = new ArrayList<>();
        Queue<Vertex<Type>> myQueue = new LinkedList<>();
        reset();
        for (Type vertexName : allVerties.keySet()) {
            Vertex<Type> vertex = allVerties.get(vertexName);
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
