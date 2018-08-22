package coreGraph;

import Software.SoftwareTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class to test ConnectionEdge
 */
class ConnectionEdgeTest extends SoftwareTest {
    @Test
    void testToString() {
        String expected = "(7)Lakshmi is GREAT GREAT GRANDMOTHER of (6)Pranavi RelationLevel: 4\n";
        System.out.println(new ConnectionEdge("7", "GRANDPARENT", "6", 4, family));
        assertEquals(expected, outContent.toString());
    }

    @Test
    void testToString1() {
        String expected = "(6)Pranavi is GREAT GREAT GRANDDAUGHTER of (7)Lakshmi RelationLevel: -4\n";
        System.out.println(new ConnectionEdge("6", "GRANDCHILD", "7", -4, family));
        assertEquals(expected, outContent.toString());
    }
}