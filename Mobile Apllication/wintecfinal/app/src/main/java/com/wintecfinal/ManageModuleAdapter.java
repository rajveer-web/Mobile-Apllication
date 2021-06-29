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


/**
 * Created by om on 7/7/2017.
 */

public class ManageModuleAdapter extends RecyclerView.Adapter<ManageModuleAdapter.MyViewHolder> {

    public List<CourseModule> courseModules = new ArrayList<>();
    Eventlistener mEventlistener;
    Context context;

    public ManageModuleAdapter(Context c) {
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

    public void remove(int position) {
        courseModules.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.manage_module_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final CourseModule courseModule = courseModules.get(position);
        holder.tvCourseCode.setText(courseModule.moduleCode);
        holder.tvCourseTitle.setText(courseModule.moduleTitle);
        holder.tvCourseLevel.setText("Level : " + courseModule.NZQALevel);
        holder.tvCourseCredit.setText("Credit : " + courseModule.NZQACredit);

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
        return courseModules.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        View container;
        TextView tvCourseTitle;
        TextView tvCourseLevel;
        TextView tvCourseCredit;
        TextView tvCourseCode;
        ImageView imgMore;

        public MyViewHolder(View view) {
            super(view);
            container = view.findViewById(R.id.container);
            tvCourseTitle = view.findViewById(R.id.tvCourseTitle);
            tvCourseLevel = view.findViewById(R.id.tvCourseLevel);
            tvCourseCredit = view.findViewById(R.id.tvCourseCredit);
            tvCourseCode = view.findViewById(R.id.tvCourseCode);
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
