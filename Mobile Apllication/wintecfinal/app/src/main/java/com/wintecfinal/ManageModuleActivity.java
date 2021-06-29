package com.wintecfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.wintec.wintecfinal.R;

public class ManageModuleActivity extends AppCompatActivity {

    Drawer result;

    RecyclerView rvManageModuleList;
    ManageModuleAdapter courseAdapter;
    FloatingActionButton fabAddNew;
    TextView editCourse, editYears, tvNoModuleData, btnSubmitNewModule;
    LinearLayout llSubmit;
    String selectedCourse = "";
    String selectedYear = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_module);

        fabAddNew = findViewById(R.id.fabAddNew);
        editCourse = findViewById(R.id.editCourse);
        editYears = findViewById(R.id.editYears);
        llSubmit = findViewById(R.id.llSubmit);
        tvNoModuleData = findViewById(R.id.tvNoModuleData);
        btnSubmitNewModule = findViewById(R.id.btnSubmitNewModule);

        rvManageModuleList = findViewById(R.id.rvManageModuleList);
        rvManageModuleList.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter = new ManageModuleAdapter(ManageModuleActivity.this);
        rvManageModuleList.setAdapter(courseAdapter);

        selectedYear = "Year 1";
        selectedCourse = "Web Development";

        editCourse.setText(selectedCourse);
        editYears.setText(selectedYear);

        fabAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Intent i = new Intent(getActivity(), AddNewModuleActivity.class);
                    i.putExtra("course", editCourse.getText().toString().trim());
                    i.putExtra("year", editYears.getText().toString().trim());
                    startActivityForResult(i, 101);
                }
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

        PopupMenu popupCourse = new PopupMenu(ManageModuleActivity.this, editCourse);
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

        PopupMenu popupYear = new PopupMenu(ManageModuleActivity.this, editYears);
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

        courseAdapter.setEventlistener(new ManageModuleAdapter.Eventlistener() {
            @Override
            public void onItemviewClick(int position) {

            }

            @Override
            public void onItemEditClick(int position, ImageView imgMore) {
                CourseModule data = courseAdapter.getItem(position);
                PopupMenu popupStudentList = new PopupMenu(getActivity(), imgMore);
                popupStudentList.getMenuInflater().inflate(R.menu.student_list_menu, popupStudentList.getMenu());
                popupStudentList.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.edit) {
                            Intent i = new Intent(getActivity(), AddNewModuleActivity.class);
                            i.putExtra("data", new Gson().toJson(data));
                            i.putExtra("course", editCourse.getText().toString().trim());
                            i.putExtra("year", editYears.getText().toString().trim());
                            i.putExtra("isFoEdit", true);
                            startActivityForResult(i, 101);
                        } else if (item.getItemId() == R.id.delete) {
                            Utils.removeCourseData(getActivity(), data);
                            courseAdapter.remove(position);
                        }
                        return true;
                    }
                });
                popupStudentList.show();
            }
        });

        editYears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupYear.show();
            }
        });

        courseAdapter.addAll(Utils.getCourseData(getActivity(), selectedYear, selectedCourse));
        refreshPlaceHolder();
//        courseAdapter.addAll(Utils.getCourseData(getActivity(), selectedYear, selectedCourse));
//        refreshPlaceHolder();
//        initDrawer();
        initBack(true);
    }

    public void initDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//create the drawer and remember the `Drawer` result object
        result = new DrawerBuilder()
                .withActivity(this).withCloseOnClick(true).withSelectedItemByPosition(-1)
                .withDrawerGravity(Gravity.LEFT)
                .withHeader(R.layout.nav_header_main)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withSelectable(false),
                        new PrimaryDrawerItem().withName("Dummy Activity").withSelectable(false),
                        new PrimaryDrawerItem().withName("Logout").withSelectable(false)

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (position == 1) {
                            if (ManageModuleActivity.this instanceof ManageModuleActivity) {
                                hideMenu(true);
                            } else {
                                Intent intent = new Intent(getActivity(),
                                        ManageModuleActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                hideMenu(false);
                                finishActivity();
                            }
                        } else if (position == 2) {
//                            if (getActivity() instanceof DummyActivity) {
//                                hideMenu(true);
//                            } else {
//                                Intent intent = new Intent(getActivity(),
//                                        DummyActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                                startActivity(intent);
//                                hideMenu(false);
//                                finishActivity();
//                            }
                        }
                        return true;
                    }
                })
                .build();

        ImageView imgMenu = (ImageView) findViewById(R.id.imgMenu);
        if (imgMenu != null) {
            imgMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (result.isDrawerOpen()) {
                        result.closeDrawer();
                    } else {
                        result.openDrawer();
                    }
                }
            });
        }

//        initMenuItems();
//        fillProfileData();
    }

    public ManageModuleActivity getActivity() {
        return this;
    }

    private void hideMenu(boolean b) {
        try {
//            if (b)
            result.closeDrawer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void finishActivity() {
        if ((getActivity() instanceof ManageModuleActivity)) {

        } else {
            getActivity().finish();
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

    public void refreshPlaceHolder() {
        if (rvManageModuleList.getAdapter().getItemCount() > 0) {
            tvNoModuleData.setVisibility(View.GONE);
            rvManageModuleList.setVisibility(View.VISIBLE);
        } else {
            tvNoModuleData.setVisibility(View.VISIBLE);
            rvManageModuleList.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            courseAdapter.addAll(Utils.getCourseData(getActivity(), selectedYear, selectedCourse));
            refreshPlaceHolder();
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