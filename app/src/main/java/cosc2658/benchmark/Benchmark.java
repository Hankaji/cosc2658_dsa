package cosc2658.benchmark;

// Import ADTs and Java packages
import cosc2658.App;
import cosc2658.Grid;
import cosc2658.adt.Vec2;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Random;

public class Benchmark {

  // Benchmarking constants
  private static final int[] GRID_SIZES = {3, 4, 5, 6, 7, 8};
  private static final int NUM_ITERATIONS = 1;

  public static void main(String[] args) {
    System.out.println("Starting benchmark...");

    try (FileWriter writer = new FileWriter(
             new File(FileSystems.getDefault()
                          .getPath("src/main/java/cosc2658", "benchmark",
                                   "benchmark_results.csv")
                          .toString())
                 .getCanonicalPath())) {
      // Write CSV headers
      writer.append(
          "GridSize,InitializationTime,MemoryUsage,AverageCalculationTime\n");

      // Iterate through the grid sizes
      for (int size : GRID_SIZES) {
        System.out.println("-------------------------------------------------"
                           + "-------------------------");
        System.out.println("Grid Size: " + size);

        // Benchmark the grid for the current size
        long initializationTime = benchmarkInitialization(size);
        long memoryUsage = getMemoryUsage();
        double averageCalculationTime = benchmarkCalculationTime(size);

        // Write results to the CSV file
        writer.append(String.format("%d,%d,%d,%.2f\n", size, initializationTime,
                                    memoryUsage, averageCalculationTime));
        writer.flush(); // Ensure the data is written immediately
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("-------------------------------------------------"
                       + "-------------------------");
    System.out.println("Benchmark completed.");
  }

  private static long benchmarkInitialization(int size) {
    long startTime = System.currentTimeMillis();

    // Initialize the grid and set instruction
    Grid grid = new Grid(Vec2.splat(size));
    String instruction = "*".repeat(size * size);

    try {
      grid.setInstruction(instruction);
    } catch (Exception e) {
      System.out.println("Error during grid initialization: " + e);
    }

    long endTime = System.currentTimeMillis();
    long result = endTime - startTime;
    System.out.println("Initialization Time: " + App.formatNumber(result) +
                       " ms");
    return result;
  }

  private static double benchmarkCalculationTime(int size) {
    Grid grid = new Grid(Vec2.splat(size));
    String instruction = "*".repeat(size * size);

    try {
      grid.setInstruction(instruction);
    } catch (Exception e) {
      System.out.println("Error during instruction setup: " + e);
    }

    // Measure calculation time over multiple iterations
    long totalTime = 0;
    for (int i = 0; i < NUM_ITERATIONS; i++) {
      long startTime = System.currentTimeMillis();
      int allPaths = grid.findAllPaths();
      long endTime = System.currentTimeMillis();
      System.out.println("Total paths: " + allPaths);
      totalTime += (endTime - startTime);
    }
    double result = (totalTime / (double)NUM_ITERATIONS);
    System.out.println("Calculation Time: " + App.formatNumber((long)result) +
                       " ms");
    return result;
  }

  private static long getMemoryUsage() {
    Runtime runtime = Runtime.getRuntime();
    long result = (runtime.totalMemory() - runtime.freeMemory()) / 1024 /
                  1024; // Convert to MB
    System.out.println("Memory Usage: " + result + "MB");
    return result;
  }
}
