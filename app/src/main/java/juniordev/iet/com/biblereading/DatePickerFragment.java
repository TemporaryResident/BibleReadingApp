package juniordev.iet.com.biblereading;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "juniordev.iet.com.biblereading" +
            "datepickerfragment.date";
    private static final String ARG_DATE = "date";

    private DatePicker mDatePicker;

    public static DatePickerFragment newInstance(Date date) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_calendar, null);

        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDatePicker = ((DatePicker) view.findViewById(R.id.dialog_date_picker));
        mDatePicker.init(year, month, day, null);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Ewangelia na: ")
                .setPositiveButton(android.R.string.ok, (dialog,  which) -> {
                    int nYear = mDatePicker.getYear();
                    int nMonth = mDatePicker.getMonth();
                    int nDay = mDatePicker.getDayOfMonth();

                    Date nDate = new GregorianCalendar(nYear, nMonth, nDay).getTime();
                    sendResult(Activity.RESULT_OK, nDate);
                })
                .create();
    }

    private void sendResult(int resultCode, Date date){
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
