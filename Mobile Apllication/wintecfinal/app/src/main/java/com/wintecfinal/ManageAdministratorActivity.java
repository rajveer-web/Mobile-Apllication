package com.wintecfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.wintec.wintecfinal.R;

public class ManageAdministratorActivity extends AppCompatActivity {

    Drawer result;

    CardView cardModule, cardStudent, cardStudentProfileRecord;
    ImageView imgMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_administraror);

        cardModule = findViewById(R.id.cardModule);
        cardStudent = findViewById(R.id.cardStudent);
        cardStudentProfileRecord = findViewById(R.id.cardStudentProfileRecord);
        imgMenu = findViewById(R.id.imgMenu);


        cardModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageAdministratorActivity.this, ManageModuleActivity.class);
                startActivity(i);
            }
        });

        cardStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageAdministratorActivity.this, ManageStudentActivity.class);
                startActivity(i);
            }
        });

        cardStudentProfileRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ManageStudentRecordActivity.class);
                startActivity(i);
            }
        });

        initDrawer();
        imgMenu.setVisibility(View.VISIBLE);
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
                        new PrimaryDrawerItem().withName("Module").withSelectable(false),
                        new PrimaryDrawerItem().withName("Student").withSelectable(false),
                        new PrimaryDrawerItem().withName("Student Profile Record").withSelectable(false),
                        new PrimaryDrawerItem().withName("Logout").withSelectable(false)

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (position == 1) {
                            hideMenu(true);
                        } else if (position == 2) {
                            Intent intent = new Intent(getActivity(),
                                    ManageModuleActivity.class);
                            startActivity(intent);
                            hideMenu(false);
                        } else if (position == 3) {
                            Intent intent = new Intent(getActivity(),
                                    ManageStudentActivity.class);
                            startActivity(intent);
                            hideMenu(false);
                        } else if (position == 4) {
                            Intent intent = new Intent(getActivity(),
                                    ManageStudentRecordActivity.class);
                            startActivity(intent);
                            hideMenu(false);
                        } else if (position == 5) {
                            finish();
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

    public ManageAdministratorActivity getActivity() {
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
        if ((getActivity() instanceof ManageAdministratorActivity)) {

        } else {
            getActivity().finish();
        }

    }
}