package com.example.doannt118;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doannt118.Class.TenCacTask;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RVDanhSachThe extends RecyclerView.Adapter<RVDanhSachThe.ViewHolder>{
    private ArrayList<TenCacTask> TenCacTasks;
    private Context context;
    private static RVDanhSachThe.onClickListner onclicklistner;
    private String email,project_name,task_list_name;
    public RVDanhSachThe(ArrayList<TenCacTask> TenCacTasks,Context context,String email,String project_name,String task_list_name)
    {
        this.TenCacTasks = TenCacTasks;
        this.context = context;
        this.email = email;
        this.project_name = project_name;
        this.task_list_name = task_list_name;
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
        public ImageView iv_project,iv_xoa_project;
        public LinearLayout linear;
        RVDanhSachThe adapter;
        public ViewHolder(View v) {
            super(v);
            tv_project_name = (TextView) v.findViewById(R.id.tv_project_name);
            iv_project = (ImageView) v.findViewById(R.id.iv_project);
            iv_xoa_project = (ImageView) v.findViewById(R.id.iv_xoa_project);
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
        holder.iv_xoa_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Bạn có muốn xóa thẻ này này không ?")
                        .setCancelable(false)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                removeAt(position);
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
//                    CongViec congViec = new CongViec(them_cong_viec,false);
                                reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").child(tenCacTask.getTen_cac_task()).removeValue();

                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(v.getContext(), "Bạn chọn không xóa thẻ này ",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });


                AlertDialog alert = builder.create();
                //Setting the title manually
//                alert.setTitle("AlertDialogExample");
                alert.show();

            }
        });

    }
    private void removeAt(int position) {
        TenCacTasks.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, TenCacTasks.size());
    }
    @Override
    public int getItemCount() {
        return TenCacTasks.size();
    }

}

