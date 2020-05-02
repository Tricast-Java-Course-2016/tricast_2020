/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tricast.managers.custom_classes;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class DateManager {
    private ZonedDateTime startDate;
    private ZonedDateTime finishDate;

    public DateManager() {
        this.startDate = null;
        this.finishDate = null;
        generationCurrentYearDate();
    }
    
    public DateManager(int specifiedYear){
        this.startDate = null;
        this.finishDate = null;
        dateGenerationBySpecifyingYear(specifiedYear);
    }
    
    public DateManager(int specifiedYear,int specifiedMonth){
        this.startDate = null;
        this.finishDate = null;
        dateGenerationByspecifiedYearAndMonth(specifiedYear,specifiedMonth);
    }
    
    
    
    private int getTheCurrentYear() {
		return ZonedDateTime.now().getYear();
	}
    
    private ZonedDateTime getCurrentYearFirstDay() {
		return ZonedDateTime.of(getTheCurrentYear(), 1, 1, 0, 0, 0, 0, ZoneId.systemDefault());
	}
	
	private ZonedDateTime getCurrentYearLastDay() {
		return ZonedDateTime.of(getTheCurrentYear(), 12, 31, 23, 59, 59, 999, ZoneId.systemDefault());
	}
    
    private void generationCurrentYearDate(){
        startDate = getCurrentYearFirstDay();
        finishDate = getCurrentYearLastDay();
    }
    
    
    private void dateGenerationBySpecifyingYear(int specifiedYear){
         startDate = getSpecifiedYearFirstDay(specifiedYear);
         finishDate = getSpecifiedYearLastDay(specifiedYear);
    }
    
    private ZonedDateTime getSpecifiedYearFirstDay(int specifiedYear) {
		return ZonedDateTime.of(specifiedYear, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault());
	}
	
	private ZonedDateTime getSpecifiedYearLastDay(int specifiedYear) {
		return ZonedDateTime.of(specifiedYear, 12, 31, 23, 59, 59, 999, ZoneId.systemDefault());
	}
    
    
    private void dateGenerationByspecifiedYearAndMonth(int specifiedYear, int specifiedMonth){
         startDate = getFirstDayOfSpecifiedYearAndMonth(specifiedYear,specifiedMonth);
         finishDate = getLastDayOfSpecifiedYearAndMonth(specifiedYear,specifiedMonth);
    }
    
    private ZonedDateTime getFirstDayOfSpecifiedYearAndMonth(int specifiedYear, int specifiedMonth) {
		return ZonedDateTime.of(specifiedYear, specifiedMonth, 1, 0, 0, 0, 0, ZoneId.systemDefault());
	}
	
	private ZonedDateTime getLastDayOfSpecifiedYearAndMonth(int specifiedYear, int specifiedMonth) {
		return ZonedDateTime.of(specifiedYear, specifiedMonth, getWithDayOfMonth(specifiedYear,specifiedMonth), 23, 59, 59, 999, ZoneId.systemDefault());
	}
    
    private int getWithDayOfMonth(int specifiedYear,int specifiedMonth) {
        Calendar calendar = new GregorianCalendar(specifiedYear,specifiedMonth,1,00,00,00);
        return calendar.getActualMaximum(specifiedMonth);
	}

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public ZonedDateTime getFinishDate() {
        return finishDate;
    }
    
}
