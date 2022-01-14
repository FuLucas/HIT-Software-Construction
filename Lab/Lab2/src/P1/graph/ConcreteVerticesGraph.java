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
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    // AF(vertices) = the vertices of the graph

    // Representation invariant:
    // Each vertex's name(key) MUST be unique, and cant be null

    // Safety from rep exposure:
    // List is mutable, return copied data, and dont use dangerously
    // All fields MUST be private final
    
    // constructor
    public ConcreteVerticesGraph() {}
     
    // checkRep
    private void checkRep() {
        Set<L> tests = new HashSet<>();
        for (Vertex<L> v:vertices)
            tests.add(v.name());
        assert vertices.size()==tests.size();
        for (Vertex<L> key:vertices) {
            for (Vertex<L> v: key.sources().keySet()) {
                assert v.targets().get(key).equals(key.sources().get(v));
            }
            for (Vertex<L> v: key.targets().keySet()) {
                assert v.sources().get(key).equals(key.targets().get(v));
            }
        }
    }
    
    @Override public boolean add(L vertex) {
        for (Vertex<L> v : vertices) {
            // test if this graph included this vertex, and if so return false
            if (v.name().equals(vertex))
                return false;
        }
        Vertex<L> newVertex = new Vertex<L>(vertex);
        vertices.add(newVertex);
        checkRep();
        return true;
    }

    @Override public int set(L source, L target, int weight) {
        if (weight < 0) throw new RuntimeException("Negetive weight");
        // check if the vertices existed
        Vertex<L> setSource = null, setTarget = null;
        for (Vertex<L> v : vertices) {
            if (v.name().equals(source))
                setSource = v;
            if (v.name().equals(target))
                setTarget = v;
        }
        // not already exist, add to the vertices list
        if (setSource == null) {
            setSource = new Vertex<L>(source);
            vertices.add(setSource);
        }
        if (setTarget == null) {
            setTarget = new Vertex<L>(target);
            vertices.add(setTarget);
        }
        // if the edge label from source contains target
        // that means the edge already exist, return previous weight
        int prevWeight = 0;
        if (setSource.sources().containsKey(setTarget)) {
            prevWeight = setSource.removeSourceEdge(setTarget);
            prevWeight = setTarget.removeTargetEdge(setSource);
        }
        if (weight > 0) {
            setSource.addSourceEdges(setTarget, weight);
            setTarget.addTargetEdges(setSource, weight);
        }
        checkRep();
        return prevWeight;
    }

    @Override public boolean remove(L vertex) {
        Vertex<L> remVertex = null;
        for (Vertex<L> v : vertices) {
            if (v.name().equals(vertex)) remVertex = v;
        }
        // this graph didnt include a vertex with the given label
        if (remVertex == null) return false;
        vertices.remove(remVertex);
        // clear some useless edges
        for (Vertex<L> v : vertices) {
            if (v.sources().containsKey(remVertex))
                v.removeSourceEdge(remVertex);
            if (v.targets().containsKey(remVertex))
                v.removeTargetEdge(remVertex);
        }
        checkRep();
        return true;
    }

    @Override public Set<L> vertices() {
        Set<L> ret = new HashSet<L>();
        for (Vertex<L> v: vertices)
            ret.add(v.name());
        checkRep();
        return ret;
    }

    @Override public Map<L, Integer> sources(L target) {
        Map<L, Integer> ret = new HashMap<>();
        Vertex<L> vertexTarget = null;
        for (Vertex<L> v: vertices)
            if (v.name().equals(target))
                vertexTarget = v;
        if (vertexTarget == null) return ret;
        Map<Vertex<L>, Integer> temp = new HashMap<>();
        temp = vertexTarget.targets();
        for (Vertex<L> v:temp.keySet()) {
            ret.put(v.name(), temp.get(v));
        }
        return ret;
    }

    @Override public Map<L, Integer> targets(L source) {
        Map<L, Integer> ret = new HashMap<>();
        Vertex<L> vertexSource = null;
        for (Vertex<L> v: vertices)
            if (v.name().equals(source))
                vertexSource = v;
        if (vertexSource == null) return ret;
        Map<Vertex<L>, Integer> temp = new HashMap<>();
        temp = vertexSource.sources();
        for (Vertex<L> v:temp.keySet()) {
            ret.put(v.name(), temp.get(v));
        }
        return ret;
    }
    
    // toString()
    @Override public String toString() {
        StringBuffer ret = new StringBuffer();
        ret.append("Vertices:");
        for (Vertex<L> v:vertices)
            ret.append(" \\" + v.name() + "\\");
        ret.append("\n");
        for (Vertex<L> v: vertices) {
            Map<Vertex<L>, Integer> temp = new HashMap<>();
            temp = v.sources();
            for (Vertex<L> source:temp.keySet()) {
                ret.append("Edge <" + v.name() + ", " + source.name() + 
                ">  Weight:" + temp.get(source) + "\n");
            }
        }
        return ret.toString();
    }
}

/**
 * specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {
    
    // fields
    private final L name;
    // this vertex as source
    private final Map<Vertex<L>, Integer> sourceEdges = new HashMap<>();
    // this vertex as target
    private final Map<Vertex<L>, Integer> targetEdges = new HashMap<>();
    
    // Abstraction function:
    // AF(sourceEdges) = vertecies to the target and thier weights
    //                   the edges to the vertex
    // AF(targetEdges) = vertecies from the source and thier weight
    //                   the edges from the vertex

    // Representation invariant:
    // the weight have to be positive ( > 0)
    // if vertex A has a edge to vertex B, then B must 
    // have an edge from A with the SAME weight

    // Safety from rep exposure:
    // Check the rep invariant is true, all fields are private
    // return copied Maps
    
    // constructor
    public Vertex (L vertex) {
        this.name = vertex;
        checkRep();
    }
    
    // checkRep
    private void checkRep() {
        for (Integer sourceVal:sourceEdges.values())
            assert sourceVal > 0;
        for (Integer targetVal:targetEdges.values())
            assert targetVal > 0;
    }
    
    // methods
    public L name() {
        return name;
    }

    public Map<Vertex<L>, Integer> sources() {
        Map<Vertex<L>, Integer> sources = new HashMap<>();
        sources.putAll(sourceEdges);
        return sources;
    }

    public Map<Vertex<L>, Integer> targets() {
        Map<Vertex<L>, Integer> targets = new HashMap<>();
        targets.putAll(targetEdges);
        return targets;
    }

    // HashMap: for existed key, change the value, return old value
    // that means there are no repeated edges

    public void addSourceEdges(Vertex<L> target, int weight) {
        if (weight <= 0) return;
        sourceEdges.put(target, weight);
        checkRep();
    }

    public void addTargetEdges(Vertex<L> source, int weight) {
        if (weight <= 0) return;
        targetEdges.put(source, weight);
        checkRep();
    }


    public int removeSourceEdge(Vertex<L> target) {
        int prevWeight = sourceEdges.get(target);
        sourceEdges.remove(target);
        checkRep();
        return prevWeight;
    }

    public int removeTargetEdge(Vertex<L> source) {
        int prevWeight = targetEdges.get(source);
        targetEdges.remove(source);
        checkRep();
        return prevWeight;
    }
    
    // toString()
    @Override public String toString() {
        StringBuffer ret = new StringBuffer();
        // name of the vertex
        ret.append("Vertex: \\" + name + "\\\n");
        // Edges whose sources are the vertex
        ret.append("Edges whose source is " + name + ".\n");
        for (Vertex<L> key:sourceEdges.keySet()) {
            ret.append("Edge <" + name + ", " + key.name() + ">  Weight:" + 
                        sourceEdges.get(key) + "\n");	
        }
        // Edges whose targets are the vertex
        ret.append("Edges whose target is " + name + ".\n");
        for (Vertex<L> key:targetEdges.keySet()) {
            ret.append("Edge <" + key.name() + ", " + name + ">  Weight:" + 
                        targetEdges.get(key) + "\n");
        }
        checkRep();
        return ret.toString();
    }
}
