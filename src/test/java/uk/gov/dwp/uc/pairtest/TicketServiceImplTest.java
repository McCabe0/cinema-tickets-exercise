package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.Test;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketType;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TicketServiceImplTest {

    private final TicketPurchasingRules ticketPurchasingRules = new TicketPurchasingRules();
    private final SeatReservationService seatReservationService = mock(SeatReservationService.class);
    private final TicketPaymentService ticketPaymentService = mock(TicketPaymentService.class);
    private final TicketServiceImpl underTest = new TicketServiceImpl(ticketPurchasingRules, seatReservationService, ticketPaymentService);

    @Test
    public void completesPurchaseCheck() {
        TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest(1, List.of(new TicketRequest(TicketType.ADULT, 3), new TicketRequest(TicketType.CHILD, 2)));
        underTest.purchaseTickets(ticketPurchaseRequest);

        verify(seatReservationService).reserveSeat(ticketPurchaseRequest.getAccountId(), ticketPurchaseRequest.getTotalAmountOfSeating());
        verify(ticketPaymentService).makePayment(ticketPurchaseRequest.getAccountId(), ticketPurchaseRequest.getTotalPrice());

    }

    @Test
    public void checkExceptionIsThrown() {
        TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest(1, List.of(new TicketRequest(TicketType.CHILD, 21)));

        underTest.purchaseTickets(ticketPurchaseRequest);

       assertThrows(InvalidPurchaseException.class, () -> ticketPurchasingRules.isPurchaseValid(ticketPurchaseRequest));
    }

}