package com.wintecfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wintec.wintecfinal.R;

import java.util.ArrayList;
import java.util.List;

public class ManageStudentAdapter extends RecyclerView.Adapter<ManageStudentAdapter.MyViewHolder> {

    public List<Student> studentData = new ArrayList<>();
    Eventlistener mEventlistener;
    Context context;

    public ManageStudentAdapter(Context c) {
        this.context = c;
    }

    public void addAll(List<Student> mData) {
        studentData.clear();
        studentData.addAll(mData);
        notifyDataSetChanged();
    }

    public Student getItem(int position) {

        return studentData.get(position);
    }

    public void remove(int position) {
        studentData.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.manage_student_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Student student = studentData.get(position);
        holder.tvStudentName.setText(student.studentName);
        holder.tvStudentId.setText("Student Id : " + student.studentId);
        holder.tvStudentCourse.setText("Course : " + student.studentCourse);
//
//        if (!StringUtils.isEmpty(slider.slideimage)) {
//            imageLoader.displayImage(slider.slideimage, holder.imgView);
//        }
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventlistener != null) {
                    mEventlistener.onItemviewClick(position);
                }
            }
        });
        holder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEventlistener != null) {
                    mEventlistener.onItemEditClick(position, holder.imgMore);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return studentData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        View container;
        TextView tvStudentName;
        TextView tvStudentId;
        TextView tvStudentCourse;
        ImageView imgMore;

        public MyViewHolder(View view) {
            super(view);
            container = view.findViewById(R.id.container);
            tvStudentName = view.findViewById(R.id.tvStudentName);
            tvStudentId = view.findViewById(R.id.tvStudentId);
            tvStudentCourse = view.findViewById(R.id.tvStudentCourse);
            imgMore = view.findViewById(R.id.imgMore);
        }

    }

    public interface Eventlistener {

        void onItemviewClick(int position);

        void onItemEditClick(int position, ImageView imgMore);
    }

    public void setEventlistener(Eventlistener eventlistener) {

        this.mEventlistener = eventlistener;
    }

}
