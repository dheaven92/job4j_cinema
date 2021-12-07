package ru.job4j.cinema.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(AccountServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Account account = objectMapper.readValue(req.getReader().readLine(), Account.class);
            Account accountInDb = DbStore.instanceOf().saveAccount(account);
            String accountJson = objectMapper.writeValueAsString(accountInDb);
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().write(accountJson);
        } catch (Exception e) {
            LOG.error("Could not save an account", e);
            resp.setContentType("application/json; charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        }
    }
}
