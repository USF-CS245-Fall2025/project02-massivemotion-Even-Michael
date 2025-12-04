/**
 * DummyHeadedList based implementation of the List<T> interface
 *
 * @param <T> the type of elements stored in this list
 */

public class ListImpl_DummyHead<T> implements List<T> {

    // Singly-linked node
    private class Node {
        T data;
        Node next;
        Node() {}
        Node(T value) { data = value; }
    }

    int size;
    Node dummy;
    Node tail;

    public ListImpl_DummyHead() {
        size = 0;
        dummy = new Node();
        tail = dummy;
    }



    /**
     * Inserts the specified item at the given position in the list.
     * If inserting at the end, the method appends using the tail pointer. 
     * Otherwise, the new node is linked between the dummy node or a 
     * later node and the node currently at the specified position.
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

        if (pos == size) {
            tail.next = n;
            tail = n; 
            size++;
            return;
        }

        Node prev = dummy;
        Node curr = dummy.next;

        for (int i = 0; i < pos; i++) {
            prev = curr;
            curr = curr.next;
        }

        n.next = curr;
        prev.next = n;  

        if (prev == tail) {
            tail = n;
        }  

        size++;
    }

    /**
     * Appends the specified item to the end of the list.
     * This runs in O(1) time due to the tail pointer.
     *
     * @param item the element to append
     * @return true (from list interface)
     */
    @Override
    public boolean add(T item) {
        Node n = new Node(item);
        tail.next = n;
        tail = n;
        size++;
        return true;
    }

    /**
     * Returns the element at the specified position in the list.
     * Traversal begins after the dummy head node.
     *
     * @param pos the index of the element to retrieve
     * @return the element stored at the specified position
     */
    @Override
    public T get(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("index=" + pos + ", size=" + size);
        }
        Node cur = dummy.next; 
        for (int i = 0; i < pos; i++) {
            cur = cur.next;
        }
        return cur.data;
    }

    /**
     * Removes the element at the specified position in the list.
     * The dummy node simplifies removal at the head. If the removed
     * node is the last real node, the tail pointer is updated.
     *
     * @param pos the index of the element to remove
     * @return the element that was removed
     */
    @Override
    public T remove(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("index=" + pos + ", size=" + size);
        }

        Node prev = dummy;
        for (int i = 0; i < pos; i++) {
            prev = prev.next;
        }

        Node target = prev.next;
        T val = target.data;

        prev.next = target.next;

        if (target == tail) {
            tail = prev;
        }

        size--;
        return val;
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
