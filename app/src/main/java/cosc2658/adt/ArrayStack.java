package cosc2658.adt;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Array-based implementation of stack
public class ArrayStack<T> implements Iterable<T> {
  private int size;
  private int MAX_SIZE = 10;
  private T[] items;

  /**
   * Construct an array stack with a size capacity
   * 
   * @param capacity the initial size
   */
  @SuppressWarnings("unchecked")
  public ArrayStack(int capacity) {
    MAX_SIZE = capacity;
    size = 0;
    items = (T[]) new Object[MAX_SIZE];
  }

  /**
   * Construct an array stack with a size 10
   * 
   */
  public ArrayStack() {
    this(10);
  }

  /**
   * Construct an array stack with another array
   * 
   * @param objArray
   */
  public ArrayStack(T[] objArray) {
    this(objArray.length);
    for (T obj : objArray) {
      push(obj);
    }
  }

  /**
   * The actual size of the array
   * 
   * @return int
   */
  public int size() {
    return size;
  }

  /**
   * Push an item to the top of the stack
   * 
   * @param item T, item to push
   * @return boolean, if the push success
   */
  public boolean push(T item) {
    // make sure the stack still have empty slot
    if (size < MAX_SIZE) {
      items[size] = item;
      size++;
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    if (size == 0) {
      return "[]";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < size; i++) {
      sb.append(items[i]);
      if (i < size - 1) {
        sb.append(", ");
      }
    }
    sb.append("]");
    return sb.toString();
  }

  private class ArrayListIterator implements Iterator<T> {

    private int currentIdx = 0;

    @Override
    public boolean hasNext() {
      return currentIdx < size;
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return items[currentIdx++];
    }
  }

  @Override
  public Iterator<T> iterator() {
    return new ArrayListIterator();
  }

  /**
   * Check if the array is empty or not
   * 
   * @return boolean
   */
  public boolean isEmpty() {
    return size == 0;
  }

}
