package com.example.hotel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel.R;
import com.example.hotel.Hotel;

import java.util.List;

public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.HotelViewHolder> {

    private List<Hotel> hotels;
    private OnHotelSelectedListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public interface OnHotelSelectedListener {
        void onHotelSelected(Hotel hotel);
    }

    public HotelListAdapter(List<Hotel> hotels, OnHotelSelectedListener listener) {
        this.hotels = hotels;
        this.listener = listener;
    }

    @Override
    public HotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hotel, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotelViewHolder holder, int position) {
        Hotel hotel = hotels.get(position);
        holder.bind(hotel, position == selectedPosition);

        holder.itemView.setOnClickListener(view -> {
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged();
            listener.onHotelSelected(hotel);
        });
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    class HotelViewHolder extends RecyclerView.ViewHolder {
        TextView tvHotelName, tvPrice, tvAvailability;
        View rootLayout;

        HotelViewHolder(View itemView) {
            super(itemView);
            rootLayout = itemView.findViewById(R.id.rootLayout);
            tvHotelName = itemView.findViewById(R.id.tvHotelName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvAvailability = itemView.findViewById(R.id.tvAvailability);
        }

        void bind(Hotel hotel, boolean isSelected) {
            tvHotelName.setText(hotel.getHotelName());
            tvPrice.setText("Price: " + hotel.getPrice());
            tvAvailability.setText(hotel.isAvailability() ? "Available" : "Not Available");

            // 简单选中效果
            if (isSelected) {
                rootLayout.setBackgroundColor(0xFFE0E0E0); // 浅灰
            } else {
                rootLayout.setBackgroundColor(0xFFFFFFFF); // 白色
            }
        }
    }
}
