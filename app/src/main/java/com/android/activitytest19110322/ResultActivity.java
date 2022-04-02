package com.android.activitytest19110322;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    private EditText txtProfit;
    private EditText txtTotal;
    private String[] reply;
    public static final String EXTRA_REPLY = "com.android.activitytest19110322.extra.REPLY";
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
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
        Button photoButton = (Button)findViewById(R.id.btnTakePic);
        photoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            Intent replyIntent = new Intent();
            String [] empty_rep = {"","",""};
            replyIntent.putExtra(EXTRA_REPLY, empty_rep);
            setResult(RESULT_OK, replyIntent);
            finish();

        }
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