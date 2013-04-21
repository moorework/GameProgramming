
package edu.moravian.WorldMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Myles
 */
public class NavigationalGraph {
    private HashMap<PathCell, ArrayList<Node>> vertices;

    public NavigationalGraph()
    {
        vertices = new HashMap<PathCell, ArrayList<Node>>();
    }

    public double getEdge(PathCell p, PathCell p1)
    {
        ArrayList<Node> neighbors = vertices.get(p);

        for (Node n : neighbors)
        {
            if (n.vertex.equals(p1))
            {
                return n.edgeWeight;
            }
        }

        return 0.0;
    }

    public void addEdge(PathCell p, PathCell p1)
    {
        addEdge(p, p1, 1.0);
    }

    public void addEdge(PathCell p, PathCell p1, double edgeWeight)
    {
        if (vertices.containsKey(p) == false)
        {
            vertices.put(p, new ArrayList<Node>());
        }

        if (vertices.containsKey(p1) == false)
        {
            vertices.put(p1, new ArrayList<Node>());
        }

        vertices.get(p).add(new Node(p1, edgeWeight));
        vertices.get(p1).add(new Node(p, edgeWeight));
    }

    public void addVertex(PathCell p)
    {
        vertices.put(p, new ArrayList<Node>());
    }

    public boolean containsEdge(PathCell p, PathCell p1)
    {
        ArrayList<Node> pNeighbors = vertices.get(p);

        for (Node n : pNeighbors)
        {
            if (n.vertex.equals(p1))
            {
                return true;
            }
        }

        return false;
    }

    public boolean containsVertex(PathCell p)
    {
        return vertices.containsKey(p);
    }

    public Collection<PathCell> edgesOf(PathCell p)
    {
        ArrayList<Node> neighbors = vertices.get(p);
        ArrayList<PathCell> ret = new ArrayList<PathCell>();

        for (Node n : neighbors)
        {
            ret.add(n.vertex);
        }

        return ret;
    }

    public void removeEdge(PathCell p, PathCell p1)
    {
        ArrayList<Node> neighbors;
        neighbors = vertices.get(p);
        Node n;
        boolean found = false;
        int counter = 0;
        while (found == false && counter < neighbors.size())
        {
            n = neighbors.get(counter);
            if (n.vertex.equals(p1))
            {
                found = true;
            } else
            {
                counter++;
            }
        }

        if (found == true)
        {
            neighbors.remove(counter);
        }

        neighbors = vertices.get(p1);
        n = null;
        found = false;
        counter = 0;
        while (found == false && counter < neighbors.size())
        {
            n = neighbors.get(counter);

            if (n.vertex.equals(p))
            {
                found = true;
            } else
            {
                counter++;
            }
        }
        
        if (found == true)
        {
            neighbors.remove(counter);
        }
    }

    public void removeVertex(PathCell p)
    {
        vertices.remove(p);
    }

    public Collection<PathCell> vertexSet()
    {
        return vertices.keySet();
    }

    private class Node {
        private PathCell vertex;
        private double edgeWeight;

        public Node(PathCell pCell, double edgeWeight)
        {
            vertex = pCell;
            this.edgeWeight = edgeWeight;
        }

        public PathCell getVertex()
        {
            return vertex;
        }

        public double getEdgeWeight()
        {
            return edgeWeight;
        }
    }
}
