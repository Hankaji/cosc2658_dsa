package cosc2658.adt;

public class Optional<T> {
  private T data;

  private Optional(T data) {
    this.data = data;
  }

  /**
   * Construct a None Optional
   *
   * @param <T>
   * @return A None
   */
  public static <T> Optional<T> none() {
    return new Optional<T>(null);
  }

  /**
   * Construct a Some with a value
   *
   * @param <T> The object type T
   * @return An Optional with some value T
   */
  public static <T> Optional<T> some(T obj) {
    return new Optional<T>(obj);
  }

  /**
   * Check if value is none or not
   *
   * @return boolean, if it is a none or not (null data)
   */
  public boolean isNone() {
    return data == null;
  }

  /**
   * Unwrap and return the inner T value
   * Nonsafe function, throw an Exception if data is null/none
   *
   * @return T, The underlying data
   */
  public T unwrap() {
    if (data == null)
      throw new RuntimeException("Cannot unwrap on a None value");
    return data;
  }
}
