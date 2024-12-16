package cosc2658.adt;

public class Optional<T> {
  private T data;

  private Optional(T data) {
    this.data = data;
  }

  public static <T> Optional<T> none() {
    return new Optional<T>(null);
  }

  public static <T> Optional<T> some(T obj) {
    return new Optional<T>(obj);
  }

  public boolean isNone() {
    return data == null;
  }

  public T unwrap() {
    if (data == null)
      throw new RuntimeException("Cannot unwrap on a None value");
    return data;
  }
}
