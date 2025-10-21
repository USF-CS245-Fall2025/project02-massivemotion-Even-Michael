package ListImpls;


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


    @Override
    public void add(int pos, T item) {
        if (pos < 0 || pos > size) {
            throw new IndexOutOfBoundsException("index=" + pos + ", size=" + size);
        }

        // List is empty
        // Shifting old head to the right and creating new head with new data 
        if (pos == 0) {
            Node n = new Node(item);
            n.next = head;
            head = n;
            size++;
            return;
        }

        // insert in middle or at tail (pos == size)
        Node prev = head;
        for (int i = 0; i < pos - 1; i++) {
            prev = prev.next;
        }
        Node n = new Node(item);
        n.next = prev.next;   // this is null if pos == size (append)
        prev.next = n;
        size++;
    }


    @Override
    public boolean add(T item) {
        // If list is empty
        if (size == 0) {
            head = new Node(item);
            size++;
            return true;
        } 
        // else
        // Appending at the end
        Node node = head;
        while (node.next != null) {
            node = node.next;
        }
        node.next = new Node(item);
        size++;
        return true;
    }

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
        prev.next = target.next; // unlink
        size--;
        return target.data;
    }

    @Override
    public int size() {
        return size;
    }
    
}
