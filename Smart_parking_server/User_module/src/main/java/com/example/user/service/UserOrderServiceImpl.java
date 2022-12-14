package com.example.user.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.example.api.entity.order.Order;
import org.example.api.service.OrderService;
import org.example.api.service.ParkingLotService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserOrderServiceImpl {


    @DubboReference
    private ParkingLotService parkingLotService;

    @DubboReference
    private OrderService orderService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RedisTemplate redisTemplate;


    /**
     * TODO：app开始订单
     * @param user_name 用户名
     * @param license_plate_number 车牌号
     * @param parking_lot_number 停车场编号
     * @return 是否成功
     */
    public String  generate_order (String user_name,String license_plate_number,String parking_lot_number,String UUID){

        String key=UserServiceImpl.md5(user_name+UUID);
        Boolean hasKey = redisTemplate.hasKey(key);
        if (!hasKey){
            return "找不到此用户";
        }
        int num = (int) redisTemplate.opsForValue().get(key);
        if(num==1){
            return "您还有订单未完成，请完成后再预约";
        }
        long l = System.currentTimeMillis();
        String s = orderService.generate_order(user_name, license_plate_number, parking_lot_number,l);
        if (s==null){
            return "错误";
        }
        String s1=user_name + '-' + parking_lot_number + '-' + license_plate_number+'&'+l;
        if (s.equals(s1)){
            rabbitTemplate.convertAndSend("OrderExchange","Timeout",s,setConfirmCallback());
            Long aLong = redisTemplate.opsForValue().increment(key);
            if (aLong==-1){
                return "数据错误-3";
            }
            return "订单 "+s+" 已开始";
        }else {
            return s+" 失败";
        }
    }



    /**
     * TODO：搜索订单
     * @param user_name 用户名
     * @param order_number 订单号
     * @return 是否成功
     */
    public Object findOrder (String user_name,String order_number){
        return orderService.getOrderByNumber(order_number);
    }




    /**
     * TODO：设置RabbitMQ的ConfirmCallback
     */
    public CorrelationData setConfirmCallback (){
        CorrelationData correlationData=new CorrelationData(UUID.randomUUID().toString());
        correlationData.getFuture().addCallback(result -> {
                    if (result.isAck()){
                        //ACK
                        log.info("消息发送成功，id{}",correlationData.getId());
                    }else {
                        //NACK
                        log.error("消息发送失败，id{}，原因{}",correlationData.getId(),result.getReason());
                    }
                 },
                ex -> {
                    log.error("消息发送异常，id{}，原因{}",correlationData.getId(),ex.getMessage());
                });
        return correlationData;
    }



    /**
     * TODO：获取某位用户的订单列表
     * @param user_name 所查找的用户
     * @return 用户订单
     */
    public List<Order> getOrderByUsername(String user_name) {
        return orderService.getOrderByUsername(user_name);
    }


    /**
     * TODO：订单支付完成
     * @param user_name 用户名
     * @param order_number 订单号
     * @return 是否成功
     */
    public String complete_Order (String user_name, String order_number, String UUID){
        String key=UserServiceImpl.md5(user_name+UUID);
        boolean hasKey = redisTemplate.hasKey(key);
        if(!hasKey){
            return "数据错误-1";
        }
        int o = (int) redisTemplate.opsForValue().get(key);
        if (!(o==1)){
            return "数据错误-2";
        }

        rabbitTemplate.convertAndSend("IntegralExchange","addIntegral",user_name,setConfirmCallback());
        String s = orderService.complete_Order( order_number);
        if (s.equals("支付完成")){
            redisTemplate.opsForValue().decrement(key);
        }
        return s;
    }



    /**
     * TODO：app订单取消
     * @param user_name 用户名
     * @param order_number 订单号
     * @return 是否成功
     */
    public String app_cancellation_Order (String user_name,String order_number,String UUID){
        String s = orderService.app_cancellation_Order(order_number);
        if (s.equals("订单已取消")){
            String key=UserServiceImpl.md5(user_name+UUID);
            redisTemplate.opsForValue().decrement(key);
        }
        return s;
    }



    /**
     * TODO：获取当前所在地的停车场情况
     * @param city 所在城市
     * @return 是否成功
     */
    public Object get_parking_lot ( String city){
        return parkingLotService.get_parking_lot(city);
    }

}
