package cosc2658.benchmark;

import cosc2658.Grid;
import cosc2658.adt.ArrayList;
import cosc2658.adt.Vec2;
import java.util.Random;

public class Benchmark {

  // Benchmarking constants
  private static final int NUM_ITERATIONS = 1_000;
  private static final int GRID_SIZE = 100;

  public static void main(String[] args) {
      System.out.println("Starting benchmark...");

      // Example benchmark for ArrayList operations
      benchmarkArrayList();

      // Example benchmark for Grid operations
      benchmarkGrid();

      System.out.println("Benchmark completed.");
  }

  private static void benchmarkArrayList() {
    ArrayList<Integer> list = new ArrayList<>();
    long startTime = System.nanoTime();

    // Perform NUM_ITERATIONS operations on the ArrayList
    for (int i = 0; i < NUM_ITERATIONS; i++) {
      list.add(i); // Add elements
    }

    long endTime = System.nanoTime();
    System.out.println("ArrayList benchmark completed in: " +
                       (endTime - startTime) / 1_000_000 + " ms");
  }

  private static void benchmarkGrid() {
    // Grid grid = new Grid(GRID_SIZE, GRID_SIZE);
    Grid grid = new Grid(Vec2.splat(GRID_SIZE));
    Random random = new Random();
    long startTime = System.nanoTime();

    // Perform operations on the grid
    for (int i = 0; i < NUM_ITERATIONS; i++) {
      int x = random.nextInt(GRID_SIZE);
      int y = random.nextInt(GRID_SIZE);
      grid.setCell(x, y, i); // Example operation
    }

    long endTime = System.nanoTime();
    System.out.println("Grid benchmark completed in: " +
                       (endTime - startTime) / 1_000_000 + " ms");
  }
}
