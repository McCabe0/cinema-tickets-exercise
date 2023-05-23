package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketType;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TicketPurchasingRulesTest {

    TicketPurchasingRules underTest = new TicketPurchasingRules();

    private final TicketRequest adultRequest = new TicketRequest(TicketType.ADULT, 1);
    private final TicketRequest childRequest = new TicketRequest(TicketType.CHILD, 3);
    private final TicketRequest infantRequest = new TicketRequest(TicketType.INFANT, 1);
    private final TicketPurchaseRequest ticketPurchaseRequestWithAdult = new TicketPurchaseRequest(1, List.of(adultRequest, childRequest, infantRequest));


    @Test
    public void checkIfPurchaseIsValid() {
        assertDoesNotThrow(() -> underTest.isPurchaseValid(ticketPurchaseRequestWithAdult));
    }


    @Test
    public void testRequestContainsAdultTicketReturnsFalse() {
        TicketPurchaseRequest ticketPurchaseRequestNoAdult = new TicketPurchaseRequest(2, List.of(childRequest, infantRequest));

        Throwable exception = assertThrows(InvalidPurchaseException.class, () -> underTest.isPurchaseValid(ticketPurchaseRequestNoAdult));

        assertEquals("You can't purchase a child or infant ticket without an Adult ticket", exception.getMessage());
    }

    @Test
    public void noMoreThan20TicketsCanBePurchased() {
        TicketRequest moreThan20Tickets = new TicketRequest(TicketType.ADULT, 21);
        TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest(2, List.of(moreThan20Tickets));

        Throwable exception = assertThrows(InvalidPurchaseException.class, () -> underTest.isPurchaseValid(ticketPurchaseRequest));

        assertEquals("Number of tickets your trying to purchase is below 0 or over 20", exception.getMessage());

    }

    @Test
    public void noLessThanOneTicketsCanBePurchased() {
        TicketRequest moreThan20Tickets = new TicketRequest(TicketType.ADULT, 0);
        TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest(2, List.of(moreThan20Tickets));

        Throwable exception = assertThrows(InvalidPurchaseException.class, () -> underTest.isPurchaseValid(ticketPurchaseRequest));

        assertEquals("Number of tickets your trying to purchase is below 0 or over 20", exception.getMessage());

    }

    @Test
    public void canPurchase20Tickets() {
        TicketRequest ticketRequestFor20 = new TicketRequest(TicketType.ADULT, 20);
        TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest(2, List.of(ticketRequestFor20));
        assertDoesNotThrow(() -> underTest.isPurchaseValid(ticketPurchaseRequest));

    }

    @Test
    public void idSetAsZero() {
        TicketPurchaseRequest ticketPurchaseWithAccountNumberBeing0 = new TicketPurchaseRequest(0, List.of(childRequest, infantRequest, adultRequest));

        Throwable exception = assertThrows(InvalidPurchaseException.class, () -> underTest.isPurchaseValid(ticketPurchaseWithAccountNumberBeing0));

        assertEquals("Account id has to be greater than 0", exception.getMessage());

    }


}