package uk.gov.dwp.uc.pairtest.domain;

/**
 * Should be an Immutable Object
 */
public class TicketRequest {

    private final int noOfTickets;
    private final TicketType type;

    public TicketRequest(TicketType type, int noOfTickets) {
        this.type = type;
        this.noOfTickets = noOfTickets;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public TicketType getTicketType() {
        return type;
    }

}
