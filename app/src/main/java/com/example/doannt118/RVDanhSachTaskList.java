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
import com.example.doannt118.Class.TenDanhSachTask;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RVDanhSachTaskList extends RecyclerView.Adapter<RVDanhSachTaskList.ViewHolder>{
    private ArrayList<TenDanhSachTask> tenDanhSachTasks;
    private Context context;
    private static RVDanhSachTaskList.onClickListner onclicklistner;
    private String email,project_name;
    public RVDanhSachTaskList(ArrayList<TenDanhSachTask> tenDanhSachTasks,Context context,String email,String project_name)
    {
        this.tenDanhSachTasks = tenDanhSachTasks;
        this.context = context;
        this.email = email;
        this.project_name = project_name;

    }

    @Override
    public RVDanhSachTaskList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_danh_sach_task_list, parent, false);

        // Return a new holder instance
        RVDanhSachTaskList.ViewHolder viewHolder = new RVDanhSachTaskList.ViewHolder(contactView);
        return viewHolder;

    }
    class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public TextView tv_ten_danh_sach_task_list;
        public ImageView iv_project,iv_xoa_task_list;
        public LinearLayout linear;
        RVDanhSachTaskList adapter;
        public ViewHolder(View v) {
            super(v);
            tv_ten_danh_sach_task_list = (TextView) v.findViewById(R.id.tv_ten_danh_sach_task_list);
            iv_project = (ImageView) v.findViewById(R.id.iv_project);
            iv_xoa_task_list = (ImageView) v.findViewById(R.id.iv_xoa_task_list);
            linear = (LinearLayout) v.findViewById(R.id.ll_danh_sach_task_list);
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
    public void setOnItemClickListener(RVDanhSachTaskList.onClickListner onclicklistner) {
        RVDanhSachTaskList.onclicklistner = onclicklistner;
    }


    public interface onClickListner {
        void onItemClick(int position, View v);
//        void onItemLongClick(int position, View v);
    }



    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }
    @Override
    public void onBindViewHolder(RVDanhSachTaskList.ViewHolder holder, int position) {
        TenDanhSachTask tenCacTask = tenDanhSachTasks.get(position);
        if (tenCacTask.getTen_danh_sach_task()!=null)
        {
            holder.tv_ten_danh_sach_task_list.setText(tenCacTask.getTen_danh_sach_task());
        }
        holder.iv_xoa_task_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Bạn có muốn xóa danh sách này này không ?")
                        .setCancelable(false)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                removeAt(position);
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
//                    CongViec congViec = new CongViec(them_cong_viec,false);
                                reference.child(project_name).child("task_lists").child(tenCacTask.getTen_danh_sach_task()).removeValue();

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
        tenDanhSachTasks.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tenDanhSachTasks.size());
    }
    @Override
    public int getItemCount() {
        return tenDanhSachTasks.size();
    }

}

