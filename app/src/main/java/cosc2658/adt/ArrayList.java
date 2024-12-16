package cosc2658.adt;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Array-based implementation of stack
public class ArrayList<T> implements Iterable<T> {
  private int size;
  private int MAX_SIZE = 10;
  private T[] items;

  @SuppressWarnings("unchecked")
  public ArrayList(int capacity) {
    MAX_SIZE = capacity;
    size = 0;
    items = (T[]) new Object[MAX_SIZE];
  }

  public ArrayList() {
    this(10);
  }

  public int size() {
    return size;
  }

  public boolean push(T item) {
    // make sure the stack still have empty slot
    if (size < MAX_SIZE) {
      items[size] = item;
      size++;
      return true;
    }
    return false;
  }

  public T remove(int index) {
    checkIndex(index);
    T removedElement = items[index];
    int numMoved = size - index - 1;
    for (int i = 0; i < numMoved; i++) {
      items[i + index] = items[i + index + 1];
    }
    size--;
    return removedElement;
  }

  public T get(int index) {
    checkIndex(index);
    return items[index];
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

  // ------------------------- Helper methods -------------------------
  private void checkIndex(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
  }

  public boolean isEmpty() {
    return size == 0;
  }

}
