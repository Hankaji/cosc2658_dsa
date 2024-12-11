package cosc2658;

import java.util.Optional;

import cosc2658.adt.Vec2;

public class Grid {
  private Vec2 size;
  private int[] gridData; // Contains 2 types of int. 1 = blocked & 0 = passable
  private Vec2 startPos;
  private Vec2 desPos; // Destination position
  private Vec2 currPos; // Curent position
  private String instruction;

  private boolean debugMode = false;

  public static void main(String[] args) {

  }

  public int findAllPaths() {
    currPos = startPos;
    return findAllPathsHelper(0, Optional.empty());
  }

  private int findAllPathsHelper(int steps, Optional<Vec2> direction) {

    setGridData(currPos, true);

    if (debugMode) {
      // clearScreen();
      drawMap();
      try {
        Thread.sleep(1000);
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

    // Early termination
    if (direction.isPresent()) {
      Vec2 dir = direction.get();
      if (!isCellValid(currPos.add(dir))) {
        if (dir.equals(Vec2.X) || dir.equals(Vec2.negX)) {
          if (isCellValid(currPos.add(Vec2.Y)) && isCellValid(currPos.add(Vec2.negY))) {
            setGridData(currPos, false);
            return 0;
          }
        } else if (dir.equals(Vec2.Y) || dir.equals(Vec2.negY)) {
          if (isCellValid(currPos.add(Vec2.X)) && isCellValid(currPos.add(Vec2.negX))) {
            setGridData(currPos, false);
            return 0;
          }
        }
      }
    }
    ;

    int pathSum = 0;
    // Explore all 4 ways
    for (Vec2 neighborPath : new Vec2[] { Vec2.X, Vec2.Y, Vec2.negX, Vec2.negY }) {
      // Get r,t,l,b Cell
      currPos.selfAdd(neighborPath);
      if (isCellValid(currPos)) {
        pathSum += findAllPathsHelper(steps + 1, Optional.of(neighborPath));
      }
      currPos.selfSubtract(neighborPath);
    }

    setGridData(currPos, false);

    return pathSum;
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

  public Grid(Vec2 size, Vec2 startPos, Vec2 desPos) {
    this.size = size;
    this.gridData = new int[size.x * size.y];
    this.startPos = startPos;
    this.desPos = desPos;
    this.instruction = "*".repeat(size.x * size.y);
  }

  private int to1d(Vec2 idx2d) {
    return idx2d.x + idx2d.y * this.size.x;
  }

  private int getData(Vec2 pos) {
    return this.gridData[to1d(pos)];
  }

  public void setInstruction(String instruction) {
    // TODO:
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
        }
        System.out.print(this.gridData[to1d(new Vec2(x, y))]);
      }
      System.out.println();
    }
    System.out.println();
  }

  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}
