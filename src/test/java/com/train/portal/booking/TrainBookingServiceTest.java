package com.train.portal.booking;

import com.train.portal.TestDataStore;
import com.train.portal.booking.modal.BookRequest;
import com.train.portal.db.DataStore;
import com.train.portal.db.TripDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainBookingServiceTest {

    private TrainBookingService trainBookingService;

    @Test
    void bookTicket() {

        BookRequest request=new BookRequest();
        request.setPEmail("a@g.com");
        request.setPFirstName("first");
        request.setPLastName("last");
        request.setFrom("from");
        request.setTo("to");
        request.setSection("A");
        request.setSeatNo(5);

        DataStore mock = mock(DataStore.class);

        try (MockedStatic<DataStore> cacheManagerMock = mockStatic(DataStore.class)) {

            cacheManagerMock.when(DataStore::getInstance).thenReturn(mock);
            TestDataStore.mockDataStore(mock);

            trainBookingService = new TrainBookingService();
            trainBookingService.bookTicket(request);

            Assertions.assertEquals(mock.getUserDetailsMap().size(), 1);

            Map<Integer, String> seatsFilled=mock.getVacancy().get("from-to").get("A");
            Assertions.assertTrue(seatsFilled.containsKey(5));
            assertEquals("a@g.com",seatsFilled.get(5));

        }


    }
}