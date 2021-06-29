package com.wintecfinal;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.wintec.wintecfinal.R;

public class HomeActivity extends AppCompatActivity {

    Drawer result;

    TextView tvStudent, tvManagerAdministration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvStudent = findViewById(R.id.tvStudent);
        tvManagerAdministration = findViewById(R.id.tvManagerAdministration);


        tvStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), StudentHomeActivity.class);
                startActivity(i);
            }
        });

        tvManagerAdministration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogClass cdd = new CustomDialogClass(HomeActivity.this);
                cdd.show();
            }
        });

        initDrawer();
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
                            if (HomeActivity.this instanceof HomeActivity) {
                                hideMenu(true);
                            } else {
                                Intent intent = new Intent(getActivity(),
                                        HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                hideMenu(false);
                                finishActivity();
                            }
                        } else if (position == 2) {

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

    public HomeActivity getActivity() {
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
        if ((getActivity() instanceof HomeActivity)) {

        } else {
            getActivity().finish();
        }

    }

    public class CustomDialogClass extends Dialog {
        public Activity c;
        public TextView btnSubmit, tvWrongPassword;
        public EditText edtPassword;

        public CustomDialogClass(Activity a) {
            super(a);
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_password);
            btnSubmit = (TextView) findViewById(R.id.btnSubmit);
            tvWrongPassword = (TextView) findViewById(R.id.tvWrongPassword);
            edtPassword = (EditText) findViewById(R.id.edtPassword);

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtPassword.getText().toString().trim().equals("WinITDMP01")) {
                        tvWrongPassword.setVisibility(View.GONE);
                        Intent i = new Intent(getActivity(), ManageAdministratorActivity.class);
                        startActivity(i);
                        dismiss();
                    } else {
                        tvWrongPassword.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
}