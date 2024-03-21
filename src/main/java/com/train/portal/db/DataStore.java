package com.train.portal.db;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.train.portal.user.modal.UserDetails;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class DataStore {

    // <Trip Name, details>
    private Map<String, TripDetails> trips;

    // <user email, User details>
    private Map<String, UserDetails> userDetailsMap=new HashMap<>();

    // <Trip, <Section, <seatNo, email>>
    private Map<String, Map<String, Map<Integer, String>>> vacancy=new HashMap<>();

    private static DataStore db;

    private DataStore() {
    }

    public static DataStore getInstance() {

        if (db == null) {
            db=new DataStore();
            db.userDetailsMap=new HashMap<>();
            try {

                File file=new ClassPathResource("masterdata/tripDetails.json").getFile();
                ObjectMapper om=new ObjectMapper();
                db.trips=om.readValue(file,new TypeReference<>() {
                });

                db.trips.forEach((tripId,tripDetails) -> {

                    db.vacancy.put(tripId,new HashMap<>());

                    tripDetails.getVacantSeats().keySet()
                            .forEach(
                                    s -> db.vacancy.get(tripId).put(s,new HashMap<>())
                            );
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return db;
    }
}
