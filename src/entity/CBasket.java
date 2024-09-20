package entity;

public class CBasket {


    private int id;
    private int productId;
    private Product product;


    public CBasket() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "CBasket{" +
                "id=" + id +
                ", productId=" + productId +
                ", product=" + product +
                '}';
    }
}
