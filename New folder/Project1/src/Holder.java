
public class Holder {

    private Holder nextHolder;

    private Holder previousHolder;

    private Product product;

    public Holder(Holder previousHolder, Product product, Holder nextHolder) {
        this.nextHolder = nextHolder;
        this.previousHolder = previousHolder;
        this.product = product;
    }

    public Holder getNextHolder() {
        return nextHolder;
    }

    public void setNextHolder(Holder nextHolder) {
        this.nextHolder = nextHolder;
    }

    public Holder getPreviousHolder() {
        return previousHolder;
    }

    public void setPreviousHolder(Holder previousHolder) {
        this.previousHolder = previousHolder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String toString() {
        return (product == null) ? "Holder empty." : product.toString() ;
    }

    @SuppressWarnings("EqualsReplaceableByObjectsCall")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Holder holder = (Holder) o;

        if (nextHolder != null ? !nextHolder.equals(holder.nextHolder) : holder.nextHolder != null) return false;
        if (previousHolder != null ? !previousHolder.equals(holder.previousHolder) : holder.previousHolder != null)
            return false;
        return product != null ? product.equals(holder.product) : holder.product == null;
    }
}
