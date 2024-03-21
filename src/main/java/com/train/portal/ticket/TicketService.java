package com.train.portal.ticket;

import com.train.portal.db.DataStore;
import com.train.portal.exception.UserNotFoundException;
import com.train.portal.ticket.modal.SeatDetails;
import com.train.portal.ticket.modal.UserDetails;
import com.train.portal.utils.TrainUtils;
import com.train.portal.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TicketService {

    private DataStore ds;
    public TicketService() {
        ds = DataStore.getInstance();
    }

    public List<UserDetails> getBookingBySections(String trainName,String section) {

        List<String> passengers = ds.getVacancy().get(trainName).get(section).values().stream().toList();
        return UserUtils.getUserDetailsFromEmails(passengers);
    }

    public UserDetails getBookingDetailsByEmail(String email) {

        if (!ds.getUserDetailsMap().containsKey(email)){
            throw new UserNotFoundException(email + " Not found");
        }

        return ds.getUserDetailsMap().get(email);
    }

    public void updateTicketByEmail(String email,SeatDetails seatDetails) {

        if (!ds.getUserDetailsMap().containsKey(email)){
            throw new UserNotFoundException(email + " Not found");
        }

        if(TrainUtils.isAvailable(seatDetails.getTrainName(), seatDetails.getSection(), seatDetails.getSeatNo())) {

            UserDetails details = ds.getUserDetailsMap().get(email);

            // Delete Existing seat
            String oldTrain = details.getSeatDetails().getTrainName();
            Integer oldSeat = details.getSeatDetails().getSeatNo();
            String oldSection = details.getSeatDetails().getSection();
            ds.getVacancy().get(oldTrain).get(oldSection).remove(oldSeat);

            log.info("Seat {}{} freed!", oldSection, oldSeat);

            // Assign new seat to the user
            ds.getVacancy()
                    .get(seatDetails.getTrainName())
                    .get(seatDetails.getSection())
                    .put(seatDetails.getSeatNo(), email);

            details.setSeatDetails(seatDetails);
        }

        log.info("User {} seat updated to {}{}", email, seatDetails.getSection(), seatDetails.getSeatNo());
    }

    public void deleteTicketByEmail(String email) {

        if (!ds.getUserDetailsMap().containsKey(email)){
            throw new UserNotFoundException(email + " Not found");
        }

        UserDetails details = ds.getUserDetailsMap().get(email);

        // Delete
        String train = details.getSeatDetails().getTrainName();
        Integer seat = details.getSeatDetails().getSeatNo();
        String section = details.getSeatDetails().getSection();

        ds.getVacancy().get(train).get(section).remove(seat);
        ds.getUserDetailsMap().remove(email);

        log.info("User {} deleted successfully, seat {}{} freeed!", email, section, seat);

    }
}
