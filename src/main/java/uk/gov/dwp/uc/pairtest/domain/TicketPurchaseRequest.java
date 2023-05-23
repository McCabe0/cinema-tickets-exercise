package uk.gov.dwp.uc.pairtest.domain;

import java.util.Collections;
import java.util.List;

/**
 * Should be an Immutable Object
 */
public class TicketPurchaseRequest {

    private final long accountId;
    private final List<TicketRequest> ticketRequests;

    public TicketPurchaseRequest(long accountId, List<TicketRequest> ticketRequests) {
        this.accountId = accountId;
        this.ticketRequests = ticketRequests;
    }

    public long getAccountId() {
        return accountId;
    }

    public List<TicketRequest> getTicketTypeRequests() {
        return Collections.unmodifiableList(ticketRequests);
    }

    public int getTotalAmountOfTickets() {
       return getTicketTypeRequests().stream()
               .mapToInt(TicketRequest::getNoOfTickets).sum();
    }

    public int getTotalAmountOfSeating() {
        List<TicketRequest> ticketRequiringSeats = getTicketTypeRequests().stream()
                .filter(ticketRequest -> ticketRequest.getTicketType() != TicketType.INFANT)
                .toList();

        return ticketRequiringSeats.stream()
                .mapToInt(TicketRequest::getNoOfTickets).sum();
    }

    public int getTotalPrice() {
       return getTicketTypeRequests().stream()
                .mapToInt(ticketRequests -> (ticketRequests.getTicketType().getPrice() * ticketRequests.getNoOfTickets()))
               .sum();

    }

    //This is the user Requesting the ticket online or machine, for example if the user gets 2 children, one adult and one infant, there will be 3 requests of 2 children, 1 adult and 1 infanct
}
