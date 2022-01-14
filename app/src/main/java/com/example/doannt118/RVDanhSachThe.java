package com.example.doannt118;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doannt118.Class.TenCacTask;

import java.util.ArrayList;

public class RVDanhSachThe extends RecyclerView.Adapter<RVDanhSachThe.ViewHolder>{
    private ArrayList<TenCacTask> TenCacTasks;
    private Context context;
    private static RVDanhSachThe.onClickListner onclicklistner;
    public RVDanhSachThe(ArrayList<TenCacTask> TenCacTasks,Context context)
    {
        this.TenCacTasks = TenCacTasks;
        this.context = context;
    }

    @Override
    public RVDanhSachThe.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_du_an, parent, false);

        // Return a new holder instance
        RVDanhSachThe.ViewHolder viewHolder = new RVDanhSachThe.ViewHolder(contactView);
        return viewHolder;

    }
    class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public TextView tv_project_name;
        public ImageView iv_project;
        public LinearLayout linear;
        RVDanhSachThe adapter;
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
    public void setOnItemClickListener(RVDanhSachThe.onClickListner onclicklistner) {
        RVDanhSachThe.onclicklistner = onclicklistner;
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
    public void onBindViewHolder(RVDanhSachThe.ViewHolder holder, int position) {
        TenCacTask tenCacTask = TenCacTasks.get(position);
        if (tenCacTask.getTen_cac_task()!=null)
        {
            holder.tv_project_name.setText(tenCacTask.getTen_cac_task());
        }
//        holder.iv_project.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return TenCacTasks.size();
    }

}

