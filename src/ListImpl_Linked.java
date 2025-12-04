
/**
 * LinkedList based implementation of the List<T> interface
 *
 * @param <T> the type of elements stored in this list
 */
public class ListImpl_Linked<T> implements List<T> {

    // Simple singly-linked node
    private class Node {
        T data;
        Node next;
        Node(T value) { 
            data = value; 
            next = null; 
        }
    }

    int size;
    Node head;

    // Empty list
    public ListImpl_Linked() {
        size = 0;
        head = null;
    }


    /**
     * Inserts specified item at given position in the list.
     * New node is linked between the previous node and the node
     * currently at the given position.
     *
     * @param pos the index at which the item should be inserted
     * @param item the element to insert
     */
    @Override
    public void add(int pos, T item) {
        if (pos < 0 || pos > size) {
            throw new IndexOutOfBoundsException("index=" + pos + ", size=" + size);
        }

        Node n = new Node(item);
        Node prev = null;
        Node cur = head;

        for (int i = 0; i < pos; i++) {
            prev = cur;
            cur = cur.next;
        }
        
        n.next = cur;

        if (prev == null) {
            head = n;
        } else {
            prev.next = n;
        }

        size++;
    }

    /**
     * Appends the specified item to the end of the list.
     * If the list is empty, the new node becomes the head.
     *
     * @param item the element to append
     * @return true (from list interface)
     */
    @Override
    public boolean add(T item) {
        Node n = new Node(item);

        // empty list
        if (size == 0) {
            head = n;
        }
        else {
            // append to end
            Node cur = head;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = n;
        }

        size++;
        return true;
    }


    /**
     * Returns the element at the specified position in the list.
     *
     * @param pos the index of the element to retrieve
     * @return the element stored at the specified position
     */
    @Override
    public T get(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("index=" + pos + ", size=" + size);
        }
        Node cur = head;
        for (int i = 0; i < pos; i++) {
            cur = cur.next;
        }
        return cur.data;
    }


    /**
     * Removes the element at the specified position in the list.
     * Handles removal at the head as well as removal in the middle
     * or at the end.
     *
     * @param pos the index of the element to remove
     * @return the element that was removed
     */
    @Override
    public T remove(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("index=" + pos + ", size=" + size);
        }

        // remove head
        if (pos == 0) {
            Node node = head;
            head = head.next;
            size--;
            return node.data;
        }

        // remove middle/tail
        Node prev = head;
        for (int i = 0; i < pos - 1; i++) {
            prev = prev.next;
        }
        Node target = prev.next;
        prev.next = target.next;
        size--;
        return target.data;
    }

    /**
     * Returns the number of elements currently stored in the list.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return size;
    }
    
}
