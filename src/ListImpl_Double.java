/**
 * DoubleLinkedList based implementation of the List<T> interface
 *
 * @param <T> the type of elements stored in this list
 */
public class ListImpl_Double<T> implements List<T> {

    // Doubly-linked node
    private class Node {
        T data;
        Node prev;
        Node next;
        Node(T value) { data = value; }
    }

    int size;
    Node head;
    Node tail;

    // Empty list
    public ListImpl_Double() {
        size = 0;
        head = null;
        tail = null;
    }

    // Return node at index (0..size-1). Walk from nearer end.
    private Node nodeAt(int index) {
        if (index < (size / 2)) {
            Node cur = head;
            for (int i = 0; i < index; i++) cur = cur.next;
            return cur;
        } else {
            Node cur = tail;
            for (int i = size - 1; i > index; i--) cur = cur.prev;
            return cur;
        }
    }


    /**
     * Inserts the specified item at the given position in the list.
     * If inserting at the end, this method reuses the append logic.
     * Otherwise, the new node is linked before the node currently
     * at the specified position.
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

        // append at tail
        if (pos == size) {
            add(item);
            return;
        }

        // insert before current node at index pos (middle)
        Node nextNode = nodeAt(pos);
        Node prevNode = nextNode.prev;
        
        n.prev = prevNode;
        n.next = nextNode;

        if (prevNode == null) {
            head = n;
        } else {
            prevNode.next = n;
        }

        nextNode.prev = n;

        size++;
    }


    /**
     * Appends item to the end of the list.
     *
     * @param item the element to append
     * @return true (from list interface)
     */
    @Override
    public boolean add(T item) {
        Node n = new Node(item);
        if (size == 0) {
            head = tail = n;
        } else {
            n.prev = tail;
            tail.next = n;
            tail = n;
        }
        size++;
        return true;
    }

    /**
     * Returns the element at the specified position in the list.
     *
     * @param pos the index of the element to retrieve
     * @return the element stored at pos
     */
    @Override
    public T get(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("index=" + pos + ", size=" + size);
        }
        return nodeAt(pos).data;
    }


    /**
     * Removes the element at the specified position in the list.
     * Handles removal at the head, tail, or any middle position.
     *
     * @param pos the index of the element to remove
     * @return the element that was removed
     */
    @Override
    public T remove(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("index=" + pos + ", size=" + size);
        }

        // Removing head
        if (pos == 0) {
            Node old = head;
            T val = old.data;
            head = old.next;
            if (head == null) {
                tail = null;       
            } else {
                head.prev = null;
            }
            size--;
            return val;
        }

        // Removing tail
        if (pos == size - 1) {
            Node old = tail;
            T val = old.data;
            tail = old.prev;
            if (tail == null) {
                head = null;        
            } else {
                tail.next = null;
            }
            size--;
            return val;
        }

        // Removing middle
        Node target = nodeAt(pos);
        T val = target.data;
        Node prevNode = target.prev;
        Node nextNode = target.next;

        prevNode.next = nextNode;
        nextNode.prev = prevNode;

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
