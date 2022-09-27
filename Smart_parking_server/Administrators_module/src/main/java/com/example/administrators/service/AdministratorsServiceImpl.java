package com.example.administrators.service;

import com.example.administrators.dao.AdministratorsDao;
import com.example.administrators.entity.Administrators;

import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdministratorsServiceImpl  {

    @Resource
    private AdministratorsDao administratorsDao;




    private static final Logger LOGGER= LoggerFactory.getLogger(AdministratorsServiceImpl.class);



    /**
     * TODO：超级管理员登录
     * @param ctr_id 超级管理员账号
     * @param ctr_password 超级管理员密码
     * @return 是否成功
     */
    public String administratorLogin(String ctr_id, String ctr_password) {
        if (ctr_id==null||ctr_password==null){
            return "用户名或密码为空";
        }
        Administrators controller=administratorsDao.findAdm(ctr_id);
        if (controller==null){
            return "用户未注册";
        }
        if (controller.getCtr_password().equals(ctr_password)){
            return "登录成功";
        }else {
            return "密码错误";
        }
    }



    /**
     * TODO：获取用户列表
     * @return 用户列表
     */

    public Object getAllUsers() {
//        return userFeignService.getAllUsers();
        return null;
    }


    /**
     * TODO：获取停车场列表
     * @return 获取停车场列表
     */
    public Object getAllParking() {
//        return parkingLotFeignService.getAllParking();
        return null;
    }


    /**
     * TODO：获取订单列表
     * @return 获取订单列表
     */
    public Object getAllOrder() {
//        return orderFeignService.getAllOrders();
        return null;
    }


    /**
     * TODO：更新停车场信息
     * @param pctr_id 停车场管理员账号
     * @param parking_lot_name 停车场名
     * @param parking_in_the_city 停车场所在城市
     * @param parking_spaces_num 停车场总车位数
     * @param billing_rules 定价
     * @return 是否成功
     */
    public String updateParking(String pctr_id, String parking_lot_name, String parking_in_the_city, Integer parking_spaces_num, float billing_rules) {
//        return parkingLotFeignService.updateParking(pctr_id, parking_lot_name, parking_in_the_city, parking_spaces_num, billing_rules);
        return null;
    }



    /**
     * TODO：超级管理员取消订单
     * @param order_number 订单编号
     * @return 是否成功
     */
    public String cancelOrder( String order_number) {
//        return orderFeignService.cancelOrder(order_number);
        return null;
    }



}
