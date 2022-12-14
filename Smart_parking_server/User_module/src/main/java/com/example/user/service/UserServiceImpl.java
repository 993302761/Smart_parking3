package com.example.user.service;


import com.example.user.dao.UserDao;
import com.example.user.entity.User_information;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.api.entity.parkingLots.Parking_for_user;
import org.example.api.entity.user.User;
import org.example.api.service.ParkingLotService;
import org.example.api.service.UserService;
import org.example.api.service.VehicleService;
import org.example.saltfish.service.MinioServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@DubboService
public class UserServiceImpl  implements UserService {

    @Resource
    private UserDao userDao;


    @DubboReference
    private ParkingLotService parkingLotService;

    @DubboReference
    private VehicleService vehicleService;


    @Resource
    private MinioServiceImpl minioService;

    @Resource
    private RedisTemplate redisTemplate;





    /**
     * TODO：添加一名用户
     * @param user_name 用户名
     * @param password 密码
     * @param user_id 身份证号码
     * @param license_plate_number 车牌号
     * @param vehicle_photos 车辆照片
     * @param registration 机动车登记证照片
     * @param driving_permit 车辆行驶证照片
     * @return 是否成功
     */
    public String add_User(String user_name,
                           String password,
                           String user_id,
                           String license_plate_number,
                           MultipartFile vehicle_photos,
                           MultipartFile registration,
                           MultipartFile driving_permit) throws IOException {

        StringBuilder s=new StringBuilder("用户:");

        if (user_name==null||password==null||user_id==null){
            return "所填信息不完整";
        }
        int user = userDao.check_User(user_name);
        if (user==1){
            return "用户已注册";
        }else if (user!=0){
            return "数据错误";
        }
        int i= userDao.add_User(user_name,password,user_id);
        if (i<=0){
            return "注册失败";
        }
        else {
            s.append("注册成功  ");
        }
        s.append("车辆信息：");


        try {
            String vehicle=vehicle_binding(user_name,user_id,license_plate_number,vehicle_photos,registration,driving_permit);
            s.append(vehicle);
            return s.toString();
        }catch (Exception e){
            userDao.delete_User(user_name);
            e.printStackTrace();
            return "错误";
        }



    }






    /**
     * TODO：添加绑定车辆
     * @param user_name 用户名
     * @param user_id 身份证号码
     * @param license_plate_number 车牌号
     * @param vehicle_photos 车辆照片
     * @param registration 机动车登记证照片
     * @param driving_permit 车辆行驶证照片
     * @return 是否成功
     */
    public String vehicle_binding (String user_name,
                                   String user_id,
                                   String license_plate_number,
                                   MultipartFile vehicle_photos,
                                   MultipartFile registration,
                                   MultipartFile driving_permit){

        int vehicleNumber = vehicleService.check_license_plate_number(user_name, license_plate_number);
        if (vehicleNumber==1){
            return "该车辆已注册，请勿重复注册";
        }else if (vehicleNumber>1){
            return "数据错误";
        }else if (vehicleNumber<0){
            return "访问错误";
        }

        String vehicle_photos_address = minioService.addVehicleFile(user_name,license_plate_number+"-1",vehicle_photos);
        String registration_address = minioService.addVehicleFile(user_name,license_plate_number+"-2",registration);
        String driving_permit_address = minioService.addVehicleFile(user_name,license_plate_number+"-3",driving_permit);
        if (vehicle_photos_address==null||registration_address==null||driving_permit_address==null){
            return "照片保存错误";
        }
        try {
            String s = vehicleService.add_Vehicle(user_name, user_id, license_plate_number, vehicle_photos_address.replace('/', '&'), registration_address.replace('/', '&'), driving_permit_address.replace('/', '&'));
            return s;
        }catch (Exception e){
            minioService.deleteVehicleFile(user_name,license_plate_number+"-1");
            minioService.deleteVehicleFile(user_name,license_plate_number+"-2");
            minioService.deleteVehicleFile(user_name,license_plate_number+"-3");
            e.printStackTrace();
            return "绑定信息失败";
        }
    }





    /**
     * TODO：删除绑定的车辆信息
     * @param user_name 用户名
     * @param license_plate_number 车牌号
     * @return 是否成功
     */
    public String deleteVehicle (String user_name, String license_plate_number){
        return vehicleService.delete_User_Vehicle(user_name,license_plate_number);
    }





    /**
     * TODO：获取用户绑定的车辆信息
     * @param user_name 用户名
     * @return 是否成功
     */
    public List<String> getUserVehicle (String user_name){
        return vehicleService.getUserVehicle(user_name);
    }







    /**
     * TODO：用户登录
     * @param user_name 用户名
     * @param password 密码
     * @param UUID 通用唯一识别码
     * @return 是否成功
     */
    public String login_User(String user_name, String password,String UUID) {
        if (user_name==null||password==null){
            return "用户名或密码为空";
        }
        User_information user=userDao.find_User(user_name);
        if (user==null){
            return "用户未注册";
        }
        if (user.getPassword().equals(password)){
            set_UUID(UUID,user_name);
            boolean b = set_UUID(UUID, user_name);
            if (!b){
                System.out.println(user_name+"：Redis设置失败");
            }
            return "登录成功";
        }else {
            return "密码错误";
        }
    }





    /**
     * TODO：查找用户
     * @param user_name 用户名
     * @return 是否成功
     */
    public boolean find(String user_name){
        int user = userDao.check_User(user_name);
        if (user==0){
            return true;
        }
        else return false;
    }




    /**
     * TODO：查找停车场
     * @param parking_lot_name 停车场名
     * @param city 当前所在城市
     */
    public Object getParkingLot (String parking_lot_name,String city){
        return parkingLotService.getParkingLot(parking_lot_name,city);
    }





    /**
     * TODO：获取用户身份证
     * @param user_name 用户名
     * @return 用户身份证
     */
    public String getUserId(String user_name){
        return userDao.getUserId(user_name);
    }






    /**
     * TODO：UUID校验
     * @param user_name 用户名
     * @param UUID 通用唯一识别码
     * @return 是否成功
     */
    public boolean check_UUID(String UUID,String user_name){
        String key=md5(user_name+UUID);
        boolean hasKey = redisTemplate.hasKey(key);
        if(hasKey){
            return true;
        } else {
            return false;
        }
    }


    /**
     * TODO：Md5加密（加盐）
     * @param data 字符串
     * @return 加密后的编码
     */
    public static String md5(String data){
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest messageDigest=MessageDigest.getInstance("md5");
            byte[] digest = messageDigest.digest(data.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b:digest){
                int num=b&0xff;
                String s=Integer.toHexString(num);
                if (s.length()==1){
                    stringBuffer.append("0");
                }
                stringBuffer.append(s);
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * TODO：UUID设置
     * @param user_name 用户名
     * @param UUID 通用唯一识别码
     * @return 是否成功
     */
    public boolean set_UUID(String UUID,String user_name){
        //设置过期时间为一个月
//        Calendar curDate = Calendar.getInstance();
//        Calendar nextDate = new GregorianCalendar(curDate.get(Calendar.YEAR),
//                curDate.get(Calendar.MONTH) + 1,
//                curDate.get(Calendar.DAY_OF_MONTH),
//                curDate.get(Calendar.HOUR_OF_DAY),
//                curDate.get(Calendar.MINUTE),
//                curDate.get(Calendar.SECOND));
//        long second = (nextDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000;
        String key=md5(user_name+UUID);
        redisTemplate.opsForValue().set(key, 0,15,TimeUnit.DAYS);
//        Boolean expire = redisTemplate.expire(key, second, TimeUnit.SECONDS);
        return true;
    }






    /**
     * TODO：获取用户列表
     * @return 用户列表
     */
    public List<User> getAllUsers() {
        List<User> users = userDao.getAllUsers();
        List<User> newUsers=new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            List<String> vehicle=vehicleService.getUserVehicle(users.get(i).getUser_name());
            User t=users.get(i);
            t.setVehicle(vehicle);
            newUsers.add(t);
        }
        return newUsers;
    }





    /**
     * TODO：删除用户
     */
    public String delete_User (String user_name,String UUID){
        userDao.delete_User(user_name);
        String key=md5(user_name+UUID);
        redisTemplate.delete(key);
        return vehicleService.delete_All_Vehicle(user_name);
    }




    /**
     * TODO：获取周边所有停车场
     * @param latitude 维度
     * @param longitude 经度
     * @param city 所在城市
     * @return 停车场列表
     */
    public List<Parking_for_user> peripheralParking(String latitude, String longitude, String city) {
        return parkingLotService.peripheralParking(latitude, longitude, city);
    }

}
