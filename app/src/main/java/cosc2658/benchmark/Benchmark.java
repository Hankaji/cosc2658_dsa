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
    benchmark();

    System.out.println("Benchmark completed.");
  }

  private static void benchmark() {
    int size = 8;
    Grid grid = new Grid(Vec2.splat(size));
    String instruction = "";
    int maxLength = size * size;
    if (instruction.length() <= maxLength) {
      instruction += "*".repeat(maxLength - instruction.length());
    }
    try {
      grid.setInstruction(instruction);
    } catch (Exception e) {
      System.out.println(e);
    }

    long startTime = System.currentTimeMillis();
    int allPaths = grid.findAllPaths();
    long endTime = System.currentTimeMillis();
    System.out.println("Total paths: " + formatNumber(allPaths));
    System.out.println("Execution time: " + formatNumber(endTime - startTime) +
                       " ms");
  }
  private static String formatNumber(long durationMs) {
    return String.format("%,d", durationMs);
  }
}
