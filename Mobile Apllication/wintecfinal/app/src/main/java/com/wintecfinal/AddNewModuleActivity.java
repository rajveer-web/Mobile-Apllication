package com.wintecfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.wintec.wintecfinal.R;

import static com.wintecfinal.Utils.addCourse;
import static com.wintecfinal.Utils.addStudent;

public class AddNewModuleActivity extends AppCompatActivity {

    EditText edtModuleCode, edtModuleTitle, edtPrescription, edtProgram, editnzqaLevel, edtNzqaCredit, edtResourceRequirement;

    TextView btnSubmitNewModule;
    CourseModule courseModule;
    private boolean isFoEdit = false;
    private String course;
    private String year;

    private void initIntentParams() {
        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().containsKey("data")) {
                    String data = getIntent().getStringExtra("data");
                    courseModule = new Gson().fromJson(data,
                            new TypeToken<CourseModule>() {
                            }.getType());
                }
                if (getIntent().getExtras().containsKey("isFoEdit")) {
                    isFoEdit = getIntent().getBooleanExtra("isFoEdit", false);
                }
                if (getIntent().getExtras().containsKey("course")) {
                    course = getIntent().getStringExtra("course");
                }
                if (getIntent().getExtras().containsKey("year")) {
                    year = getIntent().getStringExtra("year");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_module);

        initIntentParams();

        edtModuleCode = findViewById(R.id.edtModuleCode);
        edtModuleTitle = findViewById(R.id.edtModuleTitle);
        edtPrescription = findViewById(R.id.edtPrescription);
        edtProgram = findViewById(R.id.edtProgram);
        editnzqaLevel = findViewById(R.id.editnzqaLevel);
        edtNzqaCredit = findViewById(R.id.edtNzqaCredit);
        edtResourceRequirement = findViewById(R.id.edtResourceRequirement);
        btnSubmitNewModule = findViewById(R.id.btnSubmitNewModule);

        if (isFoEdit) {
            edtModuleCode.setEnabled(false);
            edtModuleCode.setText(courseModule.moduleCode);
            edtModuleTitle.setText(courseModule.moduleTitle);
            edtPrescription.setText(courseModule.modulePrescription);
            edtProgram.setText(courseModule.moduleProgram);
            editnzqaLevel.setText(courseModule.NZQALevel);
            edtNzqaCredit.setText(courseModule.NZQACredit);
            edtResourceRequirement.setText(courseModule.moduleResource);
        }


        btnSubmitNewModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    CourseModule courseModule = new CourseModule();
                    courseModule.moduleCode = edtModuleCode.getText().toString().trim();
                    courseModule.moduleTitle = edtModuleTitle.getText().toString().trim();
                    courseModule.modulePrescription = edtPrescription.getText().toString().trim();
                    courseModule.moduleProgram = edtProgram.getText().toString().trim();
                    courseModule.NZQALevel = editnzqaLevel.getText().toString().trim();
                    courseModule.NZQACredit = edtNzqaCredit.getText().toString().trim();
                    courseModule.moduleResource = edtResourceRequirement.getText().toString().trim();
                    courseModule.selectedCourse = course;
                    courseModule.selectedYear = year;

//                ArrayList<Student> studentList = new ArrayList<>();
//                studentList.add(student);

                    if (isFoEdit) {
                        Utils.removeCourseData(getActivity(), courseModule);
                        Utils.addCourse(getActivity(), courseModule);
                    } else {
                        addCourse(getActivity(), courseModule);
                    }

//                setPref(getActivity(), "student", new Gson().toJson(studentList));

//                ContentValues values = new ContentValues();
//                values.put(STUDENT_NAME, edtStudentName.getText().toString().trim());
//                values.put(STUDENT_ID, edtStudentId.getText().toString().trim());
//                values.put(STUDENT_COURSE, edtStudentCourse.getText().toString().trim());
//                values.put(STUDENT_LEVEL, edtStudentLevel.getText().toString().trim());
//                values.put(STUDENT_EMAIL, edtStudentEmail.getText().toString().trim());
//                values.put(STUDENT_CONTACT, edtStudentContact.getText().toString().trim());
//                values.put(STUDENT_ADDRESS, edtStudentAddress.getText().toString().trim());
//
////                mydatabase.execSQL("INSERT INTO Student VALUES(name =studentName,studentId= studentId,course = StudentCourse,level = StudentLevel,email = StudentEmail,contact = StudentContact,Address= StudentAddress);");
//
//                db.insert(CREATE_STUDENT_TABLE, null, values);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        initBack(true);
    }

    public AddNewModuleActivity getActivity() {
        return this;
    }

    private boolean validate() {
        if (edtModuleCode.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter module code", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtModuleTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter module title", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtPrescription.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter Prescription", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtProgram.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter program", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editnzqaLevel.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter nzqa Level", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtNzqaCredit.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter Nzqa Credit", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtResourceRequirement.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter Resource Requirement", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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