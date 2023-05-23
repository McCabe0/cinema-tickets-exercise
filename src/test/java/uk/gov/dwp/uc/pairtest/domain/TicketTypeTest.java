package uk.gov.dwp.uc.pairtest.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicketTypeTest {

    @Test
    public void checkIfAdultPrizeReturnsCorrectly() {
        double price = TicketType.ADULT.getPrice();
        assertEquals(20, price);
    }
    @Test
    public void checkIfChildPrizeReturnsCorrectly() {
        double price = TicketType.CHILD.getPrice();
        assertEquals(10, price);
    }
    @Test
    public void checkIfInfantPrizeReturnsCorrectly() {
        double price = TicketType.INFANT.getPrice();
        assertEquals(0, price);
    }

}