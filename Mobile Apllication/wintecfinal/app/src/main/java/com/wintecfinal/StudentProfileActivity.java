package com.wintecfinal;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wintec.wintecfinal.R;

public class StudentProfileActivity extends AppCompatActivity {

    TextView tvStudentName, tvStudentId, tvGender, tvContactPhone, tvEmail, tvAddress;
    ImageView imgUserProfile;
    private StudentRecordModule studentRecordModule;
    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        initIntentParams();

        imageLoader = Utils.initImageLoader(getActivity());

        tvStudentName = findViewById(R.id.tvStudentName);
        tvStudentId = findViewById(R.id.tvStudentId);
        tvGender = findViewById(R.id.tvGender);
        tvContactPhone = findViewById(R.id.tvContactPhone);
        tvEmail = findViewById(R.id.tvEmail);
        tvAddress = findViewById(R.id.tvAddress);
        imgUserProfile = findViewById(R.id.imgUserProfile);

        tvStudentName.setText(studentRecordModule.studentName);
        tvStudentId.setText(studentRecordModule.studentId);
        tvGender.setText(studentRecordModule.studentGender);
        tvContactPhone.setText(studentRecordModule.studentContactPhone);
        tvEmail.setText(studentRecordModule.studentEmail);
        tvAddress.setText(studentRecordModule.studentAddress);

        if (studentRecordModule.studentImage != null && !studentRecordModule.studentImage.isEmpty()) {
            imageLoader.displayImage("file://" + studentRecordModule.studentImage, imgUserProfile);
        }
    }

    public StudentProfileActivity getActivity() {
        return this;
    }

    private void initIntentParams() {
        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().containsKey("data")) {
                    String data = getIntent().getStringExtra("data");
                    studentRecordModule = new Gson().fromJson(data,
                            new TypeToken<StudentRecordModule>() {
                            }.getType());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}