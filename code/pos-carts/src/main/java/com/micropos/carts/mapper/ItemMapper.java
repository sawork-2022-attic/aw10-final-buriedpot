package com.micropos.carts.mapper;

import com.micropos.api.dto.CartDto;
import com.micropos.api.dto.ItemDto;
import com.micropos.api.dto.ProductDto;
import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface ItemMapper {
    Item toItem(ItemDto itemDto);

    ItemDto toItemDto(Item item);

    Collection<ItemDto> toItemsDto(Collection<Item> items);

    Collection<Item> toItems(Collection<ItemDto> itemsDto);


}
