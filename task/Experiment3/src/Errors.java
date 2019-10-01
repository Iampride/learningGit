public class Errors {
    /** 客户编号*/
    private int custom;
    /** 货物编号*/
    private int item;
    /** 货物数量*/
    private long quantity;

    public void setCustom(int custom){
        this.custom = custom;
    }

    public void setItem(int item){
        this.item = item;
    }

    public void setQuantity(long quantity){
        this.quantity = quantity;
    }

    public int getCustom(){
        return this.custom;
    }

    public int getItem(){
        return this.item;
    }

    public long getQuantity(){
        return this.quantity;
    }
}
