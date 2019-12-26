package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void getFavoriteNeighboursWithSuccess() {
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
        assertTrue(favoriteNeighbours.isEmpty());
        favoriteNeighbours.add(service.getNeighbours().get(0));
        assertEquals(1,favoriteNeighbours.size());
    }

    @Test
    public void addFavoriteNeighbourWithSuccess() {
        Neighbour neighbourToAddFavorite = service.getNeighbours().get(0);
        service.addFavoriteUser(neighbourToAddFavorite);
        assertEquals(neighbourToAddFavorite,service.getFavoriteNeighbours().get(0));
    }

    @Test
    public void deleteFavoriteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.addFavoriteUser(neighbourToDelete);
        assertTrue(service.getFavoriteNeighbours().contains(neighbourToDelete));
        service.deleteFavoriteNeighbours(neighbourToDelete);
        assertFalse(service.getFavoriteNeighbours().contains(neighbourToDelete));

    }
}