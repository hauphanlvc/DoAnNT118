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
import com.example.doannt118.Class.TenThanhVienThe;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RVDanhSachCongViec extends RecyclerView.Adapter<RVDanhSachCongViec.ViewHolder>{
    private ArrayList<CongViec> congViecs;
    private Context context;
    boolean isOnTextChanged = false;
    private String email,project_name,task_list_name,task_name;
    //    private static RVDanhSachCongViec.onClickListner onclicklistner;
    public RVDanhSachCongViec(ArrayList<CongViec> congViecs, Context context,String email,String project_name,String task_list_name,String task_name)
    {
        this.congViecs = congViecs;
        this.context = context;
        this.email = email;
        this.project_name = project_name;
        this.task_list_name = task_list_name;
        this.task_name = task_name;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_cong_viec, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
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
            ed_ten_cong_viec.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int position = getAdapterPosition();
                    CongViec congViec = congViecs.get(position);
                    String old_ten_cong_viec = congViec.getTen_cong_viec();
                    String new_ten_cong_viec = s.toString();
                    if (!old_ten_cong_viec.equals(new_ten_cong_viec)) {
                        congViec.setTen_cong_viec(new_ten_cong_viec);

                        CongViec new_congViec = new CongViec(new_ten_cong_viec, congViec.getDone());
                        congViecs.set(position, congViec);
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");

                        reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").child(task_name).child("work_checklist").child(old_ten_cong_viec).removeValue();

                        reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").child(task_name).child("work_checklist").child(new_ten_cong_viec).setValue(congViec);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });
        }

    }




    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
                congViecs.set(position,congViec);
                notifyDataSetChanged();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
//                    CongViec congViec = new CongViec(them_cong_viec,false);

                reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").child(task_name).child("work_checklist").child(congViec.getTen_cong_viec()).setValue(congViecs.get(position));

            }
        });
//

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(position);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("project");
//                    CongViec congViec = new CongViec(them_cong_viec,false);
                reference.child(project_name).child("task_lists").child(task_list_name).child("tasks").child(task_name).child("work_checklist").child(congViec.getTen_cong_viec()).removeValue();

            }
        });

    }




    @Override
    public int getItemCount() {
        return congViecs.size();
    }
    public ArrayList<CongViec> getCongViecs()
    {
        return congViecs;
    }
    private void removeAt(int position) {
        congViecs.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, congViecs.size());
    }

}


