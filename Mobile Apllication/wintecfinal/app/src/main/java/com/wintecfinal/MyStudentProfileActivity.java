package com.wintecfinal;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wintec.wintecfinal.R;
import com.wintecfinal.spinner.Spinner;
import com.wintecfinal.spinner.SpinnerAdapter;

import java.io.File;
import java.util.ArrayList;

import static com.wintecfinal.Utils.addStudent;
import static com.wintecfinal.Utils.addStudentRecord;

public class MyStudentProfileActivity extends AppCompatActivity {

    EditText edtStudentName, edtStudentId, edtStudentEmail, edtStudentContact, edtStudentAddress;
    TextView tvStudentImage, btnSubmitNewStudent;
    RadioGroup rgGender;
    private static final int ZBAR_CAMERA_PERMISSION = 1;
    ImageLoader imageLoader;
    ImageView imgSelectedImage;
    RadioButton rbMale, rbFeMale;
    private boolean isFoEdit = false;
    private StudentRecordModule studentRecordModule;
    Boolean isImageSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_student_profile);

        edtStudentName = findViewById(R.id.edtStudentName);
        edtStudentId = findViewById(R.id.edtStudentId);
        edtStudentEmail = findViewById(R.id.edtStudentEmail);
        edtStudentContact = findViewById(R.id.edtStudentContact);
        edtStudentAddress = findViewById(R.id.edtStudentAddress);
        tvStudentImage = findViewById(R.id.tvStudentImage);
        btnSubmitNewStudent = findViewById(R.id.btnSubmitNewStudent);
        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFeMale = findViewById(R.id.rbFeMale);
        imgSelectedImage = findViewById(R.id.imgSelectedImage);

        imageLoader = Utils.initImageLoader(getActivity());

        initIntentParams();

        tvStudentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });

        imgSelectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });

        if (isFoEdit) {
            edtStudentId.setEnabled(false);
            edtStudentName.setText(studentRecordModule.studentName);
            edtStudentId.setText(studentRecordModule.studentId);
            edtStudentEmail.setText(studentRecordModule.studentEmail);
            edtStudentContact.setText(studentRecordModule.studentContactPhone);
            edtStudentAddress.setText(studentRecordModule.studentAddress);
            if (studentRecordModule.studentGender.equalsIgnoreCase("male")) {
                rbMale.setChecked(true);
            } else {
                rbFeMale.setChecked(true);
            }
            imageLoader.displayImage("file://" + studentRecordModule.studentImage, imgSelectedImage);
//            imgSelectedImage.setTag(fileUri);
            isImageSelected = true;
            tvStudentImage.setVisibility(View.GONE);
            imgSelectedImage.setVisibility(View.VISIBLE);
        }

        btnSubmitNewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    StudentRecordModule student = new StudentRecordModule();
                    student.studentName = edtStudentName.getText().toString().trim();
                    student.studentId = edtStudentId.getText().toString().trim();
                    student.studentContactPhone = edtStudentContact.getText().toString().trim();
                    student.studentEmail = edtStudentEmail.getText().toString().trim();
                    student.studentAddress = edtStudentAddress.getText().toString().trim();
                    if (rbMale.isChecked()) {
                        student.studentGender = "male";
                    } else {
                        student.studentGender = "female";
                    }
                    if (fileUri == null) {
                        student.studentImage = studentRecordModule.studentImage;
                    } else {
                        student.studentImage = fileUri.getAbsolutePath();
                    }

//                ArrayList<Student> studentList = new ArrayList<>();
//                studentList.add(student);

                    if (isFoEdit) {
                        Utils.removeStudentRecordData(getActivity(), student);
                        addStudentRecord(getActivity(), student);
//                    editStudent(getActivity(), student);
                    } else {
                        addStudentRecord(getActivity(), student);
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
                    studentRecordModule = new Gson().fromJson(data,
                            new TypeToken<StudentRecordModule>() {
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

    void showPictureChooser() {

        final Dialog a = new Dialog(getActivity());
        Window w = a.getWindow();
        a.requestWindowFeature(Window.FEATURE_NO_TITLE);
        a.setContentView(R.layout.spinner);
        w.setBackgroundDrawableResource(android.R.color.transparent);

        TextView lblselect = w.findViewById(R.id.dialogtitle);
        lblselect.setText("Choose");

//        w.findViewById(R.id.dialogClear).setVisibility(View.GONE);
//        w.findViewById(R.id.editSearch).setVisibility(View.GONE);

        SpinnerAdapter adapter = new SpinnerAdapter(getActivity());
        ListView lv = w.findViewById(R.id.lvSpinner);
        adapter.setFilterable(false);

        final EditText editSearch = (EditText) w.findViewById(R.id.editSearch);
        editSearch.setVisibility(View.GONE);

        lv.setAdapter(adapter);
        adapter.add(new Spinner("1", getString(R.string.choose_gallery)));
//        adapter.add(new Spinner("2", getString(R.string.choose_camera)));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterview, View view,
                                    int position, long l) {

                a.dismiss();
                if (position == 0) {

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);

                    try {
                        startActivityForResult(Intent.createChooser(intent,
                                "Select"), REQ_PICK_IMAGE);

                    } catch (Exception ex) {
                        Toast.makeText(getActivity(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                    }

                } else if (position == 1) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    intent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
                    fileUri = new File(Utils.getOutputMediaFile().getAbsolutePath());
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileUri));

                    try {

                        startActivityForResult(Intent.createChooser(intent,
                                "Select"), REQ_CAPTURE_IMAGE);

                    } catch (Exception ex) {
                        Toast.makeText(getActivity(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        a.show();
    }

    File fileUri;
    public static final int REQ_CAPTURE_IMAGE = 4470;
    public static final int REQ_PICK_IMAGE = 4569;

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityResult(final int requestCode, final int resultCode,
                                 final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        try {
            Log.e("", "requestCode# " + requestCode + " resultCode# "
                    + resultCode);

            if (requestCode == REQ_CAPTURE_IMAGE) {

                if (resultCode == Activity.RESULT_OK) {
                    try {
                        if (fileUri == null || !fileUri.exists()) {
                            Uri tmp_fileUri = data.getData();
                            Log.e("", "tmp_fileUri : " + tmp_fileUri.getPath());
                            // Debug.e("", "fileUri : " + fileUri.getPath());

                            String selectedImagePath = UriHelper.getPath(
                                    getActivity(), tmp_fileUri);
                            fileUri = new File(selectedImagePath);

                        } else {

                        }

                        if (fileUri != null && fileUri.exists()) {
                            if (Utils.isJPEGorPNG(fileUri.getAbsolutePath())) {
                                startCropActivity(Uri.fromFile(fileUri));
                            } else {
                                Toast.makeText(getActivity(), "Select PNG or JPEG file only", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } else if (requestCode == REQ_PICK_IMAGE) {

                if (resultCode == Activity.RESULT_OK) {

                    Uri tmp_fileUri = data.getData();
                    Log.e("", "tmp_fileUri : " + tmp_fileUri.getPath());

                    String selectedImagePath = UriHelper.getPath(getActivity(),
                            tmp_fileUri);
                    fileUri = new File(selectedImagePath);

                    if (Utils.isJPEGorPNG(fileUri.getAbsolutePath())) {
                        startCropActivity(tmp_fileUri);
                    } else {
                        Toast.makeText(getActivity(), "Select PNG or JPEG file only", Toast.LENGTH_SHORT).show();
                    }

                }
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();

                    String selectedImagePath = UriHelper.getPath(
                            getActivity(), resultUri);
                    fileUri = new File(selectedImagePath);

                    Log.e("", "fileUri : " + fileUri.getAbsolutePath());

//                    imageLoader.displayImage(fileUri.getPath(), imgProfile);
                    imageLoader.displayImage("file://" + fileUri.getAbsolutePath(), imgSelectedImage);
                    imgSelectedImage.setTag(fileUri);
                    isImageSelected = true;

                    tvStudentImage.setVisibility(View.GONE);
                    imgSelectedImage.setVisibility(View.VISIBLE);

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startCropActivity(Uri tmp_fileUri) {
        CropImage.activity(tmp_fileUri)
                .setGuidelines(CropImageView.Guidelines.OFF)
                .setAllowRotation(true)
                .setFixAspectRatio(true)
                .start(this);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, ZBAR_CAMERA_PERMISSION);
        } else {
            showPictureChooser();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ZBAR_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showPictureChooser();
                } else {
                    Toast.makeText(this, "Please grant camera permission to upload image", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    private void showSpinner(String title, final TextView tv,
                             final ArrayList<Spinner> data, boolean isFilterable, final int m) {

        final Dialog a = new Dialog(getActivity());
        final Window w = a.getWindow();
        a.requestWindowFeature(Window.FEATURE_NO_TITLE);
        a.setContentView(R.layout.spinner);
        w.setBackgroundDrawableResource(android.R.color.transparent);

        final TextView lblselect = (TextView) w.findViewById(R.id.dialogtitle);
        lblselect.setText(title.replace("*", "").trim());

        TextView dialogClear = (TextView) w.findViewById(R.id.dialogClear);
        dialogClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("");
                tv.setTag(null);

                a.dismiss();
            }
        });
        final EditText editSearch = (EditText) w.findViewById(R.id.editSearch);
        if (isFilterable) {
            editSearch.setVisibility(View.VISIBLE);
//            a.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        } else {
            editSearch.setVisibility(View.GONE);
        }

        final SpinnerAdapter adapter = new SpinnerAdapter(getActivity());
        adapter.setFilterable(isFilterable);
        ListView lv = (ListView) w.findViewById(R.id.lvSpinner);
        lv.setAdapter(adapter);
        adapter.addAll(data);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterview, View view,
                                    int position, long l) {

//                tvSelectStateUpdate.setText("");
//                tvSelectStateUpdate.setTag(null);

                tv.setText(adapter.getItem(position).title);
                tv.setTag(adapter.getItem(position).ID);

                a.dismiss();
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() >= 1) {
                    adapter.getFilter().filter(editable.toString().trim());
                } else {
                    adapter.getFilter().filter("");
                }

            }
        });

        a.show();
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
        if (edtStudentContact.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter student contact", Toast.LENGTH_SHORT).show();
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
        if (edtStudentAddress.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter student address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!rbMale.isChecked() && !rbFeMale.isChecked()) {
            Toast.makeText(getActivity(), "Please select your gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isImageSelected) {
            Toast.makeText(getActivity(), "Please select your profile image", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public MyStudentProfileActivity getActivity() {
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