package com.eternalcode.lobbyheads.position;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class PositionAdapterTest {

    private static final String WORLD_NAME = "Martin";

    @Test
    @DisplayName("Test converting location to position")
    void testConvertLocationToPosition() {
        World world = mock(World.class);
        when(world.getName()).thenReturn(WORLD_NAME);

        Location location = new Location(world, 1, 2, 3);

        Position position = PositionAdapter.convert(location);

        assertEquals(1, position.x());
        assertEquals(2, position.y());
        assertEquals(3, position.z());
        assertEquals(WORLD_NAME, position.world());
    }

    @Test
    @DisplayName("Test converting position to location")
    void testConvertPositionToLocation() {
        try (MockedStatic<Bukkit> mocked = mockStatic(Bukkit.class)) {
            World world = mock(World.class);
            mocked.when(() -> Bukkit.getWorld(WORLD_NAME)).thenReturn(world);

            Position position = new Position(1, 2, 3, WORLD_NAME);
            Location location = PositionAdapter.convert(position);

            assertEquals(1, location.getX());
            assertEquals(2, location.getY());
            assertEquals(3, location.getZ());
            assertEquals(world, location.getWorld());
        }
    }

    @Test
    @DisplayName("Test converting position to location with invalid world")
    void testNullPointerExceptionWhenWorldIsNull() {
        Location location = new Location(null, 1, 2, 3);

        assertThrows(IllegalStateException.class, () -> PositionAdapter.convert(location));
    }
}
