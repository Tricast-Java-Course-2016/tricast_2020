package com.tricast.managers.exceptions;

public class WorkingHoursException extends Exception {

    private static final long serialVersionUID = 5875667671354403814L;

    public WorkingHoursException() {
        super();
    }

    public WorkingHoursException(String message) {
        super(message);
    }
}
