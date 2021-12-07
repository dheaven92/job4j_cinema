package ru.job4j.cinema.store;

import org.junit.Test;
import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Ticket;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DbStoreTest {

    @Test
    public void testFindAllTicketsBySessionId() {
        Account account = DbStore.instanceOf()
                .saveAccount(new Account(0, "Anna", "a@mail.com", "111"));
        Ticket ticket1 = new Ticket(1, 1, 1, account.getId());
        Ticket ticket2 = new Ticket(1, 2, 1, account.getId());
        Ticket ticket3 = new Ticket(2, 1, 1, account.getId());
        DbStore.instanceOf().saveTicket(ticket1);
        DbStore.instanceOf().saveTicket(ticket2);
        DbStore.instanceOf().saveTicket(ticket3);
        assertThat(DbStore.instanceOf().findAllTicketsBySessionId(1), is(List.of(ticket1, ticket2)));
    }

    @Test
    public void testSaveTicket() {
        Account account = DbStore.instanceOf()
                .saveAccount(new Account(0, "Bob", "b@mail.com", "222"));
        Ticket ticket = DbStore.instanceOf().saveTicket(new Ticket(1, 2, 3, account.getId()));
        List<Ticket> tickets = DbStore.instanceOf().findAllTicketsBySessionId(1);
        assertThat(tickets.get(tickets.size() - 1), is(ticket));
    }
}