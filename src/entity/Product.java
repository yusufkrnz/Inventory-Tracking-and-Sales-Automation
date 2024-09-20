package entity;

public class Product {

    private int id;
    private String name;
    private String code;
    private int price;
    private int stock;
    private Type Category;
    private String uniqcode;
    private String supplier;

    public Product() {

    }

    public String getSupplier() {
        return this.supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public enum Type {
        SmartAssistants,
        PersonalCare,
        SmartDevices,
        KitchenHelpers
    }

    public Type getCategory() {
        return Category;
    }

    public void setCategory(Type category) {
        Category = category;
    }

    public String getUniqcode() {
        return uniqcode;
    }

    public void setUniqcode(String uniqcode) {
        this.uniqcode = uniqcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", uniqcode='" + uniqcode + '\'' +
                '}';
    }
}