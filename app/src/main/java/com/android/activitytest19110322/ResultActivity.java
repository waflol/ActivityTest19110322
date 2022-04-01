package com.android.activitytest19110322;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;

public class ResultActivity extends AppCompatActivity {

    private EditText txtProfit;
    private EditText txtTotal;
    private String[] reply;
    public static final String EXTRA_REPLY = "com.android.activitytest19110322.extra.REPLY";
    private static final int CAMERA_REQUEST = 1888;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        String[] info = intent.getStringArrayExtra(MainActivity.EXTRA_MESSAGE);
        reply = info.clone();
        long[] values = compute(info);
        txtProfit = findViewById(R.id.txtProfit);
        txtTotal = findViewById(R.id.txtTotal);

        txtProfit.setText(String.valueOf(values[0])+" đ");
        txtTotal.setText(String.valueOf(values[1])+" đ");

    }
    private long[] compute(String[] info){
        long moneysent = Long.valueOf(info[0]);

        long interest_rate = Long.valueOf(info[1]);
        long period = Long.valueOf(info[2]);

        long profit = (long)(moneysent*(interest_rate/100.0)*((period*30)/360.0));
        return new long[]{profit,profit+moneysent};
    }
            public void returnReply(View view) {
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, reply);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    public void getCam(View view){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }
}