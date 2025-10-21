package ListImpls;


public class ListImpl_ArrayList<T> implements List<T> {

    // Storage
    @SuppressWarnings("unchecked")
    
    private T[] newArray(int cap) { 
        return (T[]) new Object[cap]; 
        }

    private T[] arr;
    private int size;

    // Initializing an empty resizable array list.
    public ListImpl_ArrayList() {
        this.arr = newArray(10); // initial capacity 10
        this.size = 0;
    }

    // Core API
    private void growArray() {
        int newCap = Math.max(1, arr.length * 2);
        T[] newArr = newArray(newCap);
        // copy only the used slots [0, size)
        for (int i = 0; i < size; i++) newArr[i] = arr[i];
        arr = newArr;
    }

    @Override
    public void add(int pos, T item) {
        if (pos < 0 || pos > size) {
            throw new IndexOutOfBoundsException("index=" + pos + ", size=" + size);
        }
        if (size == arr.length) {
            growArray();
        }
        for (int i = size; i > pos; i--) {
            arr[i] = arr[i - 1];
        }
        arr[pos] = item;
        size++;
    }

    @Override
    public boolean add(T item) {
        if (size == arr.length) {
            growArray(); 
        }
        arr[size++] = item;
        return true;
    }

    @Override
    public T get(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("index=" + pos + ", size=" + size);
        }
        return arr[pos];
    }

    @Override
    public T remove(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("index=" + pos + ", size=" + size);
        }

        T old = arr[pos];

        for (int i = pos; i < size - 1; i++) {
            arr[i] = arr[i + 1];
        }
        arr[size - 1] = null;
        size--;
        return old;
    }

    @Override
    public int size() {
        return size;
    }
}
