package cosc2658.adt;

// Singly Linked List-based implementation of stack
public class LinkedListStack<T> {
  // this class is used as a container of data
  static class Node<T> {
    T data;
    Node<T> next;

    public Node(T data) {
      this.data = data;
      this.next = null;
    }
  }

  private int size;
  private Node<T> head;

  public LinkedListStack() {
    size = 0;
    head = null;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public boolean push(T item) {
    // add a new node at the beginning
    Node<T> node = new Node<T>(item);
    if (!isEmpty()) {
      node.next = head;
    }
    head = node;
    size++;
    return true;
  }

  public boolean pop() {
    // remove the first node
    // make sure the stack is not empty
    if (isEmpty()) {
      return false;
    }
    // advance head
    head = head.next;
    size--;
    return true;
  }

  public T peek() {
    // make sure the stack is not empty
    if (isEmpty()) {
      return null;
    }
    return head.data;
  }

  @Override
  public String toString() {
    if (size == 0) {
      return "[]";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    Node<T> currNode = head;
    while (currNode != null) {
      sb.append(currNode.data);
      currNode = currNode.next;
      if (currNode != null)
        sb.append(", ");
    }
    sb.append("]");
    return sb.toString();
  }

}
