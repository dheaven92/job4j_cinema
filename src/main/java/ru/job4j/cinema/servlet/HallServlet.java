package ru.job4j.cinema.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class HallServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(HallServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Ticket> tickets = DbStore.instanceOf()
                .findAllTicketsBySessionId(Integer.parseInt(req.getParameter("sessionId")));
        ObjectMapper objectMapper = new ObjectMapper();
        String ticketsJson = objectMapper.writeValueAsString(tickets);
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().write(ticketsJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Ticket ticket = objectMapper.readValue(req.getReader().readLine(), Ticket.class);
            Ticket ticketInDb = DbStore.instanceOf().saveTicket(ticket);
            String ticketJson = objectMapper.writeValueAsString(ticketInDb);
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().write(ticketJson);
        } catch (Exception e) {
            LOG.error("Could not save a ticket", e);
            resp.setContentType("application/json; charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        }
    }
}
