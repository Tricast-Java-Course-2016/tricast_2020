/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tricast.repositories.models;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;


public class CurrentMonthOfYear {
    private ZonedDateTime firstDayOfCurrentMonth;
    private ZonedDateTime lastDayOfCurrentMonth;

    public CurrentMonthOfYear() {
        this.firstDayOfCurrentMonth = CurrentFirstDayOfMonth();
        this.lastDayOfCurrentMonth = CurrentLastDayOfMonth();
    }

	private ZonedDateTime CurrentFirstDayOfMonth() {
		return  ZonedDateTime.of(getCurrentYear(), getCurrentMonth() , 1, 0, 0, 0, 000, ZoneId.systemDefault());
	}

	private ZonedDateTime CurrentLastDayOfMonth() {
		return  ZonedDateTime.of(getCurrentYear(),getCurrentMonth(), getActualMaximumOfMonth(), 23, 59, 59, 999, ZoneId.systemDefault());
	}
    
    private int getActualMaximumOfMonth(){
        return Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    private int getCurrentYear(){
        return ZonedDateTime.now().getYear();
    }
    
    private int getCurrentMonth(){
        return ZonedDateTime.now().getMonthValue();
    }

    public ZonedDateTime getFirstDayOfCurrentMonth() {
        return firstDayOfCurrentMonth;
    }

    public ZonedDateTime getLastDayOfCurrentMonth() {
        return lastDayOfCurrentMonth;
    }
    
}
