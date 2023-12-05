package utilities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateAndTimeHelper {
    Context context;

    public DateAndTimeHelper(Context context) {
        this.context = context;
    }

    public static String formatDate(String date) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH);

        Date fDate = inputFormat.parse(date);
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.ENGLISH);

        return outputFormat.format(fDate);
    }

    public static String formatTime(String date) throws ParseException {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault());
        inputDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date formattedDate = inputDateFormat.parse(date);

        SimpleDateFormat outputFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        return outputFormat.format(formattedDate);
    }

    public void showDatePicker(EditText input) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    String formattedDate = handleSelectedDate(year, month, dayOfMonth);
                    try {
                        input.setText(formatDate(formattedDate));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    public void showTimePicker(EditText input) {
        Calendar calendar = Calendar.getInstance();

        // Create a TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                (TimePicker view, int hourOfDay, int minute) -> {
                    String formattedTime = handleSelectedTime(hourOfDay, minute);
                    input.setText(formattedTime);
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(context));

        timePickerDialog.show();
    }

    private String handleSelectedDate(int year, int month, int dayOfMonth) {
        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.set(year, month, dayOfMonth);
        Date selectedDate = selectedCalendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH);

        return dateFormat.format(selectedDate);
    }

    private String handleSelectedTime(int hourOfDay, int minute) {
        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        selectedCalendar.set(Calendar.MINUTE, minute);

        Date selectedTime = selectedCalendar.getTime();
        String outputTime = "";

        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

        try {
            Date date = inputFormat.parse(selectedTime.toString());

            SimpleDateFormat outputFormat = new SimpleDateFormat("h:mm a");

            outputFormat.setTimeZone(TimeZone.getDefault());

            outputTime = outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputTime;
    }

    public static String combineDateAndTime(String newDate, String newTime) {
        String finalOutput = "";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.ENGLISH);
            Date date = dateFormat.parse(newDate);

            SimpleDateFormat outputFormatterDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
            Date time = timeFormat.parse(newTime);

            SimpleDateFormat outputFormatterTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

            String outputDate = outputFormatterDate.format(date);
            String outputTime = outputFormatterTime.format(time);

            finalOutput = outputDate + "T" + outputTime + ":00.000+00:00";

            return finalOutput;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
