package cosc2658;

import org.junit.jupiter.api.Test;

import cosc2658.adt.Vec2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

class AppTest {

  @Test
  void grid8x8() {
    Grid grid = new Grid(Vec2.splat(8));
    assertEquals(8934966, grid.findAllPaths());
  }
}
