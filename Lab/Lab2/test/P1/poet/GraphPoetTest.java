/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    //
    // For corpus:
    //      cropus nothing
    //      some normal examples
    //      several lines/ just one line
    //      several continuous same words
    //
    // For input: 
    //      nothing/ some normal words
    // 
    // For output:
    //      have >= 1 bridge words
    //      have 0 bridge word
    //      have different bridge words between the same 2 words
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // my tests
    // deal with the example
    @Test
    public void testExample() throws IOException{
        File file = new File("test/P1/poet/mugar-omni-theater.txt");
        final GraphPoet nimoy = new GraphPoet(file);
        final String input = "Test the system.";
        final String output = nimoy.poem(input);
        assertEquals("Test of the system.", output);
    }
    
    // corpus address: test/P1/poet/seven-words.txt
    // corpus: You are free to add additional methods.
    // input: I want to add methods.
    @Test
    public void testMyExample() throws IOException{
        File file = new File("test/P1/poet/seven-words.txt");
        final GraphPoet graphPoet = new GraphPoet(file);
        final String input = "I want to add methods.";
        final String output = graphPoet.poem(input);
        assertEquals("I want to add additional methods.", output);
    }
    
    // corpus address: test/P1/poet/inputNothing.txt
    // corpus(I dont care):
    // input nothing
    @Test
    public void testEmptyInput() throws IOException{
        File file = new File("test/P1/poet/inputNothing.txt");
        final GraphPoet graphPoet = new GraphPoet(file);
        final String input = "";
        final String output = graphPoet.poem(input);
        assertEquals(0, output.length());
    }
    
    // corpus address: test/P1/poet/corpusNothing.txt
    // corpus nothing
    // input: I want to add methods.
    @Test
    public void testEmptyCorpus() throws IOException{
        File file = new File("test/P1/poet/corpusNothing.txt");
        final GraphPoet graphPoet = new GraphPoet(file);
        final String input = "I want to add methods.";
        final String output = graphPoet.poem(input);
        assertEquals("I want to add methods.", output);
    }
    
    // corpus address: test/P1/poet/severalLines.txt
    // corpus:
    // Hugo is one of the most popular open-source static site generators. 
    // With its amazing speed and flexibility, Hugo makes building websites fun again.
    // input: The popular hugo can makes websites with amazing speed.
    @Test
    public void testOneLineTreeOneWord() throws IOException{
        File file = new File("test/P1/poet/severalLines.txt");
        final GraphPoet graphPoet = new GraphPoet(file);
        final String input = "The popular hugo can makes websites with amazing speed.";
        final String output = graphPoet.poem(input);
        assertEquals("The most popular hugo can makes building websites with its amazing speed.", output);
    }
    
    // corpus address: test/P1/poet/corpusToSelf.txt
    // corpus: Hello, hello, hello, Hello, my name is Fu Haodong.
    // input: I want to add methods.
    @Test
    public void testOneLineTreeSeveralWords() throws IOException{
        File file = new File("test/P1/poet/corpusToSelf.txt");
        final GraphPoet graphPoet = new GraphPoet(file);
        final String input = "Hello, hello, name is Haodong.";
        final String output = graphPoet.poem(input);
        assertEquals("Hello, hello, hello, my name is fu Haodong.", output);
    }
}
