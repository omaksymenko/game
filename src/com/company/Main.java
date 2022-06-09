package com.company;

import com.company.model.DataProvider;
import com.company.model.SeededCellsCoordinates;

import java.util.ArrayList;
import java.util.Arrays;

import static com.company.model.Constants.COUNT;
import static com.company.model.Constants.ALIVE_CELL_VALUE;
import static com.company.model.Constants.DEAD_CELL_VALUE;
import static com.company.model.Constants.MAX_ALIVE_NEIGHBORS_COUNT;
import static com.company.model.Constants.MIN_ALIVE_NEIGHBORS_COUNT;


public class Main {
    public static void main(String[] args) {
        DataProvider dataProvider = new DataProvider();
        ArrayList<SeededCellsCoordinates> seeds = dataProvider.getSeededCellsCoordinates();
        System.out.println("The initial cells");
        int [][] cells = dataProvider.getSeededCells(seeds);
        showCells(cells);
        getNextCellsGenerations(cells);
    }

    private static void showCells(int[][] cells) {
        for (int[] row : cells) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Apply transformation cells due to the game rules and show the ticks and next generations
     * till the result cells equal the transformed ones.
     * @param cells cells to apply the game rules.
     */
    private static void getNextCellsGenerations(int[][] cells) {
        int generationNumber = 1;
        int tickNumber = 1;
        int [][] afterTickCells;
        for (int i = 0; i < cells.length; i++) {
            System.out.printf("Generation # %d%n%n", generationNumber);
            for (int j = 0; j < cells[i].length; j++) {
                System.out.printf("Tick # %d%n", tickNumber++);
                afterTickCells = getAfterTickCells(i, j, cells);
                if (!Arrays.deepEquals(cells, afterTickCells)) {
                    showCells(afterTickCells);
                } else {
                    System.out.printf("The initial cells are equal to the cells after the tick # %d in generation # %d.%n" +
                                    "The game's over. Please try another seeds pattern.", --tickNumber, generationNumber);
                    return;
                }
                generationNumber++;
            }
        }
    }

    private static int [][] getCellsCopy(int [][] cells) {
        final int startPosition = 0;
        int [][] cellsCopy = new int[COUNT][COUNT];
        for (int i = 0; i <  cellsCopy.length; i++) {
            System.arraycopy(cells[i], startPosition, cellsCopy[i], startPosition, cells[i].length);
        }
        return cellsCopy;
    }

    /**
     * Gets converted cells by the game rules after each tick.
     * @param rowNumber cell row number
     * @param columnNumber cell column number
     * @param grid initial cells
     * @return transformed cells by the game rules after each tick
     */
    private static int [][] getAfterTickCells(int rowNumber, int columnNumber, int[][] grid) {
        int [][] afterTickCells = getCellsCopy(grid);

        ArrayList<Integer> neighbors = getCellNeighbors(rowNumber, columnNumber, grid);
        int neighborsCellsSum = neighbors.stream().reduce(Integer::sum).orElse(0);
        int cell = grid[rowNumber][columnNumber];

        if (isCellAliveAliveAndGetsDead(neighborsCellsSum, cell)) {
            afterTickCells[rowNumber][columnNumber] = DEAD_CELL_VALUE;
        } else if (isCellDeadAndGetsAlive(neighborsCellsSum, cell)) {
            afterTickCells[rowNumber][columnNumber] = ALIVE_CELL_VALUE;
        }
        return afterTickCells;
    }

    /**
     * Rule: less than 2 alive cells neighbors or more than 3 ones - the alive cell dies.
     * Returns if the aforementioned condition is met
     * @param cellsNeighborsSum sum of neighbors cells
     * @param cell cell to be alive or dead in the next tick
     * @return if the rule condition is met
     */
    private static boolean isCellDeadAndGetsAlive(int cellsNeighborsSum, int cell) {
        return cellsNeighborsSum == MAX_ALIVE_NEIGHBORS_COUNT && cell == DEAD_CELL_VALUE;
    }

    /**
     * Rule: 3 alive neighbors if the cell is dead it's become resurrected.
     * Returns if the aforementioned condition is met
     * @param cellsNeighborsSum sum of neighbors cells
     * @param cell cell to be alive or dead in the next tick
     * @return if the rule condition is met
     */
    private static boolean isCellAliveAliveAndGetsDead(int cellsNeighborsSum, int cell) {
        return cell == ALIVE_CELL_VALUE &&
                cellsNeighborsSum < MIN_ALIVE_NEIGHBORS_COUNT || cellsNeighborsSum > MAX_ALIVE_NEIGHBORS_COUNT;
    }

    /**
     * Get all neighbors cells for the cell.
     * @param elementRowNumber cell row number
     * @param elementColumnNumber cell column number
     * @param matrix cells matrix
     * @return all neighbors cells for the cell
     */
    private static ArrayList<Integer> getCellNeighbors(int elementRowNumber, int elementColumnNumber, int[][] matrix) {
        ArrayList<Integer> neighbors = new ArrayList<>();
        for (int i = 0; isNeighborIndex(elementRowNumber, i); i++) {
            for (int j = 0; isNeighborIndex(elementColumnNumber, j); j++) {
                if (i == elementRowNumber && j == elementColumnNumber) { // not add the element itself
                    continue;
                }
                neighbors.add(matrix[i][j]);
            }
        }
        return neighbors;
    }

    /**
     * Returns if the cell index is in the cells neighbors indexes range
     * @param number number of cell row/column
     * @param index the cell index
     * @return if the cell index is in the cells neighbors indexes range
     */
    private static boolean isNeighborIndex(int number, int index) {
        final int level = 1; // level to search neighbors cells for around the element in the column/row # number
        return index >= number - level && index <= number + level;
    }
}
