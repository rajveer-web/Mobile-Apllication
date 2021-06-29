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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.wintec.wintecfinal.R;

public class StudentHomeActivity extends AppCompatActivity {

    LinearLayout llWebDev, llNetEng, llSoftEng, llDataArch;
    ImageView imgHome, imgMore,imgCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        llWebDev = findViewById(R.id.llWebDev);
        llNetEng = findViewById(R.id.llNetEng);
        llSoftEng = findViewById(R.id.llSoftEng);
        llDataArch = findViewById(R.id.llDataArch);

        imgHome = findViewById(R.id.imgHome);
        imgMore = findViewById(R.id.imgMore);
        imgCall = findViewById(R.id.imgCall);


        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ContactUsActivity.class);
                startActivity(i);
            }
        });

        PopupMenu popupMore = new PopupMenu(getActivity(), imgMore);
        popupMore.getMenuInflater().inflate(R.menu.student_home_menu, popupMore.getMenu());
        popupMore.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.profile) {
                    Intent i = new Intent(getActivity(), MyStudentProfileActivity.class);
                    startActivity(i);
                } else if (item.getItemId() == R.id.aboutus) {
                    Intent i = new Intent(getActivity(), AboutUsActivity.class);
                    startActivity(i);
                } else if (item.getItemId() == R.id.contactus) {
                    Intent i = new Intent(getActivity(), ContactUsActivity.class);
                    startActivity(i);
                }
                return true;
            }
        });

        imgMore.setVisibility(View.VISIBLE);
        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMore.show();
            }
        });


        llWebDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentHomeActivity.this, StudentViewSubActivity.class);
                i.putExtra("key", "webdev");
                startActivity(i);
            }
        });

        llNetEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentHomeActivity.this, StudentViewSubActivity.class);
                i.putExtra("key", "netEng");
                startActivity(i);
            }
        });
        llSoftEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentHomeActivity.this, StudentViewSubActivity.class);
                i.putExtra("key", "softEng");
                startActivity(i);
            }
        });
        llDataArch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentHomeActivity.this, StudentViewSubActivity.class);
                i.putExtra("key", "database");
                startActivity(i);
            }
        });
        initBack(true);
    }

    public StudentHomeActivity getActivity() {
        return this;
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