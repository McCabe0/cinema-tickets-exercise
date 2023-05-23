package uk.gov.dwp.uc.pairtest;

import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketType;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class TicketPurchasingRules {

    private static final int MAXIMUM_TICKETS = 20;

    public void isPurchaseValid(TicketPurchaseRequest ticketPurchaseRequests) {
        maxAndMinAmountToPurchase(ticketPurchaseRequests) ;
        adultNeededToCompanyChildOrInfant(ticketPurchaseRequests);
        idGreaterThan0(ticketPurchaseRequests);
    }

    private void maxAndMinAmountToPurchase(TicketPurchaseRequest ticketPurchaseRequests) {
        if (ticketPurchaseRequests.getTotalAmountOfTickets() <= 0 ||  ticketPurchaseRequests.getTotalAmountOfTickets() > MAXIMUM_TICKETS){
            throw new InvalidPurchaseException("Number of tickets your trying to purchase is below 0 or over 20");
        }
    }

    private void adultNeededToCompanyChildOrInfant(TicketPurchaseRequest ticketPurchaseRequests) {
        if (ticketPurchaseRequests.getTicketTypeRequests().stream().
                noneMatch(ticketRequest -> ticketRequest.getTicketType() == TicketType.ADULT)) {
            throw new InvalidPurchaseException("You can't purchase a child or infant ticket without an Adult ticket");
        }
    }

    private void idGreaterThan0(TicketPurchaseRequest ticketPurchaseRequests) {
        if (ticketPurchaseRequests.getAccountId() <= 0) {
            throw new InvalidPurchaseException("Account id has to be greater than 0");
        }
    }

    //Can be void to not have boolean
    //Extra rules that can be added, because of seating, you can't have more infants than adults. As there won't be lap to sit on
}
