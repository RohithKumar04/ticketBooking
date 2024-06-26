package com.train.portal.ticket.modal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetails {

    private String firstName;
    private String lastName;
    private String email;

    private SeatDetails seatDetails;
}
