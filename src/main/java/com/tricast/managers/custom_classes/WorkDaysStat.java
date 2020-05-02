/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tricast.managers.custom_classes;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tricast.repositories.entities.Workday;
import com.tricast.repositories.entities.Worktime;

/**
 *
 * @author Dell
 */
public class WorkDaysStat {
    private List<Workday> workDays;
    private List<Worktime> workTimes;
    private int currentWeekWorkTimes;
    private int previousWeekWorkTimes;
    private Map<Long, Integer> workedHours;
    private ZonedDateTime firstDayOfcurrentWeekDateTime;

    public WorkDaysStat(List<Workday> workDays, List<Worktime> workTimes,ZonedDateTime firstDayOfcurrentWeekDateTime) {
        workedHours = new HashMap<>();
        this.workDays = workDays;
        this.workTimes = workTimes;
        this.firstDayOfcurrentWeekDateTime = firstDayOfcurrentWeekDateTime;
        countsAllWorkedHoursByWorkDayIds();
        this.currentWeekWorkTimes=getWorkMinutesAtAWeek(firstDayOfcurrentWeekDateTime,workTimes)/60;
        this.previousWeekWorkTimes=getWorkMinutesAtAWeek(firstDayOfcurrentWeekDateTime.minusWeeks(1L),workTimes)/60;
    }

    private void countsAllWorkedHoursByWorkDayIds(){
        workTimes.forEach(worktime ->{
        addNewValueOrModifiedOldValue(worktime);
        });
    }

    private void addNewValueOrModifiedOldValue(Worktime workTime){
        Long workTimeId = workTime.getId();
        if(workedHours.containsKey(workTimeId)){
            workedHours.replace(workTime.getWorkdayId(), workedHours.get(workTimeId)+calculatedWorkdHours(workTime.getStartTime(),workTime.getEndTime()));
        }
        else{
            workedHours.put(workTime.getWorkdayId(),calculatedWorkdHours(workTime.getStartTime(),workTime.getEndTime()));
        }
    }

    private int calculatedWorkdHours(ZonedDateTime start, ZonedDateTime finish){
        int hours=0;
        int minutes=0;
        hours = finish.getHour()-start.getHour();
        minutes = finish.getMinute()-start.getMinute();
        return hours*60 + minutes;
    }

    private int getWorkMinutesAtAWeek(ZonedDateTime firstDayOfweek, List<Worktime> worktimes ){
        int workedMinutes=0;
        for (Worktime worktime : worktimes) {
            if(worktime.getStartTime().isAfter(firstDayOfweek) && worktime.getEndTime().isBefore(firstDayOfweek.plusDays(6))){
                workedMinutes = calculatedWorkdHours(worktime.getStartTime(), worktime.getEndTime());
            }
        }
        return  workedMinutes;
    }

    public int getCurrentWeekWorkTimes() {
        return currentWeekWorkTimes;
    }

    public int getPreviousWeekWorkTimes() {
        return previousWeekWorkTimes;
    }

    public Map<Long, Integer> getWorkedHours() {
        return workedHours;
    }

    public ZonedDateTime getFirstDayOfcurrentWeekDateTime() {
        return firstDayOfcurrentWeekDateTime;
    }

}
