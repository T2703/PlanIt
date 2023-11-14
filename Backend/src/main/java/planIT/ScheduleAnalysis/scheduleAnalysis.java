package planIT.ScheduleAnalysis;

import planIT.Entity.Events.*;
import planIT.Entity.Users.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

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
        int[] dayLength = new int[7];

        if(userSchedule.isEmpty()){
            return "Schedule is Empty";
        }
        //SORT EVENT BY DAY OF THE WEEK
        userSchedule.sort(new scheduleAnalysis.startDayComparator());

        //DEBUG
        for(Event event: userSchedule) {
            System.out.println(event.getStartDate());
        }
        //COLLECT DATA
        Calendar cal = Calendar.getInstance();

        for(Event event: userSchedule){
            cal.setTime(event.getStartDate());
            dayNum[cal.get(Calendar.DAY_OF_WEEK) - 1] += 1;
            dayLength[cal.get(Calendar.DAY_OF_WEEK)-1] += eventLength(event);
        }


        String week =
                "Sunday:    " +dayNum[0] +" Events, Total of " +dayLength[0]/60 +" Hours " +dayLength[0]%60 +" Minutes\n"
                        +toLine(dayLength[0])
                        +"Monday:    " +dayNum[1] +" Events, Total of " +dayLength[1]/60 +" Hours " +dayLength[1]%60 +" Minutes\n"
                        +toLine(dayLength[1])
                        +"Tuesday:   " +dayNum[2] +" Events, Total of " +dayLength[2]/60 +" Hours " +dayLength[2]%60 +" Minutes\n"
                        +toLine(dayLength[2])
                        +"Wednesday: " +dayNum[3] +" Events, Total of " +dayLength[3]/60 +" Hours " +dayLength[3]%60 +" Minutes\n"
                        +toLine(dayLength[3])
                        +"Thursday:  " +dayNum[4] +" Events, Total of " +dayLength[4]/60 +" Hours " +dayLength[4]%60 +" Minutes\n"
                        +toLine(dayLength[4])
                        +"Friday:    " +dayNum[5] +" Events, Total of " +dayLength[5]/60 +" Hours " +dayLength[5]%60 +" Minutes\n"
                        +toLine(dayLength[5])
                        +"Saturday:  " +dayNum[6] +" Events, Total of " +dayLength[6]/60 +" Hours " +dayLength[6]%60 +" Minutes\n"
                        +toLine(dayLength[6])
                ;

        return week;
    }

    /**
     * Calculates the duration of an event in minutes.
     *
     * @param event The event for which to calculate the duration.
     * @return The duration of the event in minutes.
     */
    static private int eventLength(Event event){
        int start=0;
        int end=0;
        start = event.getStartDate().getHours()*60 + event.getStartDate().getMinutes();
        end = event.getEndDate().getHours()*60 + event.getEndDate().getMinutes();

        return end-start;
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

}
