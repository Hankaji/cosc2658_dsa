package cosc2658;

import cosc2658.adt.Optional;

import java.util.Scanner;

import cosc2658.adt.ArrayList;
import cosc2658.adt.LinkedListQueue;
import cosc2658.adt.Vec2;

public class Grid {
  private Vec2 size;
  private int[] gridData; // Contains 2 types of int. 1 = blocked & 0 = passable
  private Vec2 startPos;
  private Vec2 desPos; // Destination position
  private Vec2 currPos; // Curent position
  private String instruction;
  private Vec2 oneWayPoint;

  private boolean debugMode = false;

  private Scanner input = new Scanner(System.in);

  public Grid(Vec2 size, Vec2 startPos, Vec2 desPos) {
    this.size = size;
    this.gridData = new int[size.x * size.y];
    this.startPos = startPos;
    this.desPos = desPos;
    this.instruction = "*".repeat(size.x * size.y);
  }

  public Grid(Vec2 size) {
    this(size, new Vec2(0, size.y - 1), Vec2.ZERO);
  }

  public int findAllPaths() {
    currPos = startPos;
    return findAllPathsHelper(0, Optional.none());
  }

  private int findAllPathsHelper(int steps, Optional<Vec2> direction) {

    setGridData(currPos, true);

    if (debugMode) {
      System.out.println();
      drawMap();
      try {
        // Thread.sleep(800);
        Thread.sleep(1);
      } catch (Exception e) {
      }
    }

    // Base cases
    if (currPos.equals(desPos)) {
      if (steps == gridData.length - 1) {
        setGridData(currPos, false);
        return 1;
      } else {
        setGridData(currPos, false);
        return 0;
      }
    }

    int pathSum = 0;
    // Explore the neighbors cells
    ArrayList<Vec2> neighborCells = findNeighbors(currPos, steps, direction);
    // System.out.println("owp: " + oneWayPoint);
    for (Vec2 neighborCell : neighborCells) {
      currPos.selfAdd(neighborCell);
      // Recursively traverse the gridmap
      pathSum += findAllPathsHelper(steps + 1, Optional.some(neighborCell));
      currPos.selfSubtract(neighborCell);
      if (currPos.equals(oneWayPoint)) {
        // System.out.println();
        // drawMap();
        // input.nextLine();
        oneWayPoint = null;
      }
    }
    if (currPos.equals(oneWayPoint))
      oneWayPoint = null;

    setGridData(currPos, false);
    return pathSum;
  }

  private ArrayList<Vec2> findNeighbors(Vec2 currPos, int stepNth, Optional<Vec2> optDir) {

    switch (instruction.charAt(stepNth)) {
      case '*':

        if (optDir.isNone()) {
          ArrayList<Vec2> directionToCheck = new ArrayList<>(3);

          for (Vec2 neighborCell : new Vec2[] { Vec2.RIGHT, Vec2.TOP, Vec2.LEFT,
              Vec2.BOT }) {
            // If the neighbor cell is valid, add them to to-be-traversed tiles
            if (isCellValid(currPos.add(neighborCell))) {
              directionToCheck.push(neighborCell);
            }
          }

          return directionToCheck;
        }

        Vec2 dir = optDir.unwrap();

        ArrayList<Vec2> directionToCheck = new ArrayList<>(3);
        ArrayList<Vec2> priotizedDirections = new ArrayList<>(1);

        for (Vec2 neighborDir : new Vec2[] {
            dir.rotateLeft(),
            dir,
            dir.rotateRight(),
        }) {
          Vec2 neighborCell = currPos.add(neighborDir);
          if (!isCellValid(neighborCell))
            continue;

          boolean cellLeftValid = isCellValid(neighborCell.add(neighborDir.rotateLeft()));
          boolean cellAheadValid = isCellValid(neighborCell.add(neighborDir));
          boolean cellRightValid = isCellValid(neighborCell.add(neighborDir.rotateRight()));

          // In case target neighbor cell is the destination point
          // We ignore it from priotized list since we want it to be the last cell
          // traversed to
          if (!neighborCell.equals(desPos)) {
            // If either of the 2 edges (Excluding the edge to the current cell) are
            // blocked, traverse that block
            // Boolean algebra: (A ∧ B) v (B ∧ C) v (A ∧ C)
            if ((!cellLeftValid && !cellAheadValid) || (!cellAheadValid && !cellRightValid)
                || (!cellLeftValid && !cellRightValid)) {
              priotizedDirections.push(neighborDir);
              return priotizedDirections;
            }
          }

          // Check for cases where it partition grid into 2 regions
          boolean cellTopLeftValid = isCellValid(neighborCell.add(neighborDir.add(neighborDir.rotateLeft())));
          boolean cellTopRightValid = isCellValid(neighborCell.add(neighborDir.add(neighborDir.rotateRight())));

          boolean caseLeftEdge = !cellTopLeftValid && cellLeftValid && cellAheadValid;
          boolean caseAhead = !cellAheadValid && cellLeftValid && cellRightValid;
          boolean caseRightEdge = !cellTopRightValid && cellAheadValid &&
              cellRightValid;

          // If either case is true (which partition grid into 2 regions)
          // ignore that route
          if (!caseLeftEdge && !caseAhead && !caseRightEdge)
            directionToCheck.push(neighborDir);
          else if (neighborDir.equals(dir) && oneWayPoint == null)
            if (isCellValid(currPos.add(dir.rotateLeft())) && isCellValid(currPos.add(dir.rotateRight()))) {
              if (!isCellValid(currPos.add(dir.rotateLeft().mul(2)))
                  || !isCellValid(currPos.add(dir.rotateRight().mul(2))))
                continue;
              // System.out.println("Reached 1way");
              // input.nextLine();
              int[] gridClone = gridData.clone();
              gridClone[to1d(neighborCell)] = 1;
              oneWayPoint = new Vec2(currPos);
              if (bucketFill(gridClone, currPos.add(dir.rotateLeft()))) {
                // System.out.println("Left way");
                return new ArrayList<>(new Vec2[] { dir.rotateRight() });
              } else {
                // System.out.println("Right way");
                return new ArrayList<>(new Vec2[] { dir.rotateLeft() });
              }
            }

          // if (neighborDir.equals(dir) && oneWayPoint == null) {
          //
          // // continue;
          // if (!isCellValid(currPos.add(dir)) ||
          // !isCellValid(currPos.add(dir.rotateRight())))
          // continue;
          // if (!isCellValid(currPos.add(dir.rotateLeft().mul(2)))
          // || !isCellValid(currPos.add(dir.rotateRight().mul(2))))
          // continue;
          // // System.out.println("Reached 1way");
          // // input.nextLine();
          // int[] gridClone = gridData.clone();
          // gridClone[to1d(neighborCell)] = 1;
          // oneWayPoint = new Vec2(currPos);
          // if (bucketFill(gridClone, currPos.add(dir.rotateLeft()))) {
          // // System.out.println("Left way");
          // return new ArrayList<>(new Vec2[] { dir.rotateRight() });
          // } else {
          // // System.out.println("Right way");
          // return new ArrayList<>(new Vec2[] { dir.rotateLeft() });
          // }
          //
          // }

        }

        return directionToCheck;
      case 'U':
        if (!isCellValid(currPos.add(Vec2.TOP)))
          break;
        return new ArrayList<>(new Vec2[] { Vec2.TOP });
      case 'R':
        if (!isCellValid(currPos.add(Vec2.RIGHT)))
          break;
        return new ArrayList<>(new Vec2[] { Vec2.RIGHT });
      case 'D':
        if (!isCellValid(currPos.add(Vec2.BOT)))
          break;
        return new ArrayList<>(new Vec2[] { Vec2.BOT });
      case 'L':
        if (!isCellValid(currPos.add(Vec2.LEFT)))
          break;
        return new ArrayList<>(new Vec2[] { Vec2.LEFT });

      default:
        throw new RuntimeException("Unrecognizable direction");
    }

    return new ArrayList<>();
  }

  private boolean bucketFill(int[] gridData, Vec2 initialPos) {
    LinkedListQueue<Vec2> queue = new LinkedListQueue<>();

    queue.enQueue(initialPos);
    gridData[to1d(initialPos)] = 1;

    while (!queue.isEmpty()) {
      Vec2 curr = queue.peekFront();
      queue.deQqueue();

      if (curr.equals(desPos))
        return true;

      for (Vec2 neighborDir : new Vec2[] { Vec2.RIGHT, Vec2.TOP, Vec2.LEFT,
          Vec2.BOT }) {
        Vec2 neighborCell = curr.add(neighborDir);

        if (neighborCell.x < 0 || neighborCell.x >= size.x || neighborCell.y < 0
            || neighborCell.y >= size.y)
          continue;
        if (gridData[to1d(neighborCell)] == 0) {
          queue.enQueue(neighborCell);
          gridData[to1d(neighborCell)] = 1;
        }
      }
    }

    return false;
  }

  private boolean isCellValid(Vec2 pos) {
    // Check is position is outside of map boudary
    if (pos.x < 0 || pos.x >= size.x || pos.y < 0 || pos.y >= size.y) {
      return false;
    } else if (getData(pos) == 1) {
      return false;
    }

    return true;
  }

  public Grid useDebug() {
    debugMode = true;
    return this;
  }

  private int to1d(Vec2 idx2d) {
    return idx2d.x + idx2d.y * this.size.x;
  }

  private int getData(Vec2 pos) {
    return this.gridData[to1d(pos)];
  }

  public void setInstruction(String instruction) throws Exception {
    final char[] validDirections = new char[] { '*', 'U', 'R', 'D', 'L' };
    instruction = instruction.toUpperCase();

    outerloop: for (char dir : instruction.toCharArray()) {
      for (char validDir : validDirections) {
        if (dir == validDir) {
          continue outerloop;
        }
      }
      throw new Exception(String.format("Invalid direction in instruction: %c", dir));
    }

    this.instruction = instruction;
  }

  public void setGridData(Vec2 pos, boolean visited) {
    gridData[to1d(pos)] = visited ? 1 : 0;
  }

  public void drawMap() {
    for (int y = this.size.y - 1; y >= 0; y--) {
      for (int x = 0; x < this.size.x; x++) {
        if (new Vec2(x, y).equals(currPos)) {
          System.out.print("c");
          continue;
        } else if (new Vec2(x, y).equals(desPos)) {
          System.out.print("e");
          continue;
        }

        System.out.print(this.gridData[to1d(new Vec2(x, y))]);
      }
      System.out.println();
    }
  }

  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}
