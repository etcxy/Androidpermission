package com.etcxy.openfly.delete_demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_activity);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // Translucent navigation bar
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        et_input = (EditText) findViewById(R.id.et_input);
    }

    public void click(View view) {
        Log.v("info", "click");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            String phNum = et_input.getText().toString();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phNum));
            startActivity(intent);
        }
    }

    //用户选择允许或拒绝后，会回调onRequestPermissionsResult方法, 该方法类似于
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.v("info", "onRequestPermissionsResult");
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                String phNum = et_input.getText().toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phNum));
                startActivity(intent);

            } else {
                // Permission Denied
                Toast.makeText(MainActivity.this, "您没有授权该权限，请在设置中打开授权", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    //判断是否是安卓6.0以上
    private boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    //是否申请了该使用权限
    private boolean isGranted(String permission) {
        int checkSelfPermission = ActivityCompat.checkSelfPermission(this, permission);
        return checkSelfPermission == PackageManager.PERMISSION_GRANTED;
    }


}
