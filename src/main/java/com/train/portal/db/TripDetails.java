package com.train.portal.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripDetails {

    private String from;
    private String to;
    private Integer price;
//    <Section, seatsRemaining>
    private Map<String, Integer> vacantSeats;


 }
