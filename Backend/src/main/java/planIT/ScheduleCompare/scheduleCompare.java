package planIT.ScheduleCompare;

import planIT.Entity.Events.Event;
import planIT.Entity.Teams.Team;
import planIT.Entity.Users.User;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class for comparing user schedules to determine availability
 */
public class scheduleCompare {

    /**
     * Finds all events across all members of a team and returns the times that all members are available for a certain time period.
     * @param team team to be compared
     * @param rangeStart beginning of the date range
     * @param rangeEnd end of the date range
     * @return returns the availability as a List<Event>
     */
    static public List<Event> compareSchedule(Team team, Date rangeStart, Date rangeEnd) {

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
        if (schedule.isEmpty()) {
            return schedule;
        }

        //INITIALIZE UNAVAILABLE LIST
        List<Event> unavailable = new ArrayList<>();
        unavailable.add(new Event());
        unavailable.get(0).setStartDate(schedule.get(0).getStartDate());
        unavailable.get(0).setEndDate(schedule.get(0).getEndDate());

        //MISC
        int k = 0;
        int i = 0;
        boolean done = false;

        //MERGE USER EVENTS INTO UNAVAILABLE EVENTS
        for (i = 0; i < schedule.size(); ++i) {
            // CURRENT VS NEW EVENT;
            if (dateWithin(schedule.get(i).getStartDate(), dateRange)) {
                done = false;
                k = 0;
                while (!done) {
                    if (dateWithin(schedule.get(i).getStartDate(), unavailable.get(k))) {
                        //Extend current unavailable...
                        if (schedule.get(i).getStartDate().before(unavailable.get(k).getStartDate())) {
                            unavailable.get(k).setStartDate(schedule.get(i).getStartDate());
                        }
                        if (schedule.get(i).getEndDate().after(unavailable.get(k).getEndDate())) {
                            unavailable.get(k).setEndDate(schedule.get(i).getEndDate());
                        }
                        done = true;
                    } else {
                        //iterate to next unavailable
                        ++k;
                        // if no next, creates new
                        if (k >= unavailable.size()) {
                            unavailable.add(new Event());
                            unavailable.get(k).setStartDate(schedule.get(i).getStartDate());
                            unavailable.get(k).setEndDate(schedule.get(i).getEndDate());
                            done = true;
                        }
                    }
                }
            }
            // CONSTRAIN BY TARGET RANGE
            if (unavailable.get(k).getStartDate().before(dateRange.getStartDate())) {
                unavailable.get(k).setStartDate(dateRange.getStartDate());
            }
            if (unavailable.get(k).getEndDate().after(dateRange.getEndDate())) {
                unavailable.get(k).setEndDate(dateRange.getEndDate());
            }
        }

        //GET AVAILABLE FROM UNAVAILABLE
        i = 0;  //i=AVAILABLE
        k = 0;  // k=UNAVAILABLE
        List<Event> available = new ArrayList<>();
        available.add(new Event());

        //START AVAILABLE
        if (unavailable.get(0).getStartDate() != dateRange.getStartDate()) {
            available.get(0).setStartDate(dateRange.getStartDate());        //NO START CONFLICT
        } else {
            available.get(0).setStartDate(unavailable.get(0).getEndDate()); //START CONFLICT
            ++k;
        }
        if (k < unavailable.size()) {
            available.get(0).setEndDate(unavailable.get(k).getStartDate());
            ++i;
        }
        //AVAILABLE REPEATING
        while (k < unavailable.size()) {
            available.add(new Event());
            available.get(i).setStartDate(unavailable.get(k).getEndDate());
            ++k;
            if (k < unavailable.size()) {
                available.get(i).setEndDate(unavailable.get(k).getStartDate());
                ++i;
            }
        }
        //FINAL AVAILABLE END DATE
        if (available.get(i).getEndDate() == null) {
            available.get(i).setEndDate(dateRange.getEndDate());
            if (available.get(i).getStartDate().equals(dateRange.getEndDate())) {
                available.remove(i);
            }
        }

        //CONVERT TO STRING
        /*
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
         */
        return available;
    }

    //RETURNS AVAILABILITY AS STRING
    /**
     * Calls the 'compareSchedule' function and formats the results into a String
     * @param team team to be compared
     * @param rangeStart beginning of the date range
     * @param rangeEnd end of the date range
     * @return returns the availability as a String
     */
    static public String compareStandard(Team team, Date rangeStart, Date rangeEnd) {

        List<Event> available = compareSchedule(team, rangeStart, rangeEnd);

        //IF NO EVENTS
        if (available.isEmpty()) {
            return ("Available: \n  " + rangeStart.toString() + " - " + rangeEnd.toString() + '\n');
        }

        //OTHERWISE
        StringBuilder availability = new StringBuilder("Available: \n");
        for (Event event : available) {
            availability.append("  ").append(formatDate(event.getStartDate())).append(" - ").append(formatDate(event.getEndDate())).append('\n');
        }

        return availability.toString();
    }

    //RETURNS AVAILABILITY IN HALF HOUR INCREMENTS
    // WORK IN PROGRESS....
    /**
     * Calls the 'scheduleCompare' function, breaks it down into 30 minute increments, and formats it into a string.
     * @param team team to be compared
     * @param rangeStart beginning of the date range
     * @param rangeEnd end of the date range
     * @return returns the availability as a String
     */
    static public String compareBy30(Team team, Date rangeStart, Date rangeEnd) {

        boolean done = false;
        StringBuilder toReturn = new StringBuilder("Available: \n");
        String temp = "";

        while (!done) {
            Date newEnd = rangeStart;
            newEnd.setMinutes(rangeStart.getMinutes() + 30);

            temp = compareStandard(team, rangeStart, newEnd);

            if (temp.length() > 15) {
                temp = temp.substring(12);
                toReturn.append(temp);
            }

            rangeStart = newEnd;

            if (newEnd.after(rangeEnd)) {
                done = true;
            }
        }
        return toReturn.toString();
    }


    //HELPER METHOD, CHECKS FOR DATE INSIDE EVENT START/END
    /**
     * Determines if a single date is within the start and end dates of an event.
     * @param date date to be examined
     * @param event provides a start and end date to compare
     * @return true/false
     */
    static public boolean dateWithin(Date date, Event event) {
        if (date == null || event.getStartDate() == null) {
            return false;
        }
        return ((event.getStartDate().before(date) && event.getEndDate().after(date))
                || event.getStartDate().equals(date) || event.getEndDate().equals(date));
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

    //FORMAT DATE STRING
    /**
     * Returns a date as a consistent String
     * @param inputDate date to be converted
     * @return String based on specified format
     */
    private static String formatDate(Date inputDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.US);
        return sdf.format(inputDate);
    }

}