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
    private int currentWeekWorkMinutes;
    private int previousWeekWorkTimesMinutes;
    private Map<Long, Integer> workedHours;
    private ZonedDateTime firstDayOfcurrentWeekDateTime;

    public WorkDaysStat(List<Workday> workDays, List<Worktime> workTimes,ZonedDateTime firstDayOfcurrentWeekDateTime) {
        workedHours = new HashMap<>();
        this.workDays = workDays;
        this.workTimes = workTimes;
        this.firstDayOfcurrentWeekDateTime = firstDayOfcurrentWeekDateTime;
        countsAllWorkedHoursByWorkDayIds();
        int tempWorktimes = getWorkMinutesAtAWeek(firstDayOfcurrentWeekDateTime,workTimes);
        this.currentWeekWorkTimes = tempWorktimes/60;
        this.currentWeekWorkMinutes = calculateMinutes(tempWorktimes);
        tempWorktimes = getWorkMinutesAtAWeek(firstDayOfcurrentWeekDateTime.minusWeeks(1L),workTimes);
        this.previousWeekWorkTimes = tempWorktimes/60;
        this.previousWeekWorkTimesMinutes = calculateMinutes(tempWorktimes);
    }
    
    public int calculateMinutes(int minutes){
        int workMinutes=minutes;
        int workHours=workMinutes/60;
        return workMinutes-(workHours*60);
    }

    private void countsAllWorkedHoursByWorkDayIds(){
        workTimes.forEach(worktime ->{
        addNewValueOrModifiedOldValue(worktime);
        });
    }

    private void addNewValueOrModifiedOldValue(Worktime workTime){
        Long getWorkdayId = workTime.getWorkdayId();
        if(workedHours.containsKey(getWorkdayId)){
            int newValue = workedHours.get(getWorkdayId)+calculatedWorkdHours(workTime.getStartTime(),workTime.getEndTime());
            workedHours.replace(workTime.getWorkdayId(),newValue);
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
                workedMinutes = workedMinutes + calculatedWorkdHours(worktime.getStartTime(), worktime.getEndTime());
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

    public int getCurrentWeekWorkMinutes() {
        return currentWeekWorkMinutes;
    }

    public void setCurrentWeekWorkMinutes(int currentWeekWorkMinutes) {
        this.currentWeekWorkMinutes = currentWeekWorkMinutes;
    }

    public int getPreviousWeekWorkTimesMinutes() {
        return previousWeekWorkTimesMinutes;
    }

    public void setPreviousWeekWorkTimesMinutes(int previousWeekWorkTimesMinutes) {
        this.previousWeekWorkTimesMinutes = previousWeekWorkTimesMinutes;
    }

}
