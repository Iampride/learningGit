public class NewInventory {
    private int item;
    private long quantity;
    private int supplier;
    private String description;

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
}
