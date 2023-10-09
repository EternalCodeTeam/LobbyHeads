package com.eternalcode.lobbyheads.position;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    private Position position;

    @BeforeEach
    void setUp() {
        this.position = new Position(10.5, 20.5, 30.5, 10.2F, 10.1F, "TestWorld");
    }

    @Test
    void testGetX() {
        assertEquals(10.5, position.getX());
    }

    @Test
    void testGetY() {
        assertEquals(20.5, position.getY());
    }

    @Test
    void testGetZ() {
        assertEquals(30.5, position.getZ());
    }

    @Test
    void testGetYaw() {
        assertEquals(10.2F, position.getYaw());
    }

    @Test
    void testGetPitch() {
        assertEquals(10.1F, position.getPitch());
    }

    @Test
    void testGetWorld(){
        assertEquals("TestWorld", position.getWorld());
    }

    @Test
    void testParse() {
        Position parsed = Position.parse("Position{x=10.5, y=20.5, z=30.5, yaw=10.2, pitch=10.1, world='TestWorld'}");
        assertEquals(position, parsed);
    }

    @Test
    void testToString() {
        assertEquals("Position{x=10.5, y=20.5, z=30.5, yaw=10.2, pitch=10.1, world='TestWorld'}", position.toString());
    }
}