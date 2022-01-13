package com.example.doannt118;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doannt118.Class.CongViec;
import com.example.doannt118.Class.TenDuAn;

import java.util.ArrayList;

public class RVDanhSachCongViec extends RecyclerView.Adapter<RVDanhSachCongViec.ViewHolder>{
    private ArrayList<CongViec> congViecs;
    private Context context;
    boolean isOnTextChanged = false;
//    private static RVDanhSachCongViec.onClickListner onclicklistner;
    public RVDanhSachCongViec(ArrayList<CongViec> congViecs,Context context)
    {
        this.congViecs = congViecs;
        this.context = context;
    }

    @Override
    public RVDanhSachCongViec.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_cong_viec, parent, false);

        // Return a new holder instance
        RVDanhSachCongViec.ViewHolder viewHolder = new RVDanhSachCongViec.ViewHolder(contactView);
        return viewHolder;

    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox cb_done;
        public ImageView iv_delete;
        public LinearLayout linear;
        public EditText ed_ten_cong_viec;
        RVDanhSachCongViec adapter;
        public ViewHolder(View v) {
            super(v);
            cb_done = (CheckBox) v.findViewById(R.id.cb_done);
            iv_delete = (ImageView) v.findViewById(R.id.iv_xoa_cong_viec);
            linear = (LinearLayout) v.findViewById(R.id.ll_danh_sach_cong_viec);
            ed_ten_cong_viec = (EditText) v.findViewById(R.id.ed_ten_cong_viec);
//            v.setOnClickListener(this);
//            v.setOnLongClickListener(this);

        }

    }




    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }
    @Override
    public void onBindViewHolder(RVDanhSachCongViec.ViewHolder holder, int position) {
        CongViec congViec = congViecs.get(position);
        if (congViec.getTen_cong_viec()!=null)
        {
            holder.ed_ten_cong_viec.setText(congViec.getTen_cong_viec());
        }
        holder.cb_done.setChecked(congViec.getDone());
        holder.cb_done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                congViec.setDone(isChecked);
            }
        });

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                congViecs.remove(position);

            }
        });
        holder.ed_ten_cong_viec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return congViecs.size();
    }

}
