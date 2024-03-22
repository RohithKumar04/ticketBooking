package com.train.portal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.train.portal.db.DataStore;
import com.train.portal.db.TripDetails;
import com.train.portal.ticket.modal.UserDetails;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class TestDataStore {

    // <Trip Name, details>
    private Map<String, TripDetails> trips;

    // <user email, User details>
    private Map<String, UserDetails> userDetailsMap;

    // <Trip, <Section, <seatNo, email>>
    private Map<String, Map<String, Map<Integer, String>>> vacancy=new HashMap<>();

    public TestDataStore() {

        userDetailsMap=new HashMap<>();
        TripDetails t=TripDetails.builder()
                .from("from")
                .to("to")
                .price(5)
                .vacantSeats(Map.of("A",5,"B",5))
                .build();

        trips.put("from-to",t);

        t.getVacantSeats().keySet()
                .forEach(
                        s -> vacancy.get("from-to").put(s,new HashMap<>())
                );
    }

    public static DataStore mockDataStore(DataStore mock) {

        when(mock.getUserDetailsMap()).thenReturn(new HashMap<String, UserDetails>());

        TripDetails t=TripDetails.builder()
                .from("from")
                .to("to")
                .price(5)
                .vacantSeats(Map.of("A",5,"B",5))
                .build();
        when(mock.getTrips()).thenReturn(Map.of("from-to",t));

        Map<String, Map<Integer, String>> sections=new HashMap<>();
        sections.put("A", new HashMap<>());
        sections.put("B", new HashMap<>());

        Map<String, Map<String, Map<Integer, String>>> vacancies=Map.of("from-to",sections);
        when(mock.getVacancy()).thenReturn(vacancies);

        return mock;
    }
}
