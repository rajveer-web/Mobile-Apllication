package com.wintecfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wintec.wintecfinal.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by om on 7/7/2017.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {

    public List<CourseModule> courseModules = new ArrayList<>();
    Eventlistener mEventlistener;
    Context context;

    public CourseAdapter(Context c) {
        this.context = c;
    }

    public void addAll(List<CourseModule> mData) {
        courseModules.clear();
        courseModules.addAll(mData);
        notifyDataSetChanged();
    }

    public CourseModule getItem(int position) {
        return courseModules.get(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.course_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final CourseModule slider = courseModules.get(position);
        holder.tvCourseCode.setText(slider.moduleCode);
        holder.tvCourseTitle.setText(slider.moduleTitle);
        holder.tvCourseLevel.setText(slider.NZQALevel);
        holder.tvCourseCredit.setText(slider.NZQACredit);
        holder.cbCourse.setChecked(slider.isCourseChecked);

        if (slider.isCourseUnlocked) {
            holder.imgLocked.setVisibility(View.GONE);
            holder.llCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mEventlistener != null) {
                        mEventlistener.onCheckedChangeListner(position);
                    }
                }
            });
            holder.llViewCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mEventlistener != null) {
                        mEventlistener.onItemviewClick(position);
                    }
                }
            });
        } else {
            holder.imgLocked.setVisibility(View.VISIBLE);
            holder.llCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.llViewCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return courseModules.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvCourseCode;
        TextView tvCourseTitle;
        TextView tvCourseLevel;
        TextView tvCourseCredit;
        CheckBox cbCourse;
        FrameLayout container;
        LinearLayout llCheck;
        LinearLayout llViewCourse;
        ImageView imgLocked;

        public MyViewHolder(View view) {
            super(view);
            tvCourseCode = view.findViewById(R.id.tvCourseCode);
            tvCourseTitle = view.findViewById(R.id.tvCourseTitle);
            tvCourseLevel = view.findViewById(R.id.tvCourseLevel);
            tvCourseCredit = view.findViewById(R.id.tvCourseCredit);
            cbCourse = view.findViewById(R.id.cbCourse);
            container = view.findViewById(R.id.container);
            llCheck = view.findViewById(R.id.llCheck);
            llViewCourse = view.findViewById(R.id.llViewCourse);
            imgLocked = view.findViewById(R.id.imgLocked);
        }

    }

    public interface Eventlistener {
        void onItemviewClick(int position);

        void onCheckedChangeListner(int position);
    }

    public void setEventlistener(Eventlistener eventlistener) {
        this.mEventlistener = eventlistener;
    }

}
