package planIT;

import planIT.Events.*;
import planIT.Users.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class scheduleAnalysis {

    static public String measureWeeklyActivity(User user){
        List<Event> userSchedule = new ArrayList<>(user.getEvents());
        int[] dayNum = {0};
        int[] dayLength = {0};

        if(userSchedule.isEmpty()){
            return "Schedule is Empty";
        }
        //SORT EVENT BY DAY OF WEEK
        userSchedule.sort(new scheduleAnalysis.startDayComparator());

        //COLLECT DATA
        for(Event event: userSchedule){
            dayNum[event.getStartDate().getDay()] += 1;
            dayLength[event.getStartDate().getDay()] += eventLength(event);
        }

        String week =
                "Sunday:    " +dayNum[0] +" Events, Total of " +dayLength[0]/60 +" Hours " +dayLength[0]%60 +"Minutes\n"
                        +toLine(dayLength[0])
                        +"Monday:    " +dayNum[1] +" Events, Total of " +dayLength[1]/60 +" Hours " +dayLength[1]%60 +"Minutes\n"
                        +toLine(dayLength[1])
                        +"Tuesday:   " +dayNum[2] +" Events, Total of " +dayLength[2]/60 +" Hours " +dayLength[2]%60 +"Minutes\n"
                        +toLine(dayLength[0])
                        +"Wednesday: " +dayNum[3] +" Events, Total of " +dayLength[3]/60 +" Hours " +dayLength[3]%60 +"Minutes\n"
                        +toLine(dayLength[0])
                        +"Thursday:  " +dayNum[4] +" Events, Total of " +dayLength[4]/60 +" Hours " +dayLength[4]%60 +"Minutes\n"
                        +toLine(dayLength[0])
                        +"Friday:    " +dayNum[5] +" Events, Total of " +dayLength[5]/60 +" Hours " +dayLength[5]%60 +"Minutes\n"
                        +toLine(dayLength[0])
                        +"Saturday:  " +dayNum[6] +" Events, Total of " +dayLength[6]/60 +" Hours " +dayLength[6]%60 +"Minutes\n"
                        +toLine(dayLength[0])
                ;

        return "";
    }



    static private int eventLength(Event event){
        int start=0;
        int end=0;
        start = event.getStartDate().getHours()*60 + event.getStartDate().getMinutes();
        end = event.getEndDate().getHours()*60 + event.getEndDate().getMinutes();

        return end-start;
    }

    static private String toLine(int length){
        String line ="";
        for(int i=0; i<length/10; ++i){
            line += "-";
        }
        line += "\n";
        return line;
    }


    //COMPARATOR FOR EVENTS
    static private class startDayComparator implements Comparator<Event> {
        public int compare(Event event1, Event event2){
            if(event1.getStartDate().getDay() == event2.getStartDate().getDay()){
                return 0;
            }
            else if(event1.getStartDate().getDay() > event2.getStartDate().getDay()){
                return 1;
            }
            else{
                return -1;
            }
        }
    }

}
