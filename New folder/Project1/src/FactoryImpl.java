

import java.util.NoSuchElementException;
import java.util.HashSet;
public class FactoryImpl implements  Factory{

    private Holder first;
    private Holder last;
    private Integer size;

    public FactoryImpl(){
        first = null;
        last = null;
        size = 0;
    }
    @Override
    public void addFirst(Product product) {
        Holder newHolder = new Holder(null, product, null);
        if(size != 0){
            first.setPreviousHolder(newHolder);
            newHolder.setNextHolder(first);
            first = newHolder;
        }
        else{
            last = newHolder;
            first = newHolder;
        }
        size+= 1;

    }

    @Override
    public void addLast(Product product) {
        Holder newHolder = new Holder(null,product,null);
        if(size == 0){
            first = newHolder;
            last = newHolder;
        }
        else{
            last.setNextHolder(newHolder);
            newHolder.setPreviousHolder(last);
            last = newHolder;
        }

        size++;

    }

    @Override
    public Product removeFirst() throws NoSuchElementException {
        if(size == 0) throw new NoSuchElementException();
        Product oldProduct = first.getProduct();
        if(size > 1){
            Holder newFirst = first.getNextHolder();
            first.setNextHolder(null);

            first = newFirst;
            first.setPreviousHolder(null);
        }
        else{
            first = null;
            last = null;
        }
        size--;
        return oldProduct;
    }

    @Override
    public Product removeLast() throws NoSuchElementException {
        if(size == 0) throw new NoSuchElementException();
        Product oldProduct = last.getProduct();
        if(size > 1){
            Holder newLast = last.getPreviousHolder();
            last.setPreviousHolder(null);
            last = newLast;
            last.setNextHolder(null);
        }
        else{
            first = null;
            last = null;
        }
        size--;
        return oldProduct;
    }

    @Override
    public Product find(int id) throws NoSuchElementException {
        Holder cur = first;
        while(cur != null){
            if(id == cur.getProduct().getId()){
                return cur.getProduct();
            }
            cur = cur.getNextHolder();
        }
        throw new NoSuchElementException();

    }

    @Override
    public Product update(int id, Integer value) throws NoSuchElementException {
        Holder cur = first;
        while(cur != null){
            Product product = cur.getProduct();
            if(id == product.getId()){
                 Product oldProduct = new Product(id, product.getValue());
                 product.setValue(value);
                 return oldProduct;
            }
            cur = cur.getNextHolder();
        }

        throw new NoSuchElementException();
    }

    @Override
    public Product get(int index) throws IndexOutOfBoundsException {
        if(index >= size || index < 0 ){
            throw new IndexOutOfBoundsException();
        }
        Holder cur = first;
        int i = 0;
        while(i != index){
            cur = cur.getNextHolder();
            i++;
        }
        return cur.getProduct();
    }

    @Override
    public void add(int index, Product product) throws IndexOutOfBoundsException {
        if(index > size || index < 0){
            throw new IndexOutOfBoundsException();
        }
        if(index == 0){
            addFirst(product);
            return;
        }
        if(index == size){
            addLast(product);
            return;
        }
        int i = 0;
        Holder cur = first;
        while(i != index){
            cur = cur.getNextHolder();
            i++;
        }

        Holder prev = cur.getPreviousHolder();

        Holder newHolder = new Holder(prev,product, cur);

        prev.setNextHolder(newHolder);
        cur.setPreviousHolder(newHolder);

        size++;
    }

    @Override
    public Product removeIndex(int index) throws IndexOutOfBoundsException {
        if(index >= size || index < 0){
            throw new IndexOutOfBoundsException();
        }
        if(index == 0) return removeFirst();
        if(index == size - 1) return removeLast();
        Holder cur = first;
        int i = 0;
        while(i != index){
            cur = cur.getNextHolder();
            i++;
        }
        return remove(cur);

    }

    @Override
    public Product removeProduct(int value) throws NoSuchElementException {
        Holder cur = first;
        int i = 0;
        while(cur != null){
            if(cur.getProduct().getValue() == value){
                if(i == 0) return removeFirst();
                if(i == size - 1) return removeLast();
                return remove(cur);
            }
            cur = cur.getNextHolder();
            i++;
        }
        throw new NoSuchElementException();
    }
    private Product remove(Holder cur){
        Holder prev = cur.getPreviousHolder();
        Holder next = cur.getNextHolder();
        if(prev != null)
            prev.setNextHolder(next);
        if(next != null)
            next.setPreviousHolder(prev);
        cur.setPreviousHolder(null);
        cur.setNextHolder(null);
        size--;
        return cur.getProduct();
    }
    @Override
    public int filterDuplicates() {
        HashSet<Integer> set = new HashSet<Integer>();
        Holder cur = first;
        int numOfRemoved = 0;
        int i = 0;
        while(cur != null){
            if(set.contains(cur.getProduct().getValue())){
                cur = cur.getNextHolder();
                removeIndex(i);
                numOfRemoved++;
                continue;
            }
            else{
                set.add(cur.getProduct().getValue());
            }

            cur = cur.getNextHolder();
            i++;
        }
        return numOfRemoved;
    }

    @Override
    public void reverse() {
        Holder temp = null;
        Holder cur = first;
        while(cur != null){
            temp = cur.getPreviousHolder();
            cur.setPreviousHolder(cur.getNextHolder());
            cur.setNextHolder(temp);
            cur = cur.getPreviousHolder();
        }
        temp = first;
        first = last;
        last = temp;


    }

    @Override
    public String toString() {
        StringBuilder factoryString = new StringBuilder("{");
        Holder cur = first;
        while(cur != null){
           factoryString.append(cur.getProduct().toString());

           cur = cur.getNextHolder();
           if(cur != null) factoryString.append(",");
        }

        return factoryString + "}";
    }
}