package com.micropos.delivery.rest;


import com.micropos.api.dto.DeliveryDto;
import com.micropos.api.dto.ItemDto;
import com.micropos.delivery.mapper.DeliveryMapper;
import com.micropos.delivery.mapper.ItemMapper;
import com.micropos.delivery.model.Delivery;
import com.micropos.delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("delivery")
public class DeliveryController {

    private DeliveryService deliveryService;

    private DeliveryMapper deliveryMapper;
    private ItemMapper itemMapper;

    public DeliveryController(DeliveryService deliveryService){
        this.deliveryService = deliveryService;
    }


    @Autowired
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Autowired
    public void setItemMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    @Autowired
    public DeliveryController(DeliveryService deliveryService, DeliveryMapper deliveryMapper) {
        this.deliveryService = deliveryService;
        this.deliveryMapper = deliveryMapper;
    }


/*    @PostMapping("/delivery/{deliveryId}")
    public Mono<ResponseEntity<DeliveryDto>> addItemToDelivery(@PathVariable("deliveryId") String deliveryId, @RequestBody ItemDto itemDto) {
        Mono<Delivery> deliveryMono = deliveryService.getDelivery(deliveryId);
        if(deliveryMono == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Delivery ret = deliveryService.add(delivery, item);
        if(ret == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }*/

    @GetMapping("/delivery/{deliveryId}")
    public Mono<DeliveryDto> showDeliveryById(@PathVariable("deliveryId") String deliveryId) {

        return deliveryService.getDelivery(deliveryId).map(deliveryMapper::toDeliveryDto);
    }
/*
    @DeleteMapping("/delivery/{deliveryId}")
    public ResponseEntity<DeliveryDto> deleteItemFromDelivery(@PathVariable("deliveryId") Integer deliveryId, @RequestParam String productId) {
        Delivery delivery = deliveryService.getDelivery(deliveryId).orElse(null);
        if(delivery == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Delivery ret = deliveryService.delete(delivery, productId);
        if(ret == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(deliveryMapper.toDeliveryDto(ret),HttpStatus.OK);
    }

    *//**
     * modify item num

     * @return
     *//*
    @PutMapping("/delivery/{deliveryId}")
    public ResponseEntity<DeliveryDto> modifyItemInDelivery(@PathVariable("deliveryId") int deliveryId, @RequestBody Item item) {
        Delivery delivery = deliveryService.getDelivery(deliveryId).orElse(null);
        if(delivery == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Delivery ret = deliveryService.add(delivery, item);
        if(ret == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(deliveryMapper.toDeliveryDto(ret), HttpStatus.OK);
    }*/



    /*@GetMapping("/checkoutByAccountId/{accountId}")
    public Mono<ResponseEntity<Double>> checkoutByAccountId(@PathVariable("accountId") String accountId) {
        return deliveryService.checkoutByAccoundId(accountId).map(ret -> new ResponseEntity<>(ret, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/

/*
    @Override
    @PostMapping("/delivery")
    public ResponseEntity<DeliveryDto> createDelivery(@RequestBody DeliveryDto deliveryDto) {
        Delivery ret = deliveryMapper.toDelivery(deliveryDto);

        if(ret == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ret = deliveryService.addDelivery(ret);

        if (ret == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deliveryMapper.toDeliveryDto(ret),HttpStatus.OK);
    }
*/

    @GetMapping(value = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<DeliveryDto> streamListDeliverys() {

        return deliveryService.getAllDelivery().map(delivery -> deliveryMapper.toDeliveryDto(delivery));
    }

    public Flux<DeliveryDto> listDeliverys() {

        return deliveryService.getAllDelivery().map(delivery -> deliveryMapper.toDeliveryDto(delivery));
    }

/*
    @Override
    @GetMapping("/delivery/{deliveryId}")
    public ResponseEntity<DeliveryDto> showDeliveryById(@PathVariable("deliveryId") Integer deliveryId) {

        Delivery delivery = deliveryService.getDelivery(deliveryId).orElse(null);
        if(delivery == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        DeliveryDto deliveryDto = deliveryMapper.toDeliveryDto(delivery);
        return new ResponseEntity<>(deliveryDto,HttpStatus.OK);
    }

    @Override
    @GetMapping("/delivery/{deliveryId}/total")
    public ResponseEntity<Double> showDeliveryTotal(@PathVariable("deliveryId") Integer deliveryId) {
        double totalAmount =  deliveryService.checkout(deliveryId);
        if(deliveryId == -1d)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(totalAmount);
    }

    //@Override
    @GetMapping("/delivery/{deliveryId}/empty")
    public ResponseEntity<DeliveryDto> emptyDelivery(@PathVariable("deliveryId") Integer deliveryId) {

        Delivery delivery = deliveryService.getDelivery(deliveryId).orElse(null);
        if(delivery == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        delivery.empty();
        DeliveryDto deliveryDto = deliveryMapper.toDeliveryDto(delivery);
        return new ResponseEntity<>(deliveryDto,HttpStatus.OK);
    }

    @Override
    @PutMapping("/delivery/empty")
    public ResponseEntity<DeliveryDto> emptyDelivery(@RequestBody DeliveryDto deliveryDto) {

        Delivery delivery = deliveryService.empty(deliveryMapper.toDelivery(deliveryDto));
        if(delivery == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(deliveryMapper.toDeliveryDto(delivery));
    }*/

}
