/*
 * This source file was generated by the Gradle 'init' task
 */
package cosc2658;

import cosc2658.adt.Vec2;
import java.util.Scanner;

public class App {

  // SOLUTION_3 = 2;
  // SOLUTION_4 = 8;
  // SOLUTION_5 = 86;
  // SOLUTION_6 = 1770;
  // SOLUTION_8 = 8943966;

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    System.out.println("Welcome to maze traversal algorithm");
    System.out.println("Type 'quit' to exit program, <Enter> to continue.");
    while (!input.nextLine().trim().toLowerCase().equals("quit")) {

      int size;
      while (true) {
        try {
          System.out.print("Enter size of Gridmap (Integer number): ");
          size = Integer.parseInt(input.nextLine().trim());
          break;
        } catch (NumberFormatException e) {
          System.out.println(
              "Invalid input for x. Please enter a valid number.");
        }
      }
      Grid grid = new Grid(Vec2.splat(size));

      String instruction = "";
      int maxLength = size * size;
      while (true) {
        System.out.print("Give direction instruction [*, U, R, D, L] (up to " +
                         maxLength + " characters) - ");
        System.out.println("Empty/unfilled character will be defaulted to '*'");
        instruction = input.nextLine();

        if (instruction.length() <= maxLength) {
          instruction += "*".repeat(maxLength - instruction.length());
        } else {
          System.out.println("Input exceeds max characters " +
                             instruction.length() + "/" + maxLength +
                             ".Please try again.");
        }
        try {
          grid.setInstruction(instruction);
          break;
        } catch (Exception e) {
          System.out.println(e);
        }
      }

      System.out.println("Executing...\n");
      long startTime = System.currentTimeMillis();

      int allPaths = grid.findAllPaths();

      long endTime = System.currentTimeMillis();

      System.out.println("Total paths: " + formatNumber(allPaths));
      System.out.println(
          "Execution time: " + formatNumber(endTime - startTime) + " ms");

      System.out.println("-".repeat(50));
      System.out.println("Type 'quit' to exit program, <Enter> to continue.");
    }

    input.close();
  }

  public static String formatNumber(long durationMs) {
    return String.format("%,d", durationMs);
  }
}
