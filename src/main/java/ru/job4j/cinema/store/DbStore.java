package ru.job4j.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DbStore implements Store {

    private static final Logger LOG = LoggerFactory.getLogger(DbStore.class.getName());
    private final BasicDataSource pool = new BasicDataSource();

    private DbStore() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties = new Properties();
            properties.load(in);
            Class.forName(properties.getProperty("jdbc.driver"));
            pool.setDriverClassName(properties.getProperty("jdbc.driver"));
            pool.setUrl(properties.getProperty("jdbc.url"));
            pool.setUsername(properties.getProperty("jdbc.username"));
            pool.setPassword(properties.getProperty("jdbc.password"));
            pool.setMinIdle(5);
            pool.setMaxIdle(10);
            pool.setMaxOpenPreparedStatements(100);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static final class Lazy {
        private static final Store INSTANCE = new DbStore();
    }

    public static Store instanceOf() {
        return Lazy.INSTANCE;
    }

    @Override
    public List<Session> findAllSessions() {
        List<Session> sessions = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM session")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    sessions.add(new Session(
                            it.getInt("id"),
                            it.getString("name")
                    ));
                }
            }
        } catch (Exception e) {
            LOG.error("Error fetching data from DB", e);
        }
        return sessions;
    }

    @Override
    public List<Ticket> findAllTicketsBySessionId(int sessionId) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM ticket WHERE session_id = ?")
        ) {
            ps.setInt(1, sessionId);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(new Ticket(
                            it.getInt("session_id"),
                            it.getInt("line"),
                            it.getInt("cell"),
                            it.getInt("account_id")
                    ));
                }
            }
        } catch (Exception e) {
            LOG.error("Error fetching data from DB", e);
        }
        return tickets;
    }

    @Override
    public Ticket saveTicket(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO ticket(session_id, line, cell, account_id) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )
        ) {
            ps.setInt(1, ticket.getSessionId());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getAccountId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Could not create a record in DB", e);
        }
        return ticket;
    }

    @Override
    public Account saveAccount(Account account) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO account(username, email, phone) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )
        ) {
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getEmail());
            ps.setString(3, account.getPhone());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    account.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Could not create a record in DB", e);
        }
        return account;
    }
}
