package org.example.api.service;


import org.example.api.entity.parkingLots.Parking;
import org.example.api.entity.parkingLots.Parking_for_user;

import java.util.List;


public interface ParkingLotService {

    List<Parking> getAllParking();


    String getParkingName (String parking_lot_number );

    String getParkingBilling_rules ( String parking_lot_number );

    List<Parking_for_user>  get_parking_lot ( String city);

    List<Parking_for_user> getParkingLot (String parking_lot_name,String city);


    String updateParking( String pctr_id,
                          String parking_lot_number,
                          String parking_in_the_city,
                          Integer parking_spaces_num,
                          float billing_rules);


    int  findParkingLot (String parking_lot_number);

     List<Parking_for_user> peripheralParking( String latitude,
                                               String longitude,
                                               String city);
}
