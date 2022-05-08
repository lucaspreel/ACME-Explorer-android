package com.example.entregable1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entregable1.Entity.Enlace;
import com.example.entregable1.Entity.Trip;
import com.example.entregable1.MainActivity;
import com.example.entregable1.R;
import com.example.entregable1.TripDetailsActivity;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {

    private List<Trip> tripList;
    private Context context;

    public TripAdapter(List<Trip> tripList) {
        this.tripList = tripList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View tripView = layoutInflater.inflate(R.layout.trip_item, parent, false);
        return new ViewHolder(tripView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trip trip = tripList.get(position);

        holder.textViewDestination.setText(trip.getDestination());
        holder.textViewDescription.setText(trip.getDescription());

        if (trip.isSelected()) {
            holder.textViewIsSelected.setText("Viaje seleccionado");
        } else {
            holder.textViewIsSelected.setText("Viaje no seleccionado");
        }

        Picasso.get()
                .load(trip.getUrlImageViewTrip())
                .placeholder(android.R.drawable.ic_dialog_map)
                .error(android.R.drawable.ic_dialog_alert)
                .into(holder.imageViewTrip);

        holder.cardViewTrip.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, TripDetailsActivity.class);
            intent.putExtra("urlImage", trip.getUrlImageViewTrip());
            intent.putExtra("destination", trip.getDestination());
            intent.putExtra("price", trip.getPrice());
            intent.putExtra("startDate", trip.getStartDate());
            intent.putExtra("endDate", trip.getEndDate());
            intent.putExtra("startPlace", trip.getStartPlace());
            intent.putExtra("isSelected", trip.isSelected());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDestination;
        TextView textViewDescription;
        TextView textViewIsSelected;
        ImageView imageViewTrip;
        CardView cardViewTrip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDestination = itemView.findViewById(R.id.textViewDestination);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewIsSelected = itemView.findViewById(R.id.textViewIsSelected);
            imageViewTrip = itemView.findViewById(R.id.imageViewTrip);
            cardViewTrip = itemView.findViewById(R.id.cardViewTrip);
        }
    }
}