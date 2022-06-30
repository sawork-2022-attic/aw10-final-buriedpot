package com.micropos.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true, chain = true)
public class CartDto {
    @JsonProperty("cartId")
    private String cartId;

    /*@JsonProperty("accountId")
    private String accountId;*/

    @JsonProperty("items")
    @Valid
    @Getter
    @Setter
    private List<ItemDto> items = new ArrayList<>();


    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }


}
