package com.manappuram.msmetracker.utility;

import static android.graphics.Typeface.BOLD;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {


    public static String getFormattedDate(String dateInput) {
        return dateInput;
//        try {
//            String outPattern = "dd/MM/yyyy";
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(outPattern);
//            Date date = dateFormat.parse(dateInput);
//            String dateOut = simpleDateFormat.format(date);
//            return dateOut;
//        } catch (Exception e) {
//            return "";
//        }
    }

    public static String getFormatDate(String dateInput) {

        // SimpleDateFormat spf=new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
        SimpleDateFormat spf = new SimpleDateFormat("dd-MM-yyyy H:mm:ss");//for oci testing
        Date newDate = null;
        try {
            newDate = spf.parse(dateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("dd MMM");
        dateInput = spf.format(newDate);
        Log.e("TAG", "getFormatDate: " + dateInput);
        return dateInput;
    }

    public static String getFormatDateLong(String dateInput) {

        // SimpleDateFormat spf=new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");//B2Cwebservice
        SimpleDateFormat spf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");//OCI
        Date newDate = null;
        try {
            newDate = spf.parse(dateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("dd MMM yyyy");
        dateInput = spf.format(newDate);
        Log.e("TAG", "getFormatDate: " + dateInput);
        return dateInput;
    }

    public static String getFormatDateCKYC(String dateInput) {

        //SimpleDateFormat spf=new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");//B2Cwebservice
        SimpleDateFormat spf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");//OCI
        Date newDate = null;
        try {
            newDate = spf.parse(dateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("dd-MM-yyyy");
        dateInput = spf.format(newDate);
        Log.e("TAG", "getFormatDate: " + dateInput);
        return dateInput;
    }


    public static String formatDate(String dateInput) {
//        String dtStart = "2019-08-15T09:27:37Z";
        String outPattern = "dd-MM-yyyy";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(outPattern);
        try {
            Date date = format.parse(dateInput);
            String dateOut = simpleDateFormat.format(date);
            return " " + dateOut;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return dateInput;
        }
    }

    public static String getDate(int dayOfMonth, int month, int year) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar1.set(Calendar.MONTH, month);
        calendar1.set(Calendar.YEAR, year);

        return getDate(calendar1);
    }

    public static Calendar getDateCal(int dayOfMonth, int month, int year) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar1.set(Calendar.MONTH, month);
        calendar1.set(Calendar.YEAR, year);

        return calendar1;
    }

    public static String getDate(Calendar calendar) {
        try {
            String outPattern = "dd-MMM-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(outPattern);
            String dateOut = simpleDateFormat.format(calendar.getTime());
            return dateOut;
        } catch (Exception e) {
            return "";
        }
    }


    public static String getLangCode(int position) {
        String langCode = null;

        switch (position) {
            case 0:
                langCode = "EN";
                break;
            case 1:
                langCode = "HI";
                break;
            case 2:
                langCode = "MA";
                break;
            case 3:
                langCode = "TA";
                break;
            case 4:
                langCode = "KA";
                break;
            case 5:
                langCode = "TE";
                break;
            case 6:
                langCode = "MR";
                break;
            case 7:
                langCode = "GU";
                break;
            case 8:
                langCode = "OR";
                break;
            case 9:
                langCode = "PU";
                break;
            case 10:
                langCode = "BE";
                break;
            case 11:
                langCode = "AS";
                break;
        }

        return langCode;
    }


    public static boolean isNull(String string) {

        if (string == null)
            return true;

        if (string.equalsIgnoreCase("null"))
            return true;

        return false;
    }

    public static void showSnackbar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    public static void showSnackbar(View view, int msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }


    public static void showSnackbarWithAction(String actionMsg, View view, String msg, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.setAction(actionMsg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                snackbar.dismiss();
            }
        });
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    public static void showSnackbarWithAction(int actionMsg, View view, String msg, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.setAction(actionMsg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                snackbar.dismiss();
            }
        });
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }


    public static boolean isEmailValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void makeLinks(TextView textView, String[] links, ClickableSpan[] clickableSpans) {
        try {
            SpannableString spannableString = new SpannableString(textView.getText());
            for (int i = 0; i < links.length; i++) {
                ClickableSpan clickableSpan = clickableSpans[i];
                String link = links[i];

                int startIndexOfLink = textView.getText().toString().indexOf(link);
                spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new StyleSpan(BOLD), startIndexOfLink, startIndexOfLink + link.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            textView.setMovementMethod(LinkMovementMethod.getInstance());

            textView.setText(spannableString, TextView.BufferType.SPANNABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String millisecondsToTime(long milliseconds) {
        long minutes = (milliseconds / 1000) / 60;
        long seconds = (milliseconds / 1000) % 60;
        String secondsStr = Long.toString(seconds);
        String secs;
        if (secondsStr.length() >= 2) {
            secs = secondsStr.substring(0, 2);
        } else {
            secs = "0" + secondsStr;
        }
        return minutes + ":" + secs;
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        InputMethodManager methodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert methodManager != null && view != null;
        methodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

//    public static void showKeyboard(Activity activity) {
//        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//    }

    public static Bitmap convertLayoutToImage(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static String getTodayDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy EEE hh:mm:ss aa");
        return df.format(c);
    }


    public static String Base64frombitmap(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);


        return encoded;
    }


    public static String getStringPdf(Context context, Uri filepath) {
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            inputStream = context.getContentResolver().openInputStream(filepath);

            byte[] buffer = new byte[1024];
            byteArrayOutputStream = new ByteArrayOutputStream();

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        byte[] pdfByteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(pdfByteArray, Base64.DEFAULT);
    }

    @SuppressLint("Range")
    public static String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    public static String getISO8601StringForDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public static String getFormattedDateZ(String dateInput) {
        try {
            String outPattern = "dd-MM-yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(outPattern);
            Date date = dateFormat.parse(dateInput);
            String dateOut = simpleDateFormat.format(date);
            return dateOut;
        } catch (Exception e) {
            return "";
        }
    }

    public static void valueanimator(int number, TextView textview) {
        ValueAnimator animator = ValueAnimator.ofInt(0, number);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textview.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }






    public static String BitMapToStringphoto(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }




}


