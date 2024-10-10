package activity;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import activity.FlightBookingSystem.BookingResult;

public class FlightBookingSystemTest {

    private FlightBookingSystem bookingSystem;

    @Before
    public void setUp(){
        bookingSystem = new FlightBookingSystem();
    }

    @Test
    public void TC1(){
        //when
        BookingResult result = bookingSystem.bookFlight(5, LocalDateTime.of(2024, 10, 7, 14, 0), 1, 1000, 200, false, LocalDateTime.of(2024, 10, 8, 4, 0), 5000);

        //then
        assertEquals(false, result.confirmation);
        assertEquals(0, result.totalPrice, 0.1);
        assertEquals(0, result.refundAmount, 0.1);
        assertEquals(false, result.pointsUsed);
    }

    @Test
    public void TC2(){
        //when
        BookingResult result = bookingSystem.bookFlight(5, LocalDateTime.of(2024, 10, 7, 14, 0), 10, 1000, 200, false, LocalDateTime.of(2024, 10, 8, 4, 0), 5000);

        //then
        assertEquals(true, result.confirmation);
        assertEquals(7645, result.totalPrice, 0.1);
        assertEquals(0, result.refundAmount, 0.1);
        assertEquals(true, result.pointsUsed);
    }

    @Test
    public void TC3(){
        //when
        BookingResult result = bookingSystem.bookFlight(5, LocalDateTime.of(2024, 10, 7, 14, 0), 10, 1000, 200, true, LocalDateTime.of(2024, 10, 8, 4, 0), 5000);

        //then
        assertEquals(false, result.confirmation);
        assertEquals(0, result.totalPrice, 0.1);
        assertEquals(3822.5, result.refundAmount, 0.1);
        assertEquals(false, result.pointsUsed);
    }

    @Test
    public void TC4(){
        //when
        BookingResult result = bookingSystem.bookFlight(3, LocalDateTime.of(2024, 10, 7, 14, 0), 10, 1000, 200, true, LocalDateTime.of(2024, 11, 8, 4, 0), 0);

        //then
        assertEquals(false, result.confirmation);
        assertEquals(0, result.totalPrice, 0.1);
        assertEquals(4800, result.refundAmount, 0.1);
        assertEquals(false, result.pointsUsed);
    }

}
