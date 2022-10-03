package org.example.api.service;


import org.example.api.entity.order.Order;

import java.util.List;

public interface OrderService {


    List<Order> getAllOrders();


    String cancelOrder(String order_number);


    String  generate_order (String user_name,String license_plate_number,String parking_lot_number,long generation_time);


    Order getOrderByNumber ( String parking_lot_number);


    String complete_Order (String order_number);


    String app_cancellation_Order ( String order_number);


    List<Order> getOrderByUsername (String user_name);


}
