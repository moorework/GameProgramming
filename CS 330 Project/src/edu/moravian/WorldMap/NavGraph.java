package edu.moravian.WorldMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author myles
 */
public class NavGraph {
    private HashMap<PathCell, ArrayList<Node>> adjacencyList;
    
    private class Node {
        protected PathCell vertex;
        protected Double edge;
        
        protected Node(PathCell v, Double e) {
            vertex = v;
            edge = e;
        }
    }
    
    public NavGraph()
    {
        adjacencyList = new HashMap<PathCell, ArrayList<Node>>();
    }

    
    public void addEdge(PathCell v, PathCell v1)
    {
        Double edge = new Double(1);
        
        addEdge(v, v1, edge);
    }

    public void addEdge(PathCell p1, PathCell p2, Double edge)
    {
        if (adjacencyList.containsKey(p1) == false) {
            this.addVertex(p1);
        }
        
        if (adjacencyList.containsKey(p2) == false) {
            this.addVertex(p2);
        }
        
        Node n = new Node(p2, edge);
        Node nBack = new Node(p1, edge);
            
        adjacencyList.get(p1).add(n);
        adjacencyList.get(p2).add(nBack);
    }
    

    
    public boolean addVertex(PathCell newVertex)
    {
        adjacencyList.put(newVertex, new ArrayList<Node>());
        
        return true;
    }

    
    public boolean containsEdge(PathCell vertexFrom, PathCell vertexTo)
    {
        if (adjacencyList.containsKey(vertexFrom) == false) {
            return false;
        }
        else if (adjacencyList.containsKey(vertexTo) == false) {
            return false;
        }
        else {
            for (Node n :adjacencyList.get(vertexFrom)) {
                if (n.vertex == vertexTo) {
                    return true;
                }
            }
        }
        
        return false;
    }

    
    public boolean containsEdge(Double edge)
    {
        
        for (ArrayList<Node> nodes : adjacencyList.values()) {
            for (Node n : nodes) {
                if (n.edge == edge) {
                    return true;
                }
            }
        }
        
        return false;
    }

    
    public boolean containsVertex(PathCell vertex)
    {   
        return adjacencyList.containsKey(vertex);
    }

    
    public Set<PathCell> edgesOf(PathCell v)
    {
        
        HashSet<PathCell> ret = new HashSet<PathCell>();
        
        for (Node n : adjacencyList.get(v)) {
            ret.add(n.vertex);
        }
        
        return ret;
    }
    
    public Double removeEdge(PathCell vertexFrom, PathCell vertexTo)
    {
        ArrayList<Node> peers = adjacencyList.get(vertexFrom);
        
        Node n;
        int removalIndex = 0;
        boolean remove = false;
        for (int i = 0; i < peers.size(); i++) {
            n = peers.get(i);
            
            if (n.vertex.equals(vertexTo)) {
                removalIndex = i;
                remove = true;
            }
        }
        
        if (remove == true) {
            return peers.remove(removalIndex).edge;
        }
        
        return null;
    }
    
    public Collection<PathCell> vertexSet()
    {
        return adjacencyList.keySet();
    }

    
    public double getEdgeWeight(PathCell vertexFrom, PathCell vertexTo)
    {
        if (adjacencyList.containsKey(vertexFrom) == false) {
            return 0;
        }
        
        ArrayList<Node> peers = adjacencyList.get(vertexFrom);
        
        PathCell currCell;
        for (Node n : peers) {
            currCell = n.vertex;
            
            if (currCell.equals(vertexTo)) {
                return n.edge;
            }
        }
        
        return 0;
    }
    
}
