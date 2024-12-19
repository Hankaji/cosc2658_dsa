package cosc2658.benchmark;

// Import ADTs and Java packages
import cosc2658.App;
import cosc2658.Grid;
import cosc2658.adt.Vec2;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;

public class Benchmark {

  private static final int[] GRID_X = {3, 4, 5, 6, 7, 8};
  private static final int[] GRID_Y = {3, 4, 5, 6, 7, 8};
  private static final int NUM_ITERATIONS = 1;

  public static void main(String[] args) {
    System.out.println("Starting benchmark...");

    try (FileWriter writer = new FileWriter(
             new File(FileSystems.getDefault()
                          .getPath("src/main/java/cosc2658", "benchmark",
                                   "benchmark_results_x_y.csv")
                          .toString())
                 .getCanonicalPath())) {
      writer.append("GridX,GridY,TotalPaths,InitializationTime,MemoryUsage,"
                    + "AverageCalculationTime,IsAllWildcards\n");

      for (int i = 0; i < NUM_ITERATIONS; i++) {
        for (int x : GRID_X) {
          for (int y : GRID_Y) {
            System.out.println("\nBenchmarking Grid Size: " + x + " x " + y);

            // Benchmark All Wildcards
            String instruction = "*".repeat(x * y);
            benchmarkAndWrite(writer, x, y, instruction, true);

            // Benchmark Random Instructions
            instruction = Grid.generateRandomInstruction(x*y);
            benchmarkAndWrite(writer, x, y, instruction, false);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("Benchmark completed.");
  }

  private static void benchmarkAndWrite(FileWriter writer, int x, int y, 
                                        String instruction, boolean isWildcard) 
                                        throws IOException {
    System.out.println("\tBenchmarking " + (isWildcard ? "All Wildcards" : "Random Instructions"));
    long initTime = benchmarkInitialization(x, y, instruction);
    // double calcTime = benchmarkCalculationTime(x, y, instruction);

    Grid grid = new Grid(new Vec2(x, y));

    try {
      grid.setInstruction(instruction);
    } catch (Exception e) {
      System.out.println("Error during instruction setup: " + e);
    }

    long startTime = System.currentTimeMillis();
    int allPaths = grid.findAllPaths();
    long endTime = System.currentTimeMillis();

    System.out.println("Total paths: " + allPaths);
    double calcTime = endTime - startTime;
    System.out.println("Calculation Time: " + App.formatNumber((long) calcTime) + " ms");


    long memoryUsage = getMemoryUsage(Runtime.getRuntime());

    writer.append(String.format("%d,%d,%d,%d,%d,%d,%d\n", x, y, allPaths, initTime, memoryUsage, (long)calcTime, isWildcard ? 1 : 0));
    writer.flush();
  }

  private static long benchmarkInitialization(int x, int y, String instruction) {
    long startTime = System.currentTimeMillis();
    Grid grid = new Grid(new Vec2(x, y));

    try {
      grid.setInstruction(instruction);
    } catch (Exception e) {
      System.out.println("Error during grid initialization: " + e);
    }

    long endTime = System.currentTimeMillis();
    long result = endTime - startTime;
    System.out.println("Initialization Time: " + App.formatNumber(result) + " ms");
    return result;
  }

  private static long getMemoryUsage(Runtime runtime) {
    long result = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
    System.out.println("Memory Usage: " + result + " MB");
    return result;
  }
}
