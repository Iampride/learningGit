import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.omg.PortableInterceptor.INACTIVE;
import org.omg.PortableInterceptor.TRANSPORT_RETRY;

import java.io.*;
import java.util.*;

public class TotalSystem {
    /** Inventory中货物信息*/
    private List<Inventory> inventoryList;

    /** 各类事务信息*/
    private List<Transactions> transactionsListeventO;
    private List<Transactions> transactionsListeventR;
    private List<Transactions> transactionsListeventA;
    private List<Transactions> transactionsListeventD;



    private void readInventory() throws IOException {
        String path = "/home/nc/Program/Java/task/Experiment3/doc/Inventory.txt";
        BufferedReader fileRead = new BufferedReader(new FileReader(path));
        String line = null;
        inventoryList = new LinkedList<Inventory>();
        int i = 0;
        while( (line = fileRead.readLine() )!= null ){
            //System.out.println(line);
            String [] s = line.split("\t");
            inventoryList.add(new Inventory(Integer.parseInt(s[0]), Long.parseLong(s[1]),
                    Integer.parseInt(s[2]), s[3])) ;
            /*System.out.println(inventoryList.get(i).getItem());
            System.out.println(inventoryList.get(i).getQuantity());
            System.out.println(inventoryList.get(i).getSupplier());
            System.out.println(inventoryList.get(i).getDescription());
            */
            i++;
        }
        fileRead.close();
    }

    private void readTransactions()throws IOException{
        String path = "/home/nc/Program/Java/task/Experiment3/doc/Transactions.txt";
        BufferedReader fileRead = new BufferedReader(new FileReader(path));

        transactionsListeventO = new LinkedList<>();
        transactionsListeventR = new LinkedList<>();
        transactionsListeventA = new LinkedList<>();
        transactionsListeventD = new LinkedList<>();

        String line = null;
        int i = 0;
        while( (line = fileRead.readLine()) != null ){
            String [] s = line.split("\t");
            String dealWay = s[0];
            switch (dealWay){
                case "O":
                    transactionsListeventO.add(new Transactions( Integer.parseInt(s[1]),
                            Long.parseLong(s[2]), Integer.parseInt(s[3]) ));
                    break;
                case "R":
                    transactionsListeventR.add(new Transactions(  Integer.parseInt(s[1]),
                            Long.parseLong(s[2]) ));
                    break;
                case "A":
                    transactionsListeventA.add(new Transactions( Integer.parseInt(s[1]),
                            Integer.parseInt(s[2]), s[3] ) );
                    break;
                case "D":
                    transactionsListeventD.add( new Transactions( Integer.parseInt(s[1]) ) );
                    break;
                default:
                    System.out.println("读取结束");
            }
            i++;
        }
        fileRead.close();
    }

    public void dealEventA()throws IOException{
        for( Transactions t : transactionsListeventA ){
            inventoryList.add( new Inventory(t.getItem(), 0, t.getSupplier(), t.getDescription()) );
        }
    }

    public void dealEventR()throws IOException{
        for(Transactions t : transactionsListeventR){
            for( Inventory inven : inventoryList ){
                if( inven.getItem() == t.getItem() ){
                    inven.addQuantity(t.getQuantity());
                }
            }
        }
    }

    public void dealEventO() throws IOException{
        String path = "/home/nc/Program/Java/task/Experiment3/doc/Shipping.txt";
        BufferedWriter fileWrite = new BufferedWriter(new FileWriter(path, true));
        Collections.sort(transactionsListeventO, new QuantityCompare());
        for(Transactions t : transactionsListeventO){
            String s = t.getItem() + "\t" + t.getQuantity() + "\t" + t.getCustom() + "\n";
            for( Inventory inven : inventoryList ){
                if( inven.getItem() == t.getItem() ){
                    if( inven.getQuantity() >= t.getQuantity() ){
                        inven.decreaseQuantity( t.getQuantity() );
                        String shippingInfo = t.getCustom() + "\t" + t.getItem() + "\t" + t.getQuantity() + "\n";
                        fileWrite.write(shippingInfo, 0, shippingInfo.length());
                    }else{
                        String errorsPath = "/home/nc/Program/Java/task/Experiment3/doc/Errors.txt";
                        BufferedWriter errorsFileWrite = new BufferedWriter( new FileWriter( errorsPath, true ));
                        String errorsInfo = inven.getItem() + "号货物库存量不足,只剩" + inven.getQuantity() + "件." +
                                "不能满足" + t.getCustom() + "号客户" + t.getQuantity() + "件需求." + "\n";
                        errorsFileWrite.write(errorsInfo, 0, errorsInfo.length());
                        errorsFileWrite.close();
                    }
                }
            }
        }
        fileWrite.close();
    }

    public void dealEventD() throws IOException{
        for(Transactions t : transactionsListeventD){
            for(Inventory inven : inventoryList){
                if(t.getItem() == inven.getItem()){
                    if(inven.getQuantity() > 0 ){
                        String errorsPath = "/home/nc/Program/Java/task/Experiment3/doc/Errors.txt";
                        BufferedWriter errorsFileWrite = new BufferedWriter( new FileWriter( errorsPath, true ));
                        String errorsInfo = inven.getItem() + "号货物库存量不为0, 不能删除货物" + "\n";
                        errorsFileWrite.write(errorsInfo, 0, errorsInfo.length());
                        errorsFileWrite.close();
                    }else{
                        inventoryList.remove(t);
                    }
                }
            }
        }
    }

    public void saveInfoToNewInventory()throws IOException {
        String NewInventoryFilePath = "/home/nc/Program/Java/task/Experiment3/doc/NewInventory.txt";
        BufferedWriter newInventoryFileWrite = new BufferedWriter( new FileWriter(NewInventoryFilePath, true) );
        for( Inventory inven : inventoryList ){
            String upDateInvenInfo = inven.getItem() + "\t" + inven.getQuantity() + "\t" +
                    inven.getSupplier() + "\t" +inven.getDescription() + "\n";
            newInventoryFileWrite.write(upDateInvenInfo, 0, upDateInvenInfo.length());
        }
        newInventoryFileWrite.close();
    }



    public TotalSystem() throws IOException{
        readInventory();
        readTransactions();
        dealEventA();
        dealEventR();
        dealEventO();
        dealEventD();
        saveInfoToNewInventory();
    }

    /**
     * 此类根据transactionsEventO中的quantity属性进行从小到大排序,
     * 以便处理发货事件时, 对于同一类货物可以按需求从小到大满足
     * */
    private class QuantityCompare implements Comparator<Transactions> {

        @Override
        public int compare(Transactions t1, Transactions t2){
            if( t1.getQuantity() < t2.getQuantity() ){
                return -1;
            }else if( t1.getQuantity() == t2.getQuantity() ){
                return 0;
            }else{
                return 1;
            }
        }

    }
}
