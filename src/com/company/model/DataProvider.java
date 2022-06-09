package com.company.model;

import java.util.ArrayList;

import static com.company.model.Constants.ALIVE_CELL_VALUE;
import static com.company.model.Constants.COUNT;

/**
 * Provides data.
 */
public class DataProvider {

    /**
     * Returns cells with seeded cells which are placed at coordinates
     * @param coordinates coordinates to seed cells at
     * @return seeded cells
     */
    public int[][] getSeededCells(ArrayList<SeededCellsCoordinates> coordinates) {
        int [][] cells = new int [COUNT][COUNT];
        for (SeededCellsCoordinates location : coordinates) {
            cells[location.getX()][location.getY()] = ALIVE_CELL_VALUE;
        }
        return cells;
    }

    /**
     * Coordinates for seeded cells.
     * @return coordinates for seeded cells.
     */
    public static ArrayList<SeededCellsCoordinates> getSeededCellsCoordinates() {
        final ArrayList<SeededCellsCoordinates> coordinates = new ArrayList<>();
        coordinates.add(new SeededCellsCoordinates(13, 11));
        coordinates.add(new SeededCellsCoordinates(13, 12));
        coordinates.add(new SeededCellsCoordinates(13, 13));
        coordinates.add(new SeededCellsCoordinates(12, 13));
        coordinates.add(new SeededCellsCoordinates(11, 12));
        return coordinates;
    }
}
