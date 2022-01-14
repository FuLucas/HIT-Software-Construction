/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    // HashSet cant add the same vertex
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    // AF(vertices) = the vertices set of this graph.
    // AF(edges) = the edges list of this graph

    // Representation invariant:
    // one or no edge between the same source and target
    // the source and target of any edge in the edge list must be in the vertices set 
    

    // Safety from rep exposure:
    // Check the rep invariant is true, but list and set are mutable
    // All fields MUST be private
    // So make defensive copies instead of just return mutable data 
    
    
    // constructor
    public ConcreteEdgesGraph() {}
    
    // checkRep
    private void checkRep() {
        for (Edge<L> edge:edges) {
            assert vertices.contains(edge.source());
            assert vertices.contains(edge.target());
            assert edge.weight() > 0;
        }
    }
    
    @Override public boolean add(L vertex) {
        if (vertices.contains(vertex)) return false;
        vertices.add(vertex);
        return true;
    }

    @Override public int set(L source, L target, int weight) {
        if (weight < 0)
            throw new RuntimeException("Negetive weight");
        if (!vertices.contains(source))
            this.add(source);
        if (!vertices.contains(target))
            this.add(target);
        // source == target, continue, have no influence
        Edge<L> newEdge = new Edge<L>(source, target, weight);
        // check if there were the same edge
        for (Edge<L> findSameEdge:edges) {
            if (findSameEdge.haveSuchEdge(source, target)) {
                int prevWeight = findSameEdge.weight();
                edges.remove(findSameEdge);
                // if new edge's weight is nonzero, update
                // else, just remove
                if (weight > 0) {
                    edges.add(newEdge);
                }
                checkRep();
                return prevWeight;
            }
        }
        // edge doesn't exist, but new edge's weight is 0, do nothing
        if (weight == 0) {
            checkRep();
            return 0;
        }
        // edge doesn't exist and its weight is positive, add
        edges.add(newEdge);
        checkRep();
        return 0;
    }

    @Override public boolean remove(L vertex) {
        if (!vertices.contains(vertex))
            return false;
        // remove edges to or from the vertex
        edges.removeIf(edge->edge.source().equals(vertex)
                        ||edge.target().equals(vertex));
        vertices.remove(vertex);
        checkRep();
        return true;
    }
    
    @Override public Set<L> vertices() {
        // copy set
        return new HashSet<>(vertices);
    }
    
    @Override public Map<L, Integer> sources(L target) {
        // L represent source, Integer represent weight
        Map<L, Integer> sources = new HashMap<>();
        for (Edge<L> edge:edges)
            // weight is nonzero
            if (edge.target().equals(target) && edge.weight() != 0)
                    sources.put(edge.source(), edge.weight());
        checkRep();
        return sources;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        // L represent target, Integer represent weight
        Map<L, Integer> targets = new HashMap<>();
        for (Edge<L> edge:edges)
            // weight is nonzero
            if (edge.source().equals(source) && edge.weight() != 0)
                    targets.put(edge.target(), edge.weight());
        checkRep();
        return targets;
    }
    
    // toString()
    @Override public String toString() {
        StringBuffer ret = new StringBuffer("Vertices:");
        for (L vertex : vertices) {
            ret.append(" \\" + vertex.toString() + "\\");
        }
        ret.append("\n");   
        for (Edge<L> edge : edges) {
            ret.append(edge.toString() + "\n");
        }
        return ret.toString();
    }
    
}

/**
 * specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {
    
    // fields
    private final L source;
    private final L target;
    private final int weight;
    
    // Abstraction function:
    // AF(source) = the source vertex of the edge
    // AF(target) = the target vertex of the edge
    // AF(weight) = the weight of the edge

    // Representation invariant:
    // vertex can point to itself
    // the weight have to be positive ( >= 0)
    // if weight is 0, it will be removed later.

    // Safety from rep exposure:
    // Check the rep invariant is true. That means setting 
    // source, target and weight unchangeable / private / final
    // add return value MUST be immutable
    
    // constructor
    public Edge(L source, L target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }
    
    // checkRep
    private void checkRep() {
        assert weight >= 0;
    }
    
    // methods
    public L source() {
        checkRep();
        return source;
    }

    public L target() {
        checkRep();
        return target;
    }

    public int weight() {
        checkRep();
        return weight;
    }

    public boolean haveSuchEdge(L source, L target) {
        checkRep();
        return this.source.equals(source) && this.target.equals(target);
    }
    
    // toString()
    @Override
    public String toString() {
        return "Edge <" + source.toString() + ", " 
                + target.toString() + ">  Weight:" + weight;
    }
}
