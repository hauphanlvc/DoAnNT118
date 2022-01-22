package com.example.doannt118;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doannt118.Class.TenDuAn;

import java.util.ArrayList;

public class RecyleViewDanhSachDuAn extends RecyclerView.Adapter<RecyleViewDanhSachDuAn.ViewHolder>{
    private ArrayList<TenDuAn> duAns;
    private Context context;
    private static onClickListner onclicklistner;
    public RecyleViewDanhSachDuAn(ArrayList<TenDuAn> duAns,Context context)
    {
        this.duAns = duAns;
        this.context = context;
    }

    @Override
    public RecyleViewDanhSachDuAn.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_du_an, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;

    }
    class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public  TextView tv_project_name;
                public ImageView iv_project;
        public LinearLayout linear;
        RecyleViewDanhSachDuAn adapter;
        public ViewHolder(View v) {
            super(v);
            tv_project_name = (TextView) v.findViewById(R.id.tv_project_name);
            iv_project = (ImageView) v.findViewById(R.id.iv_project);

            linear = (LinearLayout) v.findViewById(R.id.ll_danh_sach_du_an);
            v.setOnClickListener(this);
//            v.setOnLongClickListener(this);

        }
        @Override
        public void onClick(View v) {
            onclicklistner.onItemClick(getAdapterPosition(), v);
        }

//        @Override
//        public boolean onLongClick(View v) {
//            onclicklistner.onItemLongClick(	getLayoutPosition(), v);
//            return true;
//        }
    }
    public void setOnItemClickListener(onClickListner onclicklistner) {
        RecyleViewDanhSachDuAn.onclicklistner = onclicklistner;
    }
//
//    public void setHeader(View v) {
//        this.headerView = v;
//    }

    public interface onClickListner {
        void onItemClick(int position, View v);
//        void onItemLongClick(int position, View v);
    }



    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }
    @Override
    public void onBindViewHolder(RecyleViewDanhSachDuAn.ViewHolder holder, int position) {
        TenDuAn tenDuAn = duAns.get(position);
        if (tenDuAn.getTen_Du_An()!=null)
        {
            holder.tv_project_name.setText(tenDuAn.getTen_Du_An());
        }
//        holder.iv_project.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return duAns.size();
    }

}
