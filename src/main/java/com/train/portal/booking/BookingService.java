package com.train.portal.booking;

import com.train.portal.booking.modal.BookRequest;
import com.train.portal.db.DataStore;
import com.train.portal.exception.UserAlreadyExistsException;
import com.train.portal.ticket.modal.SeatDetails;
import com.train.portal.ticket.modal.UserDetails;
import com.train.portal.utils.TrainUtils;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private DataStore ds;
    public BookingService() {
        ds = DataStore.getInstance();
    }

    public void bookTicket(BookRequest bookRequest) {

        String trainName=TrainUtils.getTrainName(bookRequest.getFrom(),bookRequest.getTo());

        // Check the seat availability
        if(TrainUtils.isAvailable(trainName,bookRequest.getSection(),bookRequest.getSeatNo())) {
            // Add User to User Map
            if(ds.getUserDetailsMap().containsKey(bookRequest.getPEmail())) {
                throw new UserAlreadyExistsException(bookRequest.getPEmail() + " User Already exists, Use different email");
            }

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

}
