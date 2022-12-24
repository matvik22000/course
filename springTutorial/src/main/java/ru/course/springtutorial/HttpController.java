package ru.course.springtutorial;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.course.springtutorial.data.Reservation;

import java.text.ParseException;
import java.util.Date;

@Controller
@CrossOrigin(allowCredentials = "true", originPatterns = "*")
public class HttpController {
    @ResponseBody
    @PostMapping(value = "/reserve", produces = "application/json")
    String createReservation(
            @RequestParam String classNumber,
            @RequestParam int teacherId,
            @RequestParam String reason,
            @RequestParam("startTime") long startTimeInMilliseconds,
            @RequestParam("endTime") long endTimeInMilliseconds
    ) {
        Reservation reservation = new Reservation(
                classNumber, teacherId, reason, new Date(startTimeInMilliseconds), new Date(endTimeInMilliseconds)
        );
        return reservation.save();
    }

    @ResponseBody
    @GetMapping(value = "/get_reservation", produces = "application/json")
    Reservation getReservation(@RequestParam  String id) throws ParseException {
        return Reservation.get(id);
    }

    @ResponseBody
    @GetMapping(value = "/get_reservations", produces = "application/json")
    Reservation[] getReservations() throws ParseException {
        return Reservation.getList();
    }
}
