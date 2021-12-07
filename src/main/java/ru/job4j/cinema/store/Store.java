package ru.job4j.cinema.store;

import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;

import java.util.List;

public interface Store {

    List<Session> findAllSessions();

    List<Ticket> findAllTicketsBySessionId(int sessionId);

    Ticket saveTicket(Ticket ticket);

    Account saveAccount(Account account);
}
