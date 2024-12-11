package cosc2658.adt;

public class Vec2 implements Cloneable {
  public int x;
  public int y;

  public static Vec2 X = new Vec2(1, 0);
  public static Vec2 Y = new Vec2(0, 1);
  public static Vec2 negX = new Vec2(-1, 0);
  public static Vec2 negY = new Vec2(0, -1);

  public Vec2(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Vec2(Vec2 other) {
    this.x = other.x;
    this.y = other.y;
  }

  @Override
  public boolean equals(Object obj) {

    Vec2 vec2Obj = (Vec2) obj;

    return this.x == vec2Obj.x && this.y == vec2Obj.y;
  }

  public void selfAdd(Vec2 other) {
    x += other.x;
    y += other.y;
  }

  public void selfSubtract(Vec2 other) {
    x -= other.x;
    y -= other.y;
  }

  public Vec2 add(Vec2 other) {
    return new Vec2(x + other.x, y + other.y);
  }

  public Vec2 subtract(Vec2 other) {
    return new Vec2(x - other.x, y - other.y);
  }

  @Override
  public String toString() {
    return "Vec2(" + x + ", " + y + ")";
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

}
