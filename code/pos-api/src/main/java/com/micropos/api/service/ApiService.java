package com.micropos.api.service;



import com.micropos.api.model.Account;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ApiService {

   /* public void checkout(Cart cart);

    public Cart add(Cart cart, Product product, int amount);

    public Cart add(Cart cart, String productId, int amount);

    public List<Product> products();

    public Product randomProduct();

    public void total(Cart cart);

    public boolean delete(Cart cart, String productId); // delete an item in the cart by productId

    public boolean modify(Cart cart, String productId, int amount); // modify amount of some item in the cart

    public boolean empty(Cart cart);// empty the cart

    public Product getProduct(String id);*/

    public Mono<Account> register(String accountId);

    public Mono<Void> delete(String accountId);
}
