public class Inventory {
    private int item;
    private long quantity = 0;
    private int supplier;
    private String description;


    public Inventory(){}
    public Inventory(int item, long quantity, int supplier, String description){
        this.item = item;   this.quantity = quantity;
        this.supplier = supplier;   this.description = description;
    }

    public void setItem(int item){
        this.item = item;
    }

    public void setQuantity(long quantity){
        this.quantity = quantity;
    }

    public void setSupplier(int supplier){
        this.supplier = supplier;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public int getItem(){
        return this.item;
    }

    public long getQuantity(){
        return this.quantity;
    }

    public int getSupplier(){
        return this.supplier;
    }

    public String getDescription(){
        return this.description;
    }

    public void addQuantity(Long quantity){
        this.quantity = this.quantity + quantity;
    }

    public void decreaseQuantity( Long quantity){
        this.quantity = this.quantity - quantity;
    }

}
