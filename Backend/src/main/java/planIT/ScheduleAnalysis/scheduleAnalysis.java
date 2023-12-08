package planIT.ScheduleAnalysis;

import planIT.Entity.Events.*;
import planIT.Entity.Users.*;
import planIT.ScheduleCompare.scheduleCompare;

import java.util.*;

/**
 * Utility class for analyzing a user's schedule and measuring weekly activity.
 * It provides a method to measure and display the number and duration of events for each day of the week.
 *
 * @author Chris Smith
 *
 */
public class scheduleAnalysis {

    /**
     * Measures and displays the weekly activity of a user based on their schedule.
     *
     * @param user The user for whom to measure weekly activity.
     * @return A formatted string representing the weekly activity, including the number and total duration of events for each day.
     */
    static public String measureWeeklyActivity(User user){
        if(user == null){
            return "Invalid user";
        }

        List<Event> userSchedule = new ArrayList<>(user.getEvents());
        int[] dayNum = new int[7];
        int[] dayCount = new int[7];
        int[] dayLength = new int[7];
        int weekCount = 1;

        if(userSchedule.isEmpty()){
            return "Schedule is Empty";
        }
        //SORT EVENT BY DAY OF THE WEEK
        //userSchedule.sort(new scheduleAnalysis.startDayComparator());
        userSchedule.sort(new scheduleAnalysis.startDateComparator());


//        for(Event event: userSchedule) {    //DEBUG
//            System.out.println(event.getStartDate());
//        }
        //COLLECT DATA
        Calendar cal = Calendar.getInstance();

        //for(Event event: userSchedule){
        for(int i=0; i< userSchedule.size(); ++i){
            cal.setTime(userSchedule.get(i).getStartDate());
            dayNum[cal.get(Calendar.DAY_OF_WEEK) - 1] += 1;
            dayLength[cal.get(Calendar.DAY_OF_WEEK)-1] += eventLength(userSchedule.get(i));
            if(userSchedule.size()-1 > i) {
                if (userSchedule.get(i).getStartDate().getDate() != userSchedule.get(i + 1).getStartDate().getDate()) {
                    dayCount[cal.get(Calendar.DAY_OF_WEEK) - 1] += 1;
                }
            }
        }

//        for(int day: dayCount){ //no div by 0
//            if(day<=1){
//                day=1;
//            }
//        }
        for(int i=0; i<7; ++i){
            if(dayCount[i]<=0){
                dayCount[i] =1;
            }
            if(dayCount[i]>weekCount){
                weekCount = dayCount[i];
            }
        }

        String week =
                "Sunday:\n" +dayNum[0] +" Events, Average of " +dayLength[0]/60/weekCount +" Hours " +dayLength[0]%60 +" Minutes across " +weekCount+" week(s)\n"
                        +toLine(dayLength[0]/weekCount) +"\n"
                        +"Monday:\n" +dayNum[1] +" Events, Average of " +dayLength[1]/60/weekCount +" Hours " +dayLength[1]%60 +" Minutes across " +weekCount+" week(s)\n"
                        +toLine(dayLength[1]/weekCount) +"\n"
                        +"Tuesday:\n" +dayNum[2] +" Events, Average of " +dayLength[2]/60/weekCount +" Hours " +dayLength[2]%60 +" Minutes across " +weekCount+" week(s)\n"
                        +toLine(dayLength[2]/weekCount) +"\n"
                        +"Wednesday:\n" +dayNum[3] +" Events, Average of " +dayLength[3]/60/weekCount +" Hours " +dayLength[3]%60 +" Minutes across " +weekCount+" week(s)\n"
                        +toLine(dayLength[3]/weekCount) +"\n"
                        +"Thursday:\n" +dayNum[4] +" Events, Average of " +dayLength[4]/60/weekCount +" Hours " +dayLength[4]%60 +" Minutes across " +weekCount+" week(s)\n"
                        +toLine(dayLength[4]/weekCount) +"\n"
                        +"Friday:\n" +dayNum[5] +" Events, Average of " +dayLength[5]/60/weekCount +" Hours " +dayLength[5]%60 +" Minutes across " +weekCount+" week(s)\n"
                        +toLine(dayLength[5]/weekCount) +"\n"
                        +"Saturday:\n" +dayNum[6] +" Events, Average of " +dayLength[6]/60/weekCount +" Hours " +dayLength[6]%60 +" Minutes across " +weekCount+" week(s)\n"
                        +toLine(dayLength[6]/weekCount)
                ;

        return week;
    }

    /**
     * Calculates the duration of an event in minutes.
     *
     * @param event The event for which to calculate the duration.
     * @return The duration of the event in minutes.
     */
    static private int eventLength(Event event) {
        int start = 0;
        int end = 0;
        int length =0;
        if (event.getStartDate().getDate() != event.getEndDate().getDate()){
            Date temp = event.getStartDate();
            while(temp.getDate()!=event.getEndDate().getDate()){
                length += 1440;
                temp.setDate(temp.getDate()+1);
            }
        }
        start = event.getStartDate().getHours() * 60 + event.getStartDate().getMinutes();
        end = event.getEndDate().getHours() * 60 + event.getEndDate().getMinutes();
        length = length + end-start;
        return length;
    }

    /**
     * Generates a line of dashes representing the duration of an event.
     *
     * @param length The length of the line.
     * @return A string representing the line of dashes.
     */
    static private String toLine(int length){
        String line ="[";
        for(int i=0; i<length/10; ++i){
            line += "-";
        }
        line += "]\n";
        return line;
    }


    /**
     * Comparator for sorting events based on the day of the week.
     */
    static private class startDayComparator implements Comparator<Event> {
        public int compare(Event event1, Event event2){
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(event1.getStartDate());
            cal2.setTime(event2.getStartDate());

            if(cal1.get(Calendar.DAY_OF_WEEK) == cal2.get(Calendar.DAY_OF_WEEK)){
                return 0;
            }
            else if(cal1.get(Calendar.DAY_OF_WEEK) > cal2.get(Calendar.DAY_OF_WEEK)){
                return 1;
            }
            else{
                return -1;
            }
        }
    }

    //COMPARATOR FOR EVENTS, based on startDate
    /**
     * Enables events to be compared and sorted based on their start dates
     */
    static private class startDateComparator implements Comparator<Event> {
        public int compare(Event event1, Event event2) {
            if (event1.getStartDate() == null || event2.getStartDate() == null) {
                return 0;
            } else if (event1.getStartDate().equals(event2.getStartDate())) {
                return 0;
            } else if (event1.getStartDate().after(event2.getStartDate())) {
                return 1;
            } else {
                return -1;
            }
        }
    }

}
