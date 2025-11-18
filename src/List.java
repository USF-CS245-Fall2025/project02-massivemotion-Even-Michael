
public interface List<T> {

    /**
     * Inserts an element at the given position, shifting subsequent elements
     * to the right.
     *
     * @param index position at which to insert (0 <= index <= size)
     * @param element element to insert
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public void add(int index, T element);

    /**
     * Appends an element to the end of the list.
     *
     * @param element element to append
     * @return {@code true} to match the Java Collection add convention
     */
    public boolean add(T element);

    /**
     * Returns the element at the given index.
     *
     * @param index position of the element to retrieve (0 <= index < size)
     * @return element stored at the given index
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public T get(int index);

    /**
     * Removes and returns the element at the given index, shifting later
     * elements to the left.
     *
     * @param index index of the element to remove (0 <= index < size)
     * @return the element that was removed
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public T remove(int index);

    /**
     * Returns the number of elements currently stored in the list.
     *
     * @return the size of the list
     */
    public int size();
}
