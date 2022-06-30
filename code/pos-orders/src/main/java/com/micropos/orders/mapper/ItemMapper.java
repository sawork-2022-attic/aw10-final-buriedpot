package com.micropos.orders.mapper;

import com.micropos.api.dto.ItemDto;
import com.micropos.orders.model.Item;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper
public interface ItemMapper {
    Item toItem(ItemDto itemDto);

    ItemDto toItemDto(Item item);

    Collection<ItemDto> toItemsDto(Collection<Item> items);

    Collection<Item> toItems(Collection<ItemDto> itemsDto);


}
