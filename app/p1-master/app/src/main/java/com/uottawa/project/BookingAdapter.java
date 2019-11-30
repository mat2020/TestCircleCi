package com.uottawa.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    public class BookingViewHolder extends RecyclerView.ViewHolder {

        private TextView time;

        public  BookingViewHolder(View itemView) {
            super(itemView);

            this.time = (TextView) itemView.findViewById(R.id.time);
        }
    }

    private List<String> slots;
    private BookAppointment.OnBookingClick click;

    public BookingAdapter(List<String> slots, BookAppointment.OnBookingClick click) {
        this.slots = slots;
        this.click = click;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.book_appointments_view, parent, false);
        BookingAdapter.BookingViewHolder vh = new BookingViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final BookingViewHolder holder, int position) {
        holder.time.setText(this.slots.get(position));
        holder.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.clicked(holder.time.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.slots.size();
    }
}
