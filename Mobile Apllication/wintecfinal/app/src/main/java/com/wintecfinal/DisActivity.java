package com.wintecfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.wintec.wintecfinal.R;

public class DisActivity extends AppCompatActivity {

    Drawer result;


    TextView tvAccept, tvDecline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dis);

        tvAccept = findViewById(R.id.tvAccept);
        tvDecline = findViewById(R.id.tvDecline);

        tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.setPref(getActivity(), "descAccepted", true);
                Intent i = new Intent(getActivity(), HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        tvDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                            if (DisActivity.this instanceof DisActivity) {
                                hideMenu(true);
                            } else {
                                Intent intent = new Intent(getActivity(),
                                        DisActivity.class);
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

    public DisActivity getActivity() {
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
        if ((getActivity() instanceof DisActivity)) {

        } else {
            getActivity().finish();
        }

    }
}