package com.kremes.kremeswt.utils;

import com.kremes.kremeswt.entity.Citizen;

/**
 * Created by Bota
 */

public class CitizenUtils {

    public static String formatUsername(String name, String surname) {
        return (name + surname).toLowerCase();
    }

    public static String getCitizenFullName(Citizen citizen) {
        return citizen.getFirstName() + " " + citizen.getLastName();
    }

    public static String formatCitizenPhoneNumer(String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll("\\s+","");
        if(phoneNumber.startsWith("0") && !phoneNumber.startsWith("00"))
            phoneNumber = "+387" + phoneNumber.substring(1);

        return phoneNumber;
    }
}
