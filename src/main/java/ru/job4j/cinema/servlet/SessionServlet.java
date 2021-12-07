package ru.job4j.cinema.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class SessionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Session> sessions = DbStore.instanceOf().findAllSessions();
        ObjectMapper objectMapper = new ObjectMapper();
        String sessionsJson = objectMapper.writeValueAsString(sessions);
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().write(sessionsJson);
    }
}
