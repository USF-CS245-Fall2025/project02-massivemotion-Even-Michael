

/**
 * ArrayList based implementation of the List<T> interface
 *
 * @param <T> the type of elements stored in this list
 */
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
        this.arr = newArray(10);
        this.size = 0;
    }

    private void growArray() {
        int newCap = arr.length * 2; 
        T[] newArr = newArray(newCap);
        for (int i = 0; i < size; i++) newArr[i] = arr[i];
        arr = newArr;
    }

    /**
     * Inserts specified item at given position in the list.
     * Shifts all elements at and after the position one slot to the right.
     * If the internal array is full, the capacity is doubled before insertion.
     *
     * @param pos the position at which the item should be inserted
     * @param item the element to insert
     */
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

    /**
     * Appends the specified item to the end of the list.
     * If the internal array is full, the capacity is doubled before adding.
     *
     * @param item the element to append
     * @return true (from list interface)
     */
    @Override
    public boolean add(T item) {
        if (size == arr.length) {
            growArray(); 
        }
        arr[size++] = item;
        return true;
    }

    /**
     * Returns the element at the specified position in the list.
     *
     * @param pos the index of the element to return
     * @return the element at the specified position
     */
    @Override
    public T get(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("index=" + pos + ", size=" + size);
        }
        return arr[pos];
    }

    /**
     * Removes the element at the specified position in the list.
     * Shifts all elements after the removed position one slot to the left.
     *
     * @param pos the index of the element to remove
     * @return the element that was removed
     */
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
