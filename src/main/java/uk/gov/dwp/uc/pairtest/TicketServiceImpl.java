package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;


public class TicketServiceImpl implements TicketService {

    /**
     * Should only have private methods other than the one below.
     */
    private final TicketPurchasingRules ticketPurchasingRules;
    private final SeatReservationService seatReservationService;
    private final TicketPaymentService ticketPaymentService;

    public TicketServiceImpl(TicketPurchasingRules ticketPurchasingRules, SeatReservationService seatReservationService, TicketPaymentService ticketPaymentService) {
        this.ticketPurchasingRules = ticketPurchasingRules;
        this.seatReservationService = seatReservationService;
        this.ticketPaymentService = ticketPaymentService;
    }

    @Override
    public void purchaseTickets(TicketPurchaseRequest ticketPurchaseRequest) throws InvalidPurchaseException {
        try {
            ticketPurchasingRules.isPurchaseValid(ticketPurchaseRequest);
            seatReservationService.reserveSeat(ticketPurchaseRequest.getAccountId(), ticketPurchaseRequest.getTotalAmountOfSeating());
            ticketPaymentService.makePayment(ticketPurchaseRequest.getAccountId(), ticketPurchaseRequest.getTotalPrice());
        } catch (InvalidPurchaseException e) {
            e.getMessage();
        }
    }

}
