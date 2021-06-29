package com.wintecfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.wintec.wintecfinal.R;

public class SubjectViewMoreActivity extends AppCompatActivity {

    public TextView tvModuleCode, tvModuleTitle, tvModulePrescription, tvPrograms, tvNzqaLevel, tvNzqaCredits, tResourcesRequired;
    private CourseModule courseModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_view_more);
        initIntentParams();

        tvModuleCode = (TextView) findViewById(R.id.tvModuleCode);
        tvModuleTitle = (TextView) findViewById(R.id.tvModuleTitle);
        tvModulePrescription = (TextView) findViewById(R.id.tvModulePrescription);
        tvPrograms = (TextView) findViewById(R.id.tvPrograms);
        tvNzqaLevel = (TextView) findViewById(R.id.tvNzqaLevel);
        tvNzqaCredits = (TextView) findViewById(R.id.tvNzqaCredits);
        tResourcesRequired = (TextView) findViewById(R.id.tResourcesRequired);

        tvModuleCode.setText(courseModule.moduleCode);
        tvModuleTitle.setText(courseModule.moduleTitle);
        tvModulePrescription.setText(courseModule.modulePrescription);
        tvPrograms.setText(courseModule.moduleProgram);
        tvNzqaLevel.setText(courseModule.NZQALevel);
        tvNzqaCredits.setText(courseModule.NZQACredit);
        tResourcesRequired.setText(courseModule.moduleResource);
    }

    private void initIntentParams() {
        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().containsKey("data")) {
                    String data = getIntent().getStringExtra("data");
                    courseModule = new Gson().fromJson(data,
                            new TypeToken<CourseModule>() {
                            }.getType());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}