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

    @Override
    public void add(int pos, T item) {
        if (pos < 0 || pos > size) {
            throw new IndexOutOfBoundsException("index=" + pos + ", size=" + size);
        }

        // insert into empty list (pos == 0)
        if (size == 0) {
            Node n = new Node(item);
            head = tail = n;
            size++;
            return;
        }

        // insert at head
        if (pos == 0) {
            Node n = new Node(item);
            n.next = head;
            head.prev = n;
            head = n;
            size++;
            return;
        }

        // append at tail
        if (pos == size) {
            add(item); // reuse append path
            return;
        }

        // insert before current node at index pos (middle)
        Node nextNode = nodeAt(pos);
        Node prevNode = nextNode.prev;
        Node n = new Node(item);

        n.prev = prevNode;
        n.next = nextNode;
        prevNode.next = n;
        nextNode.prev = n;

        size++;
    }

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

    @Override
    public T get(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("index=" + pos + ", size=" + size);
        }
        return nodeAt(pos).data;
    }

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
                tail = null;        // list became empty
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
                head = null;        // list became empty
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

    @Override
    public int size() {
        return size;
    }
}
