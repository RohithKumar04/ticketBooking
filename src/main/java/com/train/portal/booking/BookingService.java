package com.train.portal.booking;

import com.train.portal.exception.SeatAlreadyBookedException;
import com.train.portal.booking.modal.BookRequest;
import com.train.portal.db.DataStore;
import com.train.portal.user.modal.SeatDetails;
import com.train.portal.user.modal.UserDetails;
import com.train.portal.utils.TrainUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BookingService {

    private DataStore ds;
    public BookingService() {
        ds = DataStore.getInstance();
    }

    public void bookTicket(BookRequest bookRequest) {

        String trainName=TrainUtils.getTrainName(bookRequest.getFrom(),bookRequest.getTo());

        // Check the seat availability
        if(isAvailable(trainName,bookRequest.getSection(),bookRequest.getSeatNo())) {
            // Add User to User Map
            UserDetails user=UserDetails.builder()
                    .email(bookRequest.getPEmail())
                    .firstName(bookRequest.getPFirstName())
                    .lastName(bookRequest.getPLastName())
                    .seatDetails(SeatDetails.builder()
                            .seatNo(bookRequest.getSeatNo())
                            .section(bookRequest.getSection())
                            .trainName(trainName)
                            .build())
                    .build();

            ds.getUserDetailsMap().put(bookRequest.getPEmail(),user);

            // Assign the seat to the user
            ds.getVacancy()
                    .get(trainName)
                    .get(bookRequest.getSection())
                    .put(bookRequest.getSeatNo(),bookRequest.getPEmail());
        }

    }


    public Map<String, Map<String, Map<Integer, String>>> getAllBooking() {

        return ds.getVacancy();
    }

    private boolean isAvailable(String trainName, String section, Integer seatNo) {

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
