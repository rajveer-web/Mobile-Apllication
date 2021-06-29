package com.wintecfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

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

public class ManageStudentRecordActivity extends AppCompatActivity {

    Drawer result;

    RecyclerView rvManageStudentList;
    ManageStudentRecordAdapter manageStudentAdapter;
    FloatingActionButton fabAddNewStudent;
    TextView tvNoStudentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_student_record);

        tvNoStudentData = findViewById(R.id.tvNoStudentData);

        rvManageStudentList = findViewById(R.id.rvManageStudentList);
        rvManageStudentList.setLayoutManager(new LinearLayoutManager(this));
        manageStudentAdapter = new ManageStudentRecordAdapter(getActivity());
        rvManageStudentList.setAdapter(manageStudentAdapter);


        manageStudentAdapter.setEventlistener(new ManageStudentRecordAdapter.Eventlistener() {
            @Override
            public void onItemviewClick(int position) {
                StudentRecordModule data = manageStudentAdapter.getItem(position);
                Intent i = new Intent(getActivity(), StudentProfileActivity.class);
                i.putExtra("data", new Gson().toJson(data));
                startActivity(i);
            }

            @Override
            public void onItemEditClick(int position, ImageView imgMore) {
                StudentRecordModule data = manageStudentAdapter.getItem(position);
                PopupMenu popupStudentList = new PopupMenu(getActivity(), imgMore);
                popupStudentList.getMenuInflater().inflate(R.menu.student_list_menu, popupStudentList.getMenu());
                popupStudentList.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.edit) {
                            Intent i = new Intent(getActivity(), MyStudentProfileActivity.class);
                            i.putExtra("data", new Gson().toJson(data));
                            i.putExtra("isFoEdit", true);
                            startActivityForResult(i, 101);
                        } else if (item.getItemId() == R.id.delete) {
                            Utils.removeStudentRecordData(getActivity(), data);
                            manageStudentAdapter.remove(position);
                        }
                        return true;
                    }
                });
                popupStudentList.show();
            }
        });


//        SQLiteDatabase mydatabase = openOrCreateDatabase("wintec", MODE_PRIVATE, null);
//
//        try {
//            Cursor resultSet = mydatabase.rawQuery("Select * from Student", null);
//            resultSet.moveToFirst();
//            String studentName = resultSet.getString(0);
//            String studentId = resultSet.getString(1);
//
//            Toast.makeText(getActivity(), studentName + " " + studentId, Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        manageStudentAdapter.addAll(Utils.getStudentRecordData(getActivity()));
        refreshPlaceHolder();
//        initDrawer();
        initBack(true);
    }

    public void refreshPlaceHolder() {
        if (rvManageStudentList.getAdapter().getItemCount() > 0) {
            tvNoStudentData.setVisibility(View.GONE);
            rvManageStudentList.setVisibility(View.VISIBLE);
        } else {
            tvNoStudentData.setVisibility(View.VISIBLE);
            rvManageStudentList.setVisibility(View.GONE);
        }
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
                            if (ManageStudentRecordActivity.this instanceof ManageStudentRecordActivity) {
                                hideMenu(true);
                            } else {
                                Intent intent = new Intent(getActivity(),
                                        ManageStudentRecordActivity.class);
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

    public ManageStudentRecordActivity getActivity() {
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
        if ((getActivity() instanceof ManageStudentRecordActivity)) {

        } else {
            getActivity().finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            manageStudentAdapter.addAll(Utils.getStudentRecordData(getActivity()));
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