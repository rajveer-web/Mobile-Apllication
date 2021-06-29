package com.wintecfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mikepenz.materialdrawer.Drawer;
import com.wintec.wintecfinal.R;

import java.util.ArrayList;

import static com.wintecfinal.Utils.addStudent;
import static com.wintecfinal.Utils.editStudent;
import static com.wintecfinal.Utils.getPref;
import static com.wintecfinal.Utils.setPref;

public class AddNewStudentActivity extends AppCompatActivity {

    EditText edtStudentName, edtStudentId, edtStudentCourse, edtStudentLevel, edtStudentEmail, edtStudentContact, edtStudentAddress;

    TextView btnSubmitNewStudent;

    private static final String TABLE_STUDENT = "student";
    private static final String KEY_ID = "id";
    private static final String STUDENT_NAME = "student_name";
    private static final String STUDENT_ID = "student_id";
    private static final String STUDENT_COURSE = "student_course";
    private static final String STUDENT_LEVEL = "student_level";
    private static final String STUDENT_EMAIL = "student_name";
    private static final String STUDENT_CONTACT = "student_contact";
    private static final String STUDENT_ADDRESS = "student_address";
    Student st;
    private boolean isFoEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student);

        initIntentParams();

        edtStudentName = findViewById(R.id.edtStudentName);
        edtStudentId = findViewById(R.id.edtStudentId);
        edtStudentCourse = findViewById(R.id.edtStudentCourse);
        edtStudentLevel = findViewById(R.id.edtStudentLevel);
        edtStudentEmail = findViewById(R.id.edtStudentEmail);
        edtStudentContact = findViewById(R.id.edtStudentContact);
        edtStudentAddress = findViewById(R.id.edtStudentAddress);
        btnSubmitNewStudent = findViewById(R.id.btnSubmitNewStudent);

        if (isFoEdit) {
            edtStudentId.setEnabled(false);
            edtStudentName.setText(st.studentName);
            edtStudentId.setText(st.studentId);
            edtStudentCourse.setText(st.studentCourse);
            edtStudentLevel.setText(st.studentLevel);
            edtStudentEmail.setText(st.studentEmail);
            edtStudentContact.setText(st.studentContact);
            edtStudentAddress.setText(st.studentAddress);
        }


//        SQLiteDatabase db = openOrCreateDatabase("wintec", MODE_PRIVATE, null);
//        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Student(name VARCHAR,studentId VARCHAR,course VARCHAR,level VARCHAR,email VARCHAR,contact VARCHAR,Address VARCHAR);");
//        String CREATE_STUDENT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_STUDENT + "("
//                + KEY_ID + " INTEGER PRIMARY KEY," + STUDENT_NAME + " TEXT,"
//                + STUDENT_ID + " TEXT,"
//                + STUDENT_COURSE + " TEXT,"
//                + STUDENT_LEVEL + " TEXT,"
//                + STUDENT_EMAIL + " TEXT,"
//                + STUDENT_CONTACT + " TEXT,"
//                + STUDENT_ADDRESS + " TEXT" + ")";

//        db.execSQL(CREATE_STUDENT_TABLE);

        btnSubmitNewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Student student = new Student();
                    student.studentName = edtStudentName.getText().toString().trim();
                    student.studentId = edtStudentId.getText().toString().trim();
                    student.studentCourse = edtStudentCourse.getText().toString().trim();
                    student.studentLevel = edtStudentLevel.getText().toString().trim();
                    student.studentEmail = edtStudentEmail.getText().toString().trim();
                    student.studentContact = edtStudentContact.getText().toString().trim();
                    student.studentAddress = edtStudentAddress.getText().toString().trim();

//                ArrayList<Student> studentList = new ArrayList<>();
//                studentList.add(student);

                    if (isFoEdit) {
                        Utils.removeStudentData(getActivity(), st);
                        Utils.addStudent(getActivity(), student);
//                    editStudent(getActivity(), student);
                    } else {
                        addStudent(getActivity(), student);
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
//                mydatabase.execSQL("INSERT INTO Student VALUES(name =studentName,studentId= studentId,course = StudentCourse,level = StudentLevel,email = StudentEmail,contact = StudentContact,Address= StudentAddress);");
//
//                db.insert(CREATE_STUDENT_TABLE, null, values);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        initBack(true);
    }

    private void initIntentParams() {
        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().containsKey("data")) {
                    String data = getIntent().getStringExtra("data");
                    st = new Gson().fromJson(data,
                            new TypeToken<Student>() {
                            }.getType());
                }
                if (getIntent().getExtras().containsKey("isFoEdit")) {
                    isFoEdit = getIntent().getBooleanExtra("isFoEdit", false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AddNewStudentActivity getActivity() {
        return this;
    }

    private boolean validate() {
        if (edtStudentName.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter student name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtStudentId.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter student id", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtStudentCourse.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please student course", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtStudentLevel.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter student level", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtStudentEmail.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter student email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isValidEmail(edtStudentEmail.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Please Enter valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtStudentContact.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter student contact", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtStudentAddress.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter student address", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
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