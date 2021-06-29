package com.wintecfinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class Utils {

    public static void setPref(Context c, String pref, String val) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putString(pref, val);
        e.commit();

    }

    public static String getPref(Context c, String pref, String val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getString(pref,
                val);
    }

    public static void setPref(Context c, String pref, boolean val) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putBoolean(pref, val);
        e.commit();

    }

    public static boolean getPref(Context c, String pref, boolean val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(
                pref, val);
    }

    public static void delPref(Context c, String pref) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.remove(pref);
        e.commit();
    }

    public static void setPref(Context c, String pref, int val) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putInt(pref, val);
        e.commit();

    }

    public static int getPref(Context c, String pref, int val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getInt(pref,
                val);
    }

    public static void setPref(Context c, String pref, long val) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putLong(pref, val);
        e.commit();
    }

    public static long getPref(Context c, String pref, long val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getLong(pref,
                val);
    }

    public static void setPref(Context c, String file, String pref, String val) {
        SharedPreferences settings = c.getSharedPreferences(file,
                Context.MODE_PRIVATE);
        Editor e = settings.edit();
        e.putString(pref, val);
        e.commit();

    }

    public static String getPref(Context c, String file, String pref, String val) {
        return c.getSharedPreferences(file, Context.MODE_PRIVATE).getString(
                pref, val);
    }

    public static boolean validateEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    public static boolean validate(String target, String pattern) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            Pattern r = Pattern.compile(pattern);
            return r.matcher(target)
                    .matches();
        }
    }

    public static boolean isAlphaNumeric(String target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            Pattern r = Pattern.compile("^[a-zA-Z0-9]*$");
            return r.matcher(target)
                    .matches();
        }
    }

    public static boolean isNumeric(String target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            Pattern r = Pattern.compile("^[0-9]*$");
            return r.matcher(target)
                    .matches();
        }
    }

    public static ImageLoader initImageLoader(Context mContext) {
        ImageLoader imageLoader = null;
        try {
            File cacheDir = StorageUtils.getOwnCacheDirectory(mContext,
                    Constant.CACHE_DIR);

            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true).cacheInMemory(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
            ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                    mContext).defaultDisplayImageOptions(defaultOptions)
                    .diskCache(new UnlimitedDiskCache(cacheDir))
                    .memoryCache(new WeakMemoryCache());

            ImageLoaderConfiguration config = builder.build();
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(config);
            L.writeLogs(false);

            return imageLoader;
        } catch (Exception e) {
//            Utils.sendExceptionReport(e);
        }

        try {
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
            ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                    mContext).defaultDisplayImageOptions(defaultOptions)
                    .memoryCache(new WeakMemoryCache());

            ImageLoaderConfiguration config = builder.build();
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(config);
            L.writeLogs(false);
        } catch (Exception e) {
//            Utils.sendExceptionReport(e);
        }

        return imageLoader;
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        Log.e("type", "" + type);
        return type;
    }

    public static boolean isJPEGorPNG(String url) {
        try {
            String type = getMimeType(url);
            String ext = type.substring(type.lastIndexOf("/") + 1);
            if (ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("png")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }

        return false;
    }

    public static boolean isSDcardMounted() {

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)
                && !state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            return true;
        }

        return false;
    }


    /**
     * Create a File for saving an image or video
     *
     * @return Returns file reference
     */
    public static File getOutputMediaFile() {
        try {
            // To be safe, you should check that the SDCard is mounted
            // using Environment.getExternalStorageState() before doing this.
            File mediaStorageDir;
            if (isSDcardMounted()) {
                mediaStorageDir = new File(Constant.FOLDER_RIDEINN_PATH);
            } else {
                mediaStorageDir = new File(Environment.getRootDirectory(),
                        Constant.FOLDER_NAME);
            }

            // This location works best if you want the created images to be
            // shared
            // between applications and persist after your app has been
            // uninstalled.

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null;
                }
            }

            // Create a media file name

            File mediaFile = new File(mediaStorageDir.getPath()
                    + File.separator + new Date().getTime() + ".jpg");
            mediaFile.createNewFile();

            return mediaFile;
        } catch (Exception e) {
            return null;
        }
    }

    public static final String STUDENT_DATA = "student_data";

    public static final String COURSE_DATA = "course_data";

    public static final String STUDENT_RECORD_DATA = "student_record_data";

    public static void addStudent(Context c, Student item) {
        String response = getPref(c, STUDENT_DATA, "");

        if (response != null && !response.isEmpty()) {

            ArrayList<Student> students = new Gson().fromJson(
                    response, new TypeToken<ArrayList<Student>>() {
                    }.getType());


            if (!students.contains(item)) {
                students.add(item);
            }

//            Debug.e("addFavData", new Gson().toJson(favData));

            setPref(c, STUDENT_DATA, new Gson().toJson(students));
        } else {
            ArrayList<Student> studentlist = new ArrayList<>();
            studentlist.add(item);
            setPref(c, STUDENT_DATA, new Gson().toJson(studentlist));
        }
    }

    public static void editStudent(Context c, Student item, Student oldItem) {
        String response = getPref(c, STUDENT_DATA, "");

        if (response != null && !response.isEmpty()) {

            ArrayList<Student> students = new Gson().fromJson(
                    response, new TypeToken<ArrayList<Student>>() {
                    }.getType());

//            for (int i = 0; i < students.size(); i++) {
//                if (students.get(i).studentId.equals(item.studentId)) {
//                    students.remove(i);
//                }
//            }
            students.add(item);

//            Debug.e("addFavData", new Gson().toJson(favData));

            setPref(c, STUDENT_DATA, new Gson().toJson(students));
        } else {
            ArrayList<Student> studentlist = new ArrayList<>();
            studentlist.add(item);
            setPref(c, STUDENT_DATA, new Gson().toJson(studentlist));
        }
    }

    public static ArrayList<Student> getStudentData(Context c) {
        String response = getPref(c, STUDENT_DATA, "");

        if (response != null && !response.isEmpty()) {

            ArrayList<Student> students = new Gson().fromJson(
                    response, new TypeToken<ArrayList<Student>>() {
                    }.getType());

            if (students.size() != 0) {
                return students;
            }
        }
        return new ArrayList();
    }

    public static void removeStudentData(Context c, Student item) {
        String response = getPref(c, STUDENT_DATA, "");

        if (response != null && !response.isEmpty()) {

            ArrayList<Student> studentData = new Gson().fromJson(
                    response, new TypeToken<ArrayList<Student>>() {
                    }.getType());

            for (int i = 0; i < studentData.size(); i++) {
                if (studentData.get(i).studentId.equalsIgnoreCase(item.studentId)) {
                    studentData.remove(studentData.get(i));
                }
            }

//            Debug.e("removeFromFavData", new Gson().toJson(favData));

            setPref(c, STUDENT_DATA, new Gson().toJson(studentData));
        }
    }

    public static void addCourse(Context c, CourseModule item) {
        String response = getPref(c, COURSE_DATA, "");

        if (response != null && !response.isEmpty()) {

            ArrayList<CourseModule> courseModules = new Gson().fromJson(
                    response, new TypeToken<ArrayList<CourseModule>>() {
                    }.getType());


            if (!courseModules.contains(item)) {
                courseModules.add(item);
            }

//            Debug.e("addFavData", new Gson().toJson(favData));

            setPref(c, COURSE_DATA, new Gson().toJson(courseModules));
        } else {
            ArrayList<CourseModule> studentlist = new ArrayList<>();
            studentlist.add(item);
            setPref(c, COURSE_DATA, new Gson().toJson(studentlist));
        }
    }

    public static ArrayList<CourseModule> getCourseData(Context c, String selectedYear, String selectedCourse) {
        String response = getPref(c, COURSE_DATA, "");

        if (response != null && !response.isEmpty()) {

            ArrayList<CourseModule> students = new Gson().fromJson(
                    response, new TypeToken<ArrayList<CourseModule>>() {
                    }.getType());

            ArrayList<CourseModule> studentsFilter = new ArrayList<>();

            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).selectedYear.equalsIgnoreCase(selectedYear) && students.get(i).selectedCourse.equalsIgnoreCase(selectedCourse)) {
                    studentsFilter.add(students.get(i));
                }
            }

            if (studentsFilter.size() != 0) {
                return studentsFilter;
            }
        }
        return new ArrayList();
    }

    public static void setChecked(Context c, CourseModule courseModule, Boolean checked) {
        String response = getPref(c, COURSE_DATA, "");

        if (response != null && !response.isEmpty()) {

            ArrayList<CourseModule> item = new Gson().fromJson(
                    response, new TypeToken<ArrayList<CourseModule>>() {
                    }.getType());

            for (int i = 0; i < item.size(); i++) {
                if (item.get(i).moduleCode.equalsIgnoreCase(courseModule.moduleCode)) {
                    item.get(i).isCourseChecked = checked;
                }
            }
//            Debug.e("removeFromFavData", new Gson().toJson(favData));

            setPref(c, COURSE_DATA, new Gson().toJson(item));
        }
    }

    public static void setUnlockedFirstYear(Context c, String selectedYear, String selectedCourse) {
        String response = getPref(c, COURSE_DATA, "");
        if (response != null && !response.isEmpty()) {
            ArrayList<CourseModule> arrayList = new Gson().fromJson(
                    response, new TypeToken<ArrayList<CourseModule>>() {
                    }.getType());

            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).selectedYear.equalsIgnoreCase(selectedYear) && arrayList.get(i).selectedCourse.equalsIgnoreCase(selectedCourse)) {
                    arrayList.get(i).isCourseUnlocked = true;
                }
            }

            setPref(c, COURSE_DATA, new Gson().toJson(arrayList));
        }
    }

    public static void setUnlocked(Context c, String selectedYear, String selectedCourse) {
        String response = getPref(c, COURSE_DATA, "");
        if (response != null && !response.isEmpty()) {
            ArrayList<CourseModule> item = new Gson().fromJson(
                    response, new TypeToken<ArrayList<CourseModule>>() {
                    }.getType());

            if (isAllCourseChecked(item, selectedYear, selectedCourse)) {
                if (selectedYear.equalsIgnoreCase("Year 1")) {
                    for (int i = 0; i < item.size(); i++) {
                        if (item.get(i).selectedYear.equalsIgnoreCase("Year 2") && item.get(i).selectedCourse.equalsIgnoreCase(selectedCourse)) {
                            item.get(i).isCourseUnlocked = true;
                        }
                    }
                } else if (selectedYear.equalsIgnoreCase("Year 2")) {
                    for (int i = 0; i < item.size(); i++) {
                        if (item.get(i).selectedYear.equalsIgnoreCase("Year 3") && item.get(i).selectedCourse.equalsIgnoreCase(selectedCourse)) {
                            item.get(i).isCourseUnlocked = true;
                        }
                    }
                }
            } else {
                if (selectedYear.equalsIgnoreCase("Year 1")) {
                    for (int i = 0; i < item.size(); i++) {
                        if (item.get(i).selectedYear.equalsIgnoreCase("Year 2") && item.get(i).selectedCourse.equalsIgnoreCase(selectedCourse)) {
                            item.get(i).isCourseUnlocked = false;
                        }
                    }
                } else if (selectedYear.equalsIgnoreCase("Year 2")) {
                    for (int i = 0; i < item.size(); i++) {
                        if (item.get(i).selectedYear.equalsIgnoreCase("Year 3") && item.get(i).selectedCourse.equalsIgnoreCase(selectedCourse)) {
                            item.get(i).isCourseUnlocked = false;
                        }
                    }
                }
            }
//            Debug.e("removeFromFavData", new Gson().toJson(favData));

            setPref(c, COURSE_DATA, new Gson().toJson(item));
        }
    }

    public static boolean isAllCourseChecked(ArrayList<CourseModule> item, String selectedYear, String selectedCourse) {
        for (int i = 0; i < item.size(); i++) {
            if (item.get(i).selectedYear.equalsIgnoreCase(selectedYear) && item.get(i).selectedCourse.equalsIgnoreCase(selectedCourse)) {
                if (!item.get(i).isCourseChecked) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void removeCourseData(Context c, CourseModule item) {
        String response = getPref(c, COURSE_DATA, "");

        if (response != null && !response.isEmpty()) {

            ArrayList<CourseModule> favData = new Gson().fromJson(
                    response, new TypeToken<ArrayList<CourseModule>>() {
                    }.getType());

            for (int i = 0; i < favData.size(); i++) {
                if (favData.get(i).moduleCode.equalsIgnoreCase(item.moduleCode)) {
                    favData.remove(favData.get(i));
                }
            }

//            Debug.e("removeFromFavData", new Gson().toJson(favData));

            setPref(c, COURSE_DATA, new Gson().toJson(favData));
        }
    }

    public static void addStudentRecord(Context c, StudentRecordModule item) {
        String response = getPref(c, STUDENT_RECORD_DATA, "");

        if (response != null && !response.isEmpty()) {

            ArrayList<StudentRecordModule> courseModules = new Gson().fromJson(
                    response, new TypeToken<ArrayList<StudentRecordModule>>() {
                    }.getType());


            if (!courseModules.contains(item)) {
                courseModules.add(item);
            }

//            Debug.e("addFavData", new Gson().toJson(favData));

            setPref(c, STUDENT_RECORD_DATA, new Gson().toJson(courseModules));
        } else {
            ArrayList<StudentRecordModule> studentlist = new ArrayList<>();
            studentlist.add(item);
            setPref(c, STUDENT_RECORD_DATA, new Gson().toJson(studentlist));
        }
    }

    public static ArrayList<StudentRecordModule> getStudentRecordData(Context c) {
        String response = getPref(c, STUDENT_RECORD_DATA, "");

        if (response != null && !response.isEmpty()) {

            ArrayList<StudentRecordModule> students = new Gson().fromJson(
                    response, new TypeToken<ArrayList<StudentRecordModule>>() {
                    }.getType());

            if (students.size() != 0) {
                return students;
            }
        }
        return new ArrayList();
    }

    public static void removeStudentRecordData(Context c, StudentRecordModule item) {
        String response = getPref(c, STUDENT_RECORD_DATA, "");

        if (response != null && !response.isEmpty()) {

            ArrayList<StudentRecordModule> favData = new Gson().fromJson(
                    response, new TypeToken<ArrayList<StudentRecordModule>>() {
                    }.getType());

            for (int i = 0; i < favData.size(); i++) {
                if (favData.get(i).studentId.equalsIgnoreCase(item.studentId)) {
                    favData.remove(favData.get(i));
                }
            }

//            Debug.e("removeFromFavData", new Gson().toJson(favData));

            setPref(c, STUDENT_RECORD_DATA, new Gson().toJson(favData));
        }
    }

}

