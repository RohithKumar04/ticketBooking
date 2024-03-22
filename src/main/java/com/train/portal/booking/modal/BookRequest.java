package com.train.portal.booking.modal;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookRequest {

    @NotNull(message="Name cannot be empty")
    private String pFirstName;
    private String pLastName;

    @Email(regexp="^\\S+@\\S+$", message="Not a valid Email")
    private String pEmail;

    @NotNull
    private String from;
    @NotNull
    private String to;
    @NotNull
    private String section;
    @NotNull
    private Integer seatNo;

}
