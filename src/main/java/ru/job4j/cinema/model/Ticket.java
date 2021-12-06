package ru.job4j.cinema.model;

import java.util.Objects;

public class Ticket {

    private int id;
    private int sessionId;
    private int rowId;
    private int cellId;
    private int accountId;

    public Ticket(int sessionId, int rowId, int cellId, int accountId) {
        this.sessionId = sessionId;
        this.rowId = rowId;
        this.cellId = cellId;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int getCellId() {
        return cellId;
    }

    public void setCellId(int cellId) {
        this.cellId = cellId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return sessionId == ticket.sessionId
                && rowId == ticket.rowId
                && cellId == ticket.cellId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, rowId, cellId);
    }
}
