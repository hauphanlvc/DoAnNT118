package com.example.doannt118;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doannt118.Class.TenThanhVienThe;

import java.util.ArrayList;

public class RVAdapterDanhSachThanhVienTheDiaglog extends RecyclerView.Adapter<RVAdapterDanhSachThanhVienTheDiaglog.ViewHolder>{
    private ArrayList<TenThanhVienThe> tenThanhVienThes;
    private Context context;

    //    private static RVAdapterDanhSachThanhVienTheDiaglog.onClickListner onclicklistner;
    public RVAdapterDanhSachThanhVienTheDiaglog(ArrayList<TenThanhVienThe> tenThanhVienThes, Context context)
    {
        this.tenThanhVienThes = tenThanhVienThes;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_thanh_vien_the, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;

    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ten_thanh_vien_the;
        ImageView iv_is_added;

        RVAdapterDanhSachThanhVienTheDiaglog adapter;
        public ViewHolder(View v) {
            super(v);
                tv_ten_thanh_vien_the = v.findViewById(R.id.tv_ten_thanh_vien_the);
                iv_is_added = v.findViewById(R.id.iv_is_added);
                iv_is_added.getTag(1);
        }

    }


    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TenThanhVienThe tenThanhVienThe = tenThanhVienThes.get(position);
        holder.tv_ten_thanh_vien_the.setText(tenThanhVienThe.getTen_thanh_vien());
        if ( tenThanhVienThe.isIs_added())
        {
            holder.iv_is_added.setImageResource(R.drawable.complete);
        }
        else
        {
            holder.iv_is_added.setImageResource(android.R.drawable.ic_input_add);
        }
        holder.iv_is_added.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tenThanhVienThe.isIs_added()==false)
                {
                    holder.iv_is_added.setImageResource(android.R.drawable.ic_input_add);
                    tenThanhVienThe.setIs_added(true);
                    tenThanhVienThes.set(position,tenThanhVienThe);
                    notifyDataSetChanged();
                }
                else
                {
                    holder.iv_is_added.setImageResource(R.drawable.complete);
                    tenThanhVienThe.setIs_added(false);
                    tenThanhVienThes.set(position,tenThanhVienThe);
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return tenThanhVienThes.size();
    }
    public ArrayList<TenThanhVienThe> getTenThanhVienThes()
    {
        return tenThanhVienThes;
    }
    private void removeAt(int position) {
        tenThanhVienThes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tenThanhVienThes.size());
    }
}

