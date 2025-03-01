package assign07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph<Type> {
    private HashMap<Type, Vertex<Type>> allVerties;

    private static class Vertex<Type> {
        public Type data;
        public ArrayList<Vertex<Type>> neighbors;
        public boolean isVisited;
        public Vertex<Type> from;
        public int inDegree;
        public Vertex(Type data) {
            this.data = data;
            neighbors = new ArrayList<>();
            isVisited = false;
            inDegree = 0;
            from = null;
        }

        public void addNeighbor(Vertex<Type> newNeighbor) {
            neighbors.add(newNeighbor);
            inDegree++;
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
    }

    public void addVertex(Type data) {
        allVerties.put(data, new Vertex<>(data));
    }

    public void addNeighbor(Type target, Type neighbor) {
        if (!allVerties.containsKey(target)) {
            addVertex(target);
        }
        Vertex<Type> cur = allVerties.get(target);
        Vertex<Type> neighborNode;
        if (!allVerties.containsKey(neighbor)) {
            neighborNode = new Vertex<>(neighbor);
        } else {
            neighborNode = allVerties.get(neighbor);
        }
        cur.addNeighbor(neighborNode);
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
                addNeighbor(target, neighbor);
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

    public boolean depthFirstSearch(Type source, Type destination) {
        if (!allVerties.containsKey(destination)) return false;
        boolean isFound = dfsRecur(source, destination);
        // reset all the isVisited variables.
        for (Type key : allVerties.keySet()) {
            allVerties.get(key).isVisited = false;
        }
        return isFound;
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
        Vertex<Type> root = allVerties.get(source);
        myQueue.offer(root);
        while (!myQueue.isEmpty()) {
            Vertex<Type> cur = myQueue.poll();
            for (Vertex<Type> neighbor : cur.neighbors) {
                neighbor.from = cur;
                myQueue.offer(neighbor);
                if (neighbor.data.equals(destination)) {
                    returnList.add(neighbor.data);
                }
            }
            myQueue.poll();
        }
        if (!returnList.isEmpty()) {
            while (allVerties.get(returnList.getLast()).from != null) {
                returnList.add(allVerties.get(returnList.getLast()).from.data);
            }
        }
        return returnList;
    }

    public List<Type> topoSort() {
        return null;
    }
}
