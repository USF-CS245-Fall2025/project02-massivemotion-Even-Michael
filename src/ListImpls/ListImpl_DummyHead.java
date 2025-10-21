package ListImpls;


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

    // Empty list (dummy/sentinel head)
    public ListImpl_DummyHead() {
        size = 0;
        dummy = new Node();
        tail = dummy; // when empty, tail == dummy
    }

    @Override
    public void add(int pos, T item) {
        if (pos < 0 || pos > size) {
            throw new IndexOutOfBoundsException("index=" + pos + ", size=" + size);
        }

        // find node just before insertion point, starting from dummy
        Node prev = dummy;
        for (int i = 0; i < pos; i++) {
            prev = prev.next;
        }

        Node n = new Node(item);
        n.next = prev.next;
        prev.next = n;

        // if appended to the end, update tail
        if (prev == tail) {
            tail = n;
        }

        size++;
    }

    @Override
    public boolean add(T item) {
        Node n = new Node(item);
        tail.next = n;
        tail = n;
        size++;
        return true;
    }

    @Override
    public T get(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("index=" + pos + ", size=" + size);
        }
        Node cur = dummy.next; // first real node
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

        // find node just before target, starting from dummy
        Node prev = dummy;
        for (int i = 0; i < pos; i++) {
            prev = prev.next;
        }

        Node target = prev.next;
        T val = target.data;

        prev.next = target.next;

        // if removing the last real node, move tail back
        if (target == tail) {
            tail = prev;
        }

        size--;
        return val;
    }

    @Override
    public int size() {
        return size;
    }
}
