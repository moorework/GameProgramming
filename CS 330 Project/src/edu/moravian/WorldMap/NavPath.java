package edu.moravian.WorldMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.jgrapht.EdgeFactory;
import org.jgrapht.WeightedGraph;

/**
 *
 * @author myles
 */
public class NavPath<V, E> implements WeightedGraph {
    private HashMap<V, ArrayList<Node>> adjacencyList;
    
    private class Node {
        protected V vertex;
        protected E edge;
        
        protected Node(V v, E e) {
            vertex = v;
            edge = e;
        }
    }
    
    public NavPath()
    {
        adjacencyList = new HashMap<V, ArrayList<Node>>();
    }

    @Override
    public void setEdgeWeight(Object e, double d)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set getAllEdges(Object v, Object v1)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getEdge(Object v, Object v1)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EdgeFactory getEdgeFactory()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object addEdge(Object v, Object v1)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addEdge(Object v, Object v1, Object e)
    {
        V vertexFrom = (V) v;
        V vertexTo = (V) v1;
        E edge = (E) e;
        
        if (adjacencyList.containsKey(vertexFrom) == false) {
            
            this.addVertex(vertexFrom);
            this.addVertex(vertexTo);
        }
        
        Node n = new Node(vertexTo, edge);
            
        adjacencyList.get(vertexFrom).add(n);
        
        return true;
    }

    @Override
    public boolean addVertex(Object v)
    {
        V newVertex = (V) v;
        
        adjacencyList.put(newVertex, new ArrayList<Node>());
        
        return true;
    }

    @Override
    public boolean containsEdge(Object v, Object v1)
    {
        V vertexFrom = (V) v;
        V vertexTo = (V) v;
        
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

    @Override
    public boolean containsEdge(Object e)
    {
        E edge = (E) e;
        
        for (ArrayList<Node> nodes : adjacencyList.values()) {
            for (Node n : nodes) {
                if (n.edge == edge) {
                    return true;
                }
            }
        }
        
        return false;
    }

    @Override
    public boolean containsVertex(Object v)
    {
        V vertex = (V) v;
        
        return adjacencyList.containsKey(vertex);
    }

    @Override
    public Set edgeSet()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set edgesOf(Object v)
    {
        V vertex = (V) v;
        
        HashSet<V> ret = new HashSet<V>();
        
        for (Node n : adjacencyList.get(vertex)) {
            ret.add(n.vertex);
        }
        
        return ret;
    }

    @Override
    public boolean removeAllEdges(Collection clctn)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set removeAllEdges(Object v, Object v1)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeAllVertices(Collection clctn)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object removeEdge(Object v, Object v1)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeEdge(Object e)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeVertex(Object v)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set vertexSet()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getEdgeSource(Object e)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getEdgeTarget(Object e)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getEdgeWeight(Object e)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
