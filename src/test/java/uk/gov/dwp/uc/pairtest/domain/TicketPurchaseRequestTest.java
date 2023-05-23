package uk.gov.dwp.uc.pairtest.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TicketPurchaseRequestTest {

    private final TicketRequest adultTicketRequest = new TicketRequest(TicketType.ADULT, 15);
    private final TicketRequest childTicketRequest = new TicketRequest(TicketType.CHILD, 3);
    private final TicketRequest infantTicketRequest = new TicketRequest(TicketType.INFANT, 3);
    private final List<TicketRequest> ticketRequests = List.of(adultTicketRequest, childTicketRequest, infantTicketRequest);
    private final TicketPurchaseRequest underTest = new TicketPurchaseRequest(1, ticketRequests);

    @Test
    public void getAccountId(){
        assertEquals(1, underTest.getAccountId());
    }

    @Test
    public void getTicketRequestList() {
        assertEquals(ticketRequests, List.of(adultTicketRequest, childTicketRequest, infantTicketRequest));
    }
    @Test
    public void getCorrectTotalTicketsSum() {
        assertEquals(21, underTest.getTotalAmountOfTickets());
    }

    @Test
    public void getCorrectAmountOfSeatsToReverse() {
        assertEquals(18, underTest.getTotalAmountOfSeating());
    }

    @Test
    public void getTotal() {
        assertEquals(330, underTest.getTotalPrice());
    }
}