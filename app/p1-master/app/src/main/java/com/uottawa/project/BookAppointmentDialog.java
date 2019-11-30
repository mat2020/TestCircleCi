package com.uottawa.project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class BookAppointmentDialog extends DialogFragment {

    private String time;
    private BookingDialogListener listener;

    public BookAppointmentDialog(String time) {
        super();
        this.time = time;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Book Appointment")
                .setMessage("Do you want to book this appointment at "+this.time+"?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Book Appointment", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onBook(time);
                    }
                });
        return builder.create();
    }

    public interface BookingDialogListener {
        void onBook(String time);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (BookAppointmentDialog.BookingDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement NoticeDialogListener");
        }
    }
}
