
public class Product {

    //Unique identifier
    private final int id;

    private Integer value;

    public Product(int id, Integer value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String toString() {
        return "(" + id + ", " + value + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id == product.id;
    }

}
