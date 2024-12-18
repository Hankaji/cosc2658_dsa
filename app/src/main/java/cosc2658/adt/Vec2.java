package cosc2658.adt;

public class Vec2 implements Cloneable {
  public int x;
  public int y;

  public static final Vec2 ZERO = new Vec2(0, 0);

  public static final Vec2 RIGHT = new Vec2(1, 0);
  public static final Vec2 TOP = new Vec2(0, 1);
  public static final Vec2 LEFT = new Vec2(-1, 0);
  public static final Vec2 BOT = new Vec2(0, -1);

  public static final Vec2 TOP_LEFT = new Vec2(-1, 1);
  public static final Vec2 TOP_RIGHT = new Vec2(1, 1);
  public static final Vec2 BOT_RIGHT = new Vec2(1, -1);
  public static final Vec2 BOT_LEFT = new Vec2(-1, -1);

  /**
   * Return a Vector with all same value for x and y
   * 
   * @param v int
   * @return Vec2
   */
  public static Vec2 splat(int v) {
    return new Vec2(v, v);
  }

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

  /**
   * Mutate self, add other vector2 to this
   * 
   * @param other
   */
  public void selfAdd(Vec2 other) {
    x += other.x;
    y += other.y;
  }

  /**
   * Mutate self, subtract other vector2 to this
   * 
   * @param other
   */
  public void selfSubtract(Vec2 other) {
    x -= other.x;
    y -= other.y;
  }

  /**
   * Add other vector2
   * 
   * @param other Vec2
   * @return Vec2
   */
  public Vec2 add(Vec2 other) {
    return new Vec2(x + other.x, y + other.y);
  }

  /**
   * Subtract other vector2
   * 
   * @param other Vec2
   * @return Vec2
   */
  public Vec2 subtract(Vec2 other) {
    return new Vec2(x - other.x, y - other.y);
  }

  /**
   * Multiply vector2 by a v factor
   * 
   * @param v factor
   * @return Vec2
   */
  public Vec2 mul(int v) {
    return new Vec2(x * v, y * v);
  }

  /**
   * Rotate vector -90 degree to the left
   * 
   * @return Vec2
   */
  public Vec2 rotateLeft() {
    return new Vec2(-y, x);
  }

  /**
   * Rotate vector 90 degree to the right
   * 
   * @return Vec2
   */
  public Vec2 rotateRight() {
    return new Vec2(y, -x);
  }

  /**
   * Flip vector 180 degree around
   * 
   * @return Vec2
   */

  public Vec2 flip() {
    return new Vec2(-y, -x);
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
