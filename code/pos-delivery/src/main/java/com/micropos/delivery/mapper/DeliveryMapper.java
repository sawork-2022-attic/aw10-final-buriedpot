package com.micropos.delivery.mapper;

import com.micropos.api.dto.DeliveryDto;
import com.micropos.delivery.model.Delivery;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper
public interface DeliveryMapper {
    Delivery toDelivery(DeliveryDto deliveryDto);

    DeliveryDto toDeliveryDto(Delivery delivery);

    Collection<DeliveryDto> toDeliverysDto(Collection<Delivery> deliverys);

    Collection<Delivery> toDeliverys(Collection<DeliveryDto> deliverysDto);


}
