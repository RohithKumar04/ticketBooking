package com.train.portal.booking;


import com.train.portal.booking.modal.BookRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService=bookingService;
    }

//        {
//        "from": "LON",
//            "to": "BER",
//            "section": "A",
//            "seatNo": 5,
//            "pemail": "abc",
//            "pfirstName": "rohith",
//            "plastName": "kumar"
//    }
    @PostMapping("")
    private void bookTicket(@RequestBody BookRequest bookRequest) {
        bookingService.bookTicket(bookRequest);

    }
    
    @GetMapping("")
    private Map<String, Map<String, Map<Integer, String>>> getAllBookings() {
        return bookingService.getAllBooking();
    }

}
