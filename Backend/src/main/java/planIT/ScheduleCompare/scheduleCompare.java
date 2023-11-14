package planIT.ScheduleCompare;

import jakarta.validation.constraints.NotNull;
import planIT.Entity.Events.Event;
import planIT.Entity.Teams.Team;
import planIT.Entity.Users.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class scheduleCompare {

    static public String compareSchedule(Team team, Date rangeStart, Date rangeEnd){

        //SET DATE RANGE
        Event dateRange = new Event();
        dateRange.setStartDate(rangeStart);
        dateRange.setEndDate(rangeEnd);

        //USER EVENTS ADDED AND SORTED
        List<Event> schedule = new ArrayList<>();
        List<User> users = new ArrayList<>(team.getUsers());
        for (User user : users) {
            schedule.addAll(user.getEvents());
        }

        // FILTER AND SORT BY DATE
        schedule.removeIf(event -> !event.getStartDate().before(dateRange.getEndDate()) || !event.getEndDate().after(dateRange.getStartDate()));
        schedule.sort(new startDateComparator());

        //IF NO EVENTS
        if(schedule.isEmpty()){
            return("Available: \n  " + dateRange.getStartDate().toString() + " - " + dateRange.getEndDate().toString() + '\n');
        }

        //INITIALIZE UNAVAILABLE LIST
        List<Event> unavailable = new ArrayList<>();
        unavailable.add(new Event());
        unavailable.get(0).setStartDate(schedule.get(0).getStartDate());
        unavailable.get(0).setEndDate(schedule.get(0).getEndDate());

        //MISC
        int k=0;
        int i=0;
        boolean done=false;

        //MERGE USER EVENTS INTO UNAVAILABLE EVENTS
        for(i=0; i< schedule.size(); ++i){
            // CURRENT VS NEW EVENT;
            if( dateWithin(schedule.get(i).getStartDate(), dateRange )){
                done=false;
                k=0;
                while(!done){
                    if( dateWithin(schedule.get(i).getStartDate(), unavailable.get(k)) ){
                        //Extend current unavailable...
                        if(schedule.get(i).getStartDate().before( unavailable.get(k).getStartDate() )){
                            unavailable.get(k).setStartDate(schedule.get(i).getStartDate());
                        }
                        if(schedule.get(i).getEndDate().after( unavailable.get(k).getEndDate() )){
                            unavailable.get(k).setEndDate(schedule.get(i).getEndDate());
                        }
                        done=true;
                    }
                    else{
                        //iterate to next unavailable
                        ++k;
                        // if no next, creates new
                        if(k >= unavailable.size() ){
                            unavailable.add(new Event());
                            unavailable.get(k).setStartDate( schedule.get(i).getStartDate() );
                            unavailable.get(k).setEndDate( schedule.get(i).getEndDate() );
                            done =true;
                        }
                    }
                }
            }
            // CONSTRAIN BY TARGET RANGE
            if(unavailable.get(k).getStartDate().before(dateRange.getStartDate()) ){
                unavailable.get(k).setStartDate(dateRange.getStartDate() );
            }
            if(unavailable.get(k).getEndDate().after(dateRange.getEndDate()) ){
                unavailable.get(k).setEndDate(dateRange.getEndDate() );
            }
        }

        //GET AVAILABLE FROM UNAVAILABLE
        i=0;  //i=AVAILABLE
        k=0;  // k=UNAVAILABLE
        List<Event> available = new ArrayList<>();
        available.add(new Event());

        //START AVAILABLE
        if(unavailable.get(0).getStartDate() != dateRange.getStartDate()){
            available.get(0).setStartDate(dateRange.getStartDate());        //NO START CONFLICT
        } else {
            available.get(0).setStartDate(unavailable.get(0).getEndDate()); //START CONFLICT
            ++k;
        }
        if(k<unavailable.size()) {
            available.get(0).setEndDate(unavailable.get(k).getStartDate());
            ++i;
        }
        //AVAILABLE REPEATING
        while( k < unavailable.size() ){
            available.add( new Event() );
            available.get(i).setStartDate( unavailable.get(k).getEndDate() );
            ++k;
            if( k < unavailable.size() ){
                available.get(i).setEndDate( unavailable.get(k).getStartDate() );
                ++i;
            }
        }
        //FINAL AVAILABLE END DATE
        if(available.get(i).getEndDate() == null){
            available.get(i).setEndDate( dateRange.getEndDate() );
            if(available.get(i).getStartDate().equals(dateRange.getEndDate()) ){
                available.remove(i);
            }
        }

        //CONVERT TO STRING
        StringBuilder unavailability = new StringBuilder("Unavailable: \n");
        for (Event event : unavailable) {
            unavailability.append("  ").append(event.getStartDate().toString()).append(" - ").append(event.getEndDate().toString()).append('\n');
        }
        StringBuilder availability = new StringBuilder("Available: \n");
        for (Event event : available) {
            availability.append("  ").append(event.getStartDate().toString()).append(" - ").append(event.getEndDate().toString()).append('\n');
        }
        //availability = unavailability.append(availability);  //MERGE STRINGS
        return availability.toString();
    }

    //RETURNS AVAILABILITY AS STRING
    static public String compareStandard(List<Event> available){
        StringBuilder availability = new StringBuilder("Available: \n");
        for (Event event : available) {
            availability.append("  ").append(event.getStartDate().toString()).append(" - ").append(event.getEndDate().toString()).append('\n');
        }
        return availability.toString();
    }

    //RETURNS AVAILABILITY IN HALF HOUR INCREMENTS
    static public String compareBy30(Team team, Date rangeStart, Date rangeEnd){

        boolean done =false;
        StringBuilder toReturn = new StringBuilder("Available: \n");
        String temp = "";

        while(!done){
            Date newEnd = rangeStart;
            newEnd.setMinutes(rangeStart.getMinutes()+30);

            temp = compareSchedule(team, rangeStart, newEnd);

            if(temp.length()>15){
                temp = temp.substring(12);
                toReturn.append(temp);
            }

            rangeStart = newEnd;

            if(newEnd.after(rangeEnd)){
                done=true;
            }
        }
        return toReturn.toString();
    }


    //HELPER METHOD, CHECKS FOR DATE INSIDE EVENT START/END
    static public boolean dateWithin(Date date, Event event) {
        return ((event.getStartDate().before(date) && event.getEndDate().after(date) )
                || event.getStartDate().equals(date) || event.getEndDate().equals(date) );
    }


    //COMPARATOR FOR EVENTS
    static private class startDateComparator implements Comparator<Event> {
        public int compare(Event event1, Event event2){
            if(event1.getStartDate().equals(event2.getStartDate())){
                return 0;
            }
            else if(event1.getStartDate().after(event2.getStartDate())){
                return 1;
            }
            else{
                return -1;
            }
        }
    }
}