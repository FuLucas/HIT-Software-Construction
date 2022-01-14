/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import P1.graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with //// maximum-weight weight //// among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    // AF(graph) = A graph-based poetry generator.

    // Representation invariant:
    // words must be lowercase
    // no edge's weight <= 0.

    // Safety from rep exposure:
    // fields must be private and final
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        // throw new RuntimeException("not implemented");
        FileReader fr = new FileReader(corpus);
        BufferedReader read = new BufferedReader(fr);
        String line = null;
        String[] corpusWords;
        while ((line = read.readLine()) != null) {
            line = line.toLowerCase();
            corpusWords = line.split(" ");
            if (corpusWords.length > 0)
            graph.add(corpusWords[0]);
            for (int i = 1; i < corpusWords.length; i++) {
                graph.add(corpusWords[i]);
                Map<String, Integer> prevTargets = new HashMap<>();
                prevTargets = graph.targets(corpusWords[i-1]);
                int prevWeight = 0;
                if (prevTargets.containsKey(corpusWords[i-1]))
                    prevWeight = prevTargets.get(corpusWords[i-1]);
                graph.set(corpusWords[i-1], corpusWords[i], prevWeight + 1);
            }
        }
        read.close();
        checkRep();
    }
    
    // checkRep
    private void checkRep() {
        Set<String> vertices = graph.vertices();
        assert graph != null;
        for (String vertex : vertices) {
            assert vertex != null;
            assert vertex.equals(vertex.toLowerCase());
            Map<String, Integer> sources = graph.sources(vertex);
            for (String s:sources.keySet())
                assert sources.get(s).intValue() > 0;
        }
    }
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        StringBuffer ret = new StringBuffer();
        String[] inputWords = input.split(" ");
        String w1, w2;
        for (int i = 1; i < inputWords.length; i++) {
            w1 = inputWords[i-1].toLowerCase();
            w2 = inputWords[i].toLowerCase();
            ret.append(inputWords[i-1] + " ");
            Map<String, Integer> w1Targets = graph.targets(w1);
            Map<String, Integer> w2Sources = graph.sources(w2);
            int maxWeight = 0;
            String bridgeWord = "";
            for (String s : w1Targets.keySet()) {
                if (w2Sources.containsKey(s)) {
                    int tempWeight = w2Sources.get(s) + w1Targets.get(s);
                    if (tempWeight > maxWeight) {
                        maxWeight = tempWeight;
                        bridgeWord = s;
                    }
                }
            }
            if (maxWeight > 0)
                ret.append(bridgeWord + " ");
        }
        ret.append(inputWords[inputWords.length - 1]);
        return ret.toString();
    }
    
    // toString()
    @Override public String toString() {
        return graph.toString();
    }   
}
