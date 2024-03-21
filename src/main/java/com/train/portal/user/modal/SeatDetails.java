package com.train.portal.user.modal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatDetails {

    private String trainName;
    private String section;
    private Integer seatNo;
}
