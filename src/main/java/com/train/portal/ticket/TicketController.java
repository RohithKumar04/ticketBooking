package com.train.portal.ticket;

import com.train.portal.ticket.modal.SeatDetails;
import com.train.portal.ticket.modal.UserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService=ticketService;
    }

    @GetMapping("/trains/{trainName}")
    private List<UserDetails> getBookingBySections(
            @PathVariable("trainName") String trainName,
            @RequestParam("section") String section) {

        return ticketService.getBookingBySections(trainName, section);
    }

    @GetMapping("/user/{email}")
    public UserDetails getBookingDetailsByEmail(@PathVariable("email") String email) {

        return ticketService.getBookingDetailsByEmail(email);

    }

    @PutMapping("/user/{email}")
    public ResponseEntity<String> updateTicketByEmail(@PathVariable("email") String email,@RequestBody SeatDetails seatDetails) {
        ticketService.updateTicketByEmail(email, seatDetails);

        return ResponseEntity.ok("Updated Successfully");

    }

    @DeleteMapping("/user/{email}")
    public ResponseEntity<String> deleteTicketByEmail(@PathVariable("email") String email) {

        ticketService.deleteTicketByEmail(email);

        return ResponseEntity.ok("Deleted Successfully");

    }
}
