public class Shipping {
    /** 客户编号*/
    private int custom;
    /** 货物编号*/
    private int item;
    /** 货物数量*/
    private long quantity;


    public void setItem(int item){
        this.item = item;
    }

    public void setQuantity(long quantity){
        this.quantity = quantity;
    }

    public void setCustom(int custom){
        this.custom = custom;
    }

    public int getItem(){
        return item;
    }

    public long getQuantity(){
        return this.quantity;
    }

    public int getCustom(){
        return this.custom;
    }
}
