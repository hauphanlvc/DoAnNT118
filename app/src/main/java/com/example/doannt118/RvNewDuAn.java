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
import com.example.doannt118.Class.TenDuAn;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RvNewDuAn extends RecyclerView.Adapter<RvNewDuAn.ViewHolder>{
    private ArrayList<TenDuAn> tenDuAns;
    private Context context;
    private static RvNewDuAn.onClickListner onclicklistner;
    private String email,project_name,task_list_name;    public RvNewDuAn(ArrayList<TenDuAn> tenDuAns,Context context)
    {
        this.tenDuAns = tenDuAns;
        this.context = context;

    }

    @Override
    public RvNewDuAn.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_du_an, parent, false);

        // Return a new holder instance
        RvNewDuAn.ViewHolder viewHolder = new RvNewDuAn.ViewHolder(contactView);
        return viewHolder;

    }
    class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public TextView tv_project_name;
        public ImageView iv_project,iv_xoa_project;
        public LinearLayout linear;
        RvNewDuAn adapter;
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
    public void setOnItemClickListener(RvNewDuAn.onClickListner onclicklistner) {
        RvNewDuAn.onclicklistner = onclicklistner;
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
    public void onBindViewHolder(RvNewDuAn.ViewHolder holder, int position) {
        TenDuAn tenDuAn = tenDuAns.get(position);
        if (tenDuAn.getTen_Du_An()!=null)
        {
            holder.tv_project_name.setText(tenDuAn.getTen_Du_An());
        }
        holder.iv_xoa_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Bạn có muốn xóa dự án này không ?")
                        .setCancelable(false)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                removeAt(position);
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
//                    CongViec congViec = new CongViec(them_cong_viec,false);
                                reference.child(tenDuAn.getTen_Du_An()).removeValue();

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
        tenDuAns.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tenDuAns.size());
    }
    @Override
    public int getItemCount() {
        return tenDuAns.size();
    }

}

