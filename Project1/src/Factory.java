import java.util.NoSuchElementException;

public interface Factory {

    /**
     * Inserts {@code product} at the beginning of this factory line.
     *
     * @param   product the product to add
     */
    void addFirst(Product product);

    /**
     * Inserts {@code product} to the end of this factory line.
     *
     * @param   product the product to add
     */
    void addLast(Product product);

    /**
     * Removes and returns the first product from this factory line.
     *
     * @return  the first product from this factory line
     * @throws  NoSuchElementException if the line is empty
     */
    Product removeFirst() throws NoSuchElementException;

    /**
     * Removes and returns the first product from this factory line.
     *
     * @return  the last product from this factory line
     * @throws  NoSuchElementException if the line is empty
     */
    Product removeLast() throws NoSuchElementException;

    /**
     * Finds and returns the product with the specified {@code id}.
     *
     * @param   id id of the product
     * @return  the product with the specified id
     * @throws  NoSuchElementException if the product does not exist
     */
    Product find(int id) throws NoSuchElementException;

    /**
     * Updates the product value with {@code id} in this factory line
     * with the given {@code newValue}.
     *
     * @param   id    id of the product to update
     * @param   value new value for the specified product
     * @return  the previous product with {@code id}
     * @throws  NoSuchElementException if the product does not exist
     */
    Product update(int id, Integer value) throws NoSuchElementException;

    /**
     * Returns the product at the specified position in this factory line.
     *
     * @param   index index of the product to return
     * @return  the product at the specified position in this factory line
     * @throws  IndexOutOfBoundsException {@inheritDoc}
     */
    Product get(int index) throws IndexOutOfBoundsException;

    /**
     * Insert the {@code product} at the specified position in this factory line.
     * Shifts the products currently at and to the right of that position.
     *
     * @param index index at which the product is to be inserted
     * @param product the product to be inserted
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    void add(int index, Product product) throws IndexOutOfBoundsException;

    /**
     * Removes and returns the product at the specified position in this
     * factory line.
     *
     * @param index index of the product to remove
     * @return the removed {@code product}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    Product removeIndex(int index) throws IndexOutOfBoundsException;

    /**
     * Removes the first occurrence of the {@code product} with the
     * specified {@code value} from this factory line. If this factory
     * line does not contain a product with the specified {@code value},
     * it is unchanged. More formally, removes the {@code product} with the
     * lowest index {@code i} such that {@code product.getValue() == value}.
     *
     * @param value value of the {@code product} to be removed from
     *              this factory line, if present
     * @return the removed {@code product}
     * @throws NoSuchElementException if the product with the given
     *              {@code value} does not exist
     *
     *
     */
    Product removeProduct(int value) throws NoSuchElementException;

    /**
     * Filters the factory line such that every duplicate product is removed.
     * Duplicate products are products with the same value in this context.
     *
     * @return number of removed products
     */
    int filterDuplicates();

    /**
     * Reverses the factory line.
     */
    void reverse();


}
