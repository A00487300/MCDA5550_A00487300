package com.example.hotel;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.google.gson.annotations.SerializedName;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel.R;
import com.example.hotel.Guest;

import java.util.ArrayList;
import java.util.List;

public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder> {

    private int guestCount;
    private List<Guest> guestsData;

    public GuestListAdapter(int guestCount) {
        this.guestCount = guestCount;
        this.guestsData = new ArrayList<>();
        // 初始化 guestCount 个空Guest对象
        for (int i = 0; i < guestCount; i++) {
            guestsData.add(new Guest("", ""));
        }
    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_guest, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        holder.bind(guestsData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return guestCount;
    }

    // 暴露给外部，方便提交时获取全部 Guest 数据
    public List<Guest> getGuestsData() {
        return guestsData;
    }

    class GuestViewHolder extends RecyclerView.ViewHolder {

        EditText etGuestName;
        RadioGroup rgGender;
        RadioButton rbMale, rbFemale;

        public GuestViewHolder(View itemView) {
            super(itemView);
            etGuestName = itemView.findViewById(R.id.etGuestName);
            rgGender = itemView.findViewById(R.id.rgGender);
            rbMale = itemView.findViewById(R.id.rbMale);
            rbFemale = itemView.findViewById(R.id.rbFemale);
        }

        void bind(final Guest guest, final int position) {
            // 回显：避免 RecyclerView 重用 ViewHolder 时数据丢失
            etGuestName.setText(guest.getGuestName());
            if ("Male".equals(guest.getGender())) {
                rgGender.check(R.id.rbMale);
            } else if ("Female".equals(guest.getGender())) {
                rgGender.check(R.id.rbFemale);
            } else {
                rgGender.clearCheck();
            }

            // 监听名称输入
            etGuestName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    guestsData.get(position).setGuestName(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) { }
            });

            // 监听性别选项
            rgGender.setOnCheckedChangeListener((radioGroup, checkedId) -> {
                String gender = "";
                if (checkedId == R.id.rbMale) {
                    gender = "Male";
                } else if (checkedId == R.id.rbFemale) {
                    gender = "Female";
                }
                guestsData.get(position).setGender(gender);
            });
        }
    }
}
