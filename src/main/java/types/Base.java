package types;

import java.util.ArrayList;
import java.util.List;

public class Base {

    private String url;
    private List<Order> orders;

    public Base() {}

    public Base(String url, List<Order> orders) {
        super();
        this.url = url;
        this.orders = orders;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Base{" +
                "url='" + url + '\'' +
                ", orders=" + orders +
                '}';
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Order> getOrders() {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        return orders;
    }

    public void setOrders(List<Order> orders) {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        this.orders = orders;
    }
}