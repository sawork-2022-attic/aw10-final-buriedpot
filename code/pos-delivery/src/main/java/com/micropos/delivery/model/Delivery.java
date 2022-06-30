package com.micropos.delivery.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Data
@Document(collection = "Delivery")
@Accessors(fluent = true, chain = true)
public class Delivery implements Serializable {
    /**
     * same as accountId
     */


    @Id
    @JsonProperty("deliveryId")
    private String deliveryId;

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("currentLocation")
    private String location;

    @JsonProperty("daysLeft")
    private int daysLeft;

    @JsonProperty("items")
    private List<Item> items = new ArrayList<>();


    /*public boolean addItem(Item item) {
        return items.add(item);
    }
*/

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    public double getTotal() {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).quantity() * items.get(i).productPrice();
        }
        return total;
    }




    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean addItem(Item item) {

        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            Item item1 = it.next();
            if (item1.productId().equals(item.productId())) {
                int newAmount = item1.quantity() + item.quantity();
                if (newAmount <= 0) {
                    it.remove();
                }
                else {
                    item1.quantity(newAmount);
                }

                System.out.println("has el: " + this);
                return true;
            }
        }
        if (item.quantity() <= 0) return false;
        System.out.println("new has el: " + this);
        return items.add(item);
    }

    public boolean deleteItem(String productId) {
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (item.productId().equals(productId)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public boolean modifyItem(String productId, int amount) {
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (item.productId().equals(productId)) {
                if (amount > 0) {
                    item.quantity(amount);
                }
                else {
                    it.remove();
                }
                return true;
            }
        }
        return false;
    }

    public boolean modifyItem(Item newItem) {
        Iterator<Item> it = items.iterator();
        String productId = newItem.productId();
        int amount = newItem.quantity();
        while (it.hasNext()) {
            Item item = it.next();
            if (newItem.productId().equals(productId)) {
                if (amount > 0) {
                    newItem.quantity(amount);
                }
                else {
                    it.remove();
                }
                return true;
            }
        }
        return false;
    }

    public boolean empty() {
        items = new ArrayList<>();
        return true;
    }


    @Override
    public String toString() {
        if (items.size() ==0){
            return "Empty Cart";
        }
        double total = 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cart -----------------\n"  );

        for (int i = 0; i < items.size(); i++) {
            stringBuilder.append(items.get(i).toString()).append("\n");
            total += items.get(i).quantity() * items.get(i).productPrice();
        }
        stringBuilder.append("----------------------\n"  );

        stringBuilder.append("Total...\t\t\t" + total );

        return stringBuilder.toString();
    }
}

