package org.example.api.service;

import java.util.List;

public interface VehicleService {

    int check_license_plate_number (String user_name, String license_plate_number);

    String  add_Vehicle ( String user_name,
                              String user_id,
                              String license_plate_number,
                              String vehicle_photos,
                              String registration,
                              String driving_permit);


    String delete_User_Vehicle (String user_name, String license_plate_number);


    String delete_All_Vehicle (String user_name);


    List<String> getUserVehicle (String user_name);

}
