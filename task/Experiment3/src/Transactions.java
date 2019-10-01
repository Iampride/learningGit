public class Transactions {
    /** */
    private String dealWay = null;
    /** 货物编号*/
    private int item;
    /** 货物数量*/
    private long quantity;
    /** 客户编号*/
    private int custom;

    /** 供应商编号*/
    private int supplier;

    /** 货物描述*/
    private String description = null;


    /** 发货订单构造函数*/
    public Transactions(int item, long quantity, int custom){
        this.item = item;   this.quantity = quantity;
        this.custom = custom;
    }


    /** 到货订单构造函数*/
    public Transactions(int item, long quantity){
        this.item = item;
        this.quantity = quantity;
    }


    /** 库存增加新货物构造函数*/
    public Transactions(int item, int supplier, String description){
        this.item = item;   this.supplier = supplier;
        this.description = description;
    }


    /** 删除库存货物构造函数*/
    public Transactions(int item){
        this.item = item;
    }



    public void setdealWay(String  dealWay){
        this.dealWay = dealWay;
    }

    public void setItem(int item){
        this.item = item;
    }

    public void setQuantity(long quantity){
        this.quantity = quantity;
    }

    public void setCustom(int custom){
        this.custom = custom;
    }

    public void setSupplier(int supplier){
        this.supplier = supplier;
    }

    public void setDescription(String description){
        this.description = description;
    }


    public String  getdealWay(){
        return this.dealWay;
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

    public int getSupplier(){
        return this.supplier;
    }

    public String getDescription(){
        return this.description;
    }
}
