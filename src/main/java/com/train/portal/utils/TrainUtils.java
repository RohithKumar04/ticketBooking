package com.train.portal.utils;

import com.train.portal.db.DataStore;
import com.train.portal.exception.SeatAlreadyBookedException;

public class TrainUtils {

    public static String getTrainName(String from, String to) {
        return from+"-"+to;
    }

    public static boolean isAvailable(String trainName,String section,Integer seatNo) {

        DataStore ds = DataStore.getInstance();

        if(!ds.getVacancy().containsKey(trainName)) {
            throw new SeatAlreadyBookedException("Invalid Train Name: " + trainName);
        }

        if(!ds.getVacancy().get(trainName).containsKey(section)) {
            throw new SeatAlreadyBookedException("Invalid Section Name: " + section);
        }

        if(ds.getVacancy().get(trainName).get(section).containsKey(seatNo)) {
            throw new SeatAlreadyBookedException(String.format("%s seat already booked", seatNo));
        }
        return true;

    }
}
