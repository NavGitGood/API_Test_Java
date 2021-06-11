package types;

public class Order {
    private String crust;
    private String flavor;
    private String order_ID;
    private String size;
    private String table_No;
    private String timestamp;

    public Order() {}

    public Order(String crust, String flavor, String order_ID, String size, String table_No, String timestamp) {
        super();
        this.crust = crust;
        this.flavor = flavor;
        this.order_ID = order_ID;
        this.size = size;
        this.table_No = table_No;
        this.timestamp = timestamp;
    }

    public String getCrust() {
        return crust;
    }

    public void setCrust(String crust) {
        this.crust = crust;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getOrder_ID() {
        return order_ID;
    }

    public void setOrder_ID(String order_ID) {
        this.order_ID = order_ID;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTable_No() {
        return table_No;
    }

    public void setTable_No(String table_No) {
        this.table_No = table_No;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Order{" +
                "crust='" + crust + '\'' +
                ", flavor='" + flavor + '\'' +
                ", order_ID='" + order_ID + '\'' +
                ", size='" + size + '\'' +
                ", table_No='" + table_No + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
