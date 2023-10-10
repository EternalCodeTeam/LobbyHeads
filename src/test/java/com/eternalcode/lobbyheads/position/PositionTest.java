package com.eternalcode.lobbyheads.position;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    public static final String TEST_WORLD = "TestWorld";
    private Position position;

    @BeforeEach
    void setUp() {
        this.position = new Position(10.5, 20.5, 30.5, TEST_WORLD);
    }

    @Test
    void testGetX() {
        assertEquals(10.5, position.x());
    }

    @Test
    void testGetY() {
        assertEquals(20.5, position.y());
    }

    @Test
    void testGetZ() {
        assertEquals(30.5, position.z());
    }

    @Test
    void testGetWorld(){
        assertEquals(TEST_WORLD, position.world());
    }

    @Test
    void testParse() {
        Position parsed = Position.parse("Position{x=10.5, y=20.5, z=30.5, world='TestWorld'}");
        assertEquals(position, parsed);
    }

    @Test
    void testToString() {
        assertEquals("Position{x=10.5, y=20.5, z=30.5, world='TestWorld'}", position.toString());
    }
}
