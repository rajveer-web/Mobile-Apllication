package com.wintecfinal;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.wintec.wintecfinal.R;

public class StudentViewSubActivity extends AppCompatActivity {

    RecyclerView rvCourseList;
    CourseAdapter courseAdapter;
    TextView editCourse, editYears;
    LinearLayout llSubmit;
    TextView btnSubmitNewModule, tvNoModuleData;

    String selectedCourse = "";
    String selectedYear = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_subject);

        editCourse = findViewById(R.id.editCourse);
        editYears = findViewById(R.id.editYears);
        llSubmit = findViewById(R.id.llSubmit);
        btnSubmitNewModule = findViewById(R.id.btnSubmitNewModule);
        tvNoModuleData = findViewById(R.id.tvNoModuleData);


        rvCourseList = findViewById(R.id.rvCourseList);
        rvCourseList.setLayoutManager(new GridLayoutManager(this, 2));
        courseAdapter = new CourseAdapter(StudentViewSubActivity.this);
        rvCourseList.setAdapter(courseAdapter);


        String key = getIntent().getExtras().getString("key");

        if (key.equals("webdev")) {
            editCourse.setText("Web Development");
            selectedCourse = "Web Development";
        } else if (key.equals("netEng")) {
            editCourse.setText("Networking");
            selectedCourse = "Networking";
        } else if (key.equals("softEng")) {
            editCourse.setText("Software Engineering");
            selectedCourse = "Software Engineering";
        } else if (key.equals("database")) {
            editCourse.setText("Database");
            selectedCourse = "Database";
        }

        PopupMenu popupCourse = new PopupMenu(getActivity(), editCourse);
        popupCourse.getMenuInflater().inflate(R.menu.sub_menu, popupCourse.getMenu());
        popupCourse.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                editCourse.setText(item.getTitle());
                selectedCourse = item.getTitle().toString();
                courseAdapter.addAll(Utils.getCourseData(getActivity(), selectedYear, selectedCourse));
                refreshPlaceHolder();
                return true;
            }
        });

        editCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupCourse.show();
            }
        });

        editYears.setText("Year 1");
        selectedYear = "Year 1";

        PopupMenu popupYear = new PopupMenu(getActivity(), editYears);
        popupYear.getMenuInflater().inflate(R.menu.year_menu, popupYear.getMenu());
        popupYear.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                editYears.setText(item.getTitle());
                selectedYear = item.getTitle().toString();
                courseAdapter.addAll(Utils.getCourseData(getActivity(), selectedYear, selectedCourse));
                refreshPlaceHolder();
                return true;
            }
        });

        editYears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.setUnlocked(getActivity(), selectedYear, selectedCourse);
                popupYear.show();
            }
        });

//        btnSubmitNewModule.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (validate()) {
//                    courseAdapter.addAll(Utils.getCourseData(getActivity(), selectedYear, selectedCourse));
//                    refreshPlaceHolder();
//                }
//            }
//        });

        courseAdapter.setEventlistener(new CourseAdapter.Eventlistener() {
            @Override
            public void onItemviewClick(int position) {
                CourseModule data = courseAdapter.getItem(position);
                CustomDialogClass cdd = new CustomDialogClass(getActivity(), data);
                cdd.show();
            }

            @Override
            public void onCheckedChangeListner(int position) {
                CourseModule data = courseAdapter.getItem(position);
                data.isCourseChecked = !data.isCourseChecked;
                if (data.isCourseChecked) {
                    Utils.setChecked(getActivity(), data, true);
                } else {
                    Utils.setChecked(getActivity(), data, false);
                }
                courseAdapter.notifyDataSetChanged();
//                if (isChecked) {
//                    Toast.makeText(getActivity(), "checked", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getActivity(), "not checked", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        Utils.setUnlockedFirstYear(getActivity(), selectedYear, selectedCourse);
        courseAdapter.addAll(Utils.getCourseData(getActivity(), selectedYear, selectedCourse));
        refreshPlaceHolder();
        initBack(true);
    }

    public StudentViewSubActivity getActivity() {
        return this;
    }

    public void refreshPlaceHolder() {
        if (rvCourseList.getAdapter().getItemCount() > 0) {
            tvNoModuleData.setVisibility(View.GONE);
            rvCourseList.setVisibility(View.VISIBLE);
        } else {
            tvNoModuleData.setVisibility(View.VISIBLE);
            rvCourseList.setVisibility(View.GONE);
        }
    }

    private boolean validate() {
        if (selectedCourse.isEmpty()) {
            Toast.makeText(getActivity(), "Select course first", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (selectedYear.isEmpty()) {
            Toast.makeText(getActivity(), "Select year first", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public class CustomDialogClass extends Dialog {
        public Activity c;
        public TextView tvReadMore, tvModuleCode, tvModuleTitle, tvModulePrescription, tvPrograms, tvNzqaLevel, tvNzqaCredits;
        CourseModule courseModule;

        public CustomDialogClass(Activity a, CourseModule courseModuleL) {
            super(a);
            this.c = a;
            this.courseModule = courseModuleL;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog__view_sub);
            tvReadMore = (TextView) findViewById(R.id.tvReadMore);
            tvModuleCode = (TextView) findViewById(R.id.tvModuleCode);
            tvModuleTitle = (TextView) findViewById(R.id.tvModuleTitle);
            tvModulePrescription = (TextView) findViewById(R.id.tvModulePrescription);
            tvPrograms = (TextView) findViewById(R.id.tvPrograms);
            tvNzqaLevel = (TextView) findViewById(R.id.tvNzqaLevel);
            tvNzqaCredits = (TextView) findViewById(R.id.tvNzqaCredits);

            tvModuleCode.setText(courseModule.moduleCode);
            tvModuleTitle.setText(courseModule.moduleTitle);
            tvModulePrescription.setText(courseModule.modulePrescription);
            tvPrograms.setText(courseModule.moduleProgram);
            tvNzqaLevel.setText(courseModule.NZQALevel);
            tvNzqaCredits.setText(courseModule.NZQACredit);

            tvReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), SubjectViewMoreActivity.class);
                    i.putExtra("data", new Gson().toJson(courseModule));
                    startActivity(i);
                }
            });
        }
    }

    public void initBack(boolean b) {
        ImageView imgBack = (ImageView) findViewById(R.id.imgBack);

        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}