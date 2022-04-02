package com.android.activitytest19110322;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_MESSAGE = "com.android.activitytest19110322.extra.MESSAGE";
    private EditText Txtmoneysent;
    private EditText Txtinterest_rate;
    private EditText Txtperiod;

    public static final int TEXT_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Txtmoneysent = findViewById(R.id.Txtmoneysent);
        Txtinterest_rate = findViewById(R.id.TxtInterest_rate);
        Txtperiod = findViewById(R.id.Txtperiod);
        Txtmoneysent.requestFocus();

    }
    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                String []reply = data.getStringArrayExtra(ResultActivity.EXTRA_REPLY);
                Txtmoneysent.setText(String.valueOf(reply[0]));
                Txtinterest_rate.setText(String.valueOf(reply[1]));
                Txtperiod.setText(String.valueOf(reply[2]));
            }
        }
    }

    public void launchResultActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, ResultActivity.class);
        if(TextUtils.isEmpty(Txtmoneysent.getText())){
            Txtmoneysent.setError( "Field is required!");
        }
        else if(TextUtils.isEmpty(Txtinterest_rate.getText())){
            Txtinterest_rate.setError( "Field is required!");
        }
        else if(TextUtils.isEmpty(Txtperiod.getText())){
            Txtperiod.setError( "Field is required!");
        }
        else{
            String Strmoneysent = getText(Txtmoneysent);
            String Strinterest_rate = getText(Txtinterest_rate);
            String Strperiod = getText(Txtperiod);
            intent.putExtra(EXTRA_MESSAGE, new String[]{Strmoneysent,Strinterest_rate,Strperiod});
            //startActivity(intent);
            startActivityForResult(intent, TEXT_REQUEST);
        }
    }
    private static String getText(EditText opEditText) {
        return opEditText.getText().toString();
    }
}