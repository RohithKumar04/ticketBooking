package com.train.portal.booking.modal;

import lombok.Data;

@Data
public class BookRequest {

    private String pFirstName;
    private String pLastName;
    private String pEmail;
    private String from;
    private String to;
    private String section;
    private Integer seatNo;

}
