package com.example.user0308.login;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.FileOutputStream;

public class Setting extends AppCompatActivity {

    private String strWrite = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置返回按钮,必须在setContentView之前
        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        setContentView(R.layout.activity_setting);

        //定义username和password阶段
        final EditText userEdit = (EditText) findViewById(R.id.username_edit);
        final EditText passEdit = (EditText) findViewById(R.id.passwd_edit);

        Button button = (Button) findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userEdit.getText().toString().equals("")||passEdit.getText().toString().equals("")){
                    Toast toast = Toast.makeText(Setting.this
                            , "所填内容为空"
                            , Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    strWrite = userEdit.getText().toString() + "\t" +passEdit.getText().toString();
                    if(writeFileData("wasduy",strWrite)){
                        Toast toast = Toast.makeText(Setting.this
                                , "保存成功"
                                // 设置该Toast提示信息的持续时间
                                , Toast.LENGTH_SHORT);
                        toast.show();
                    }else {
                        Toast toast = Toast.makeText(Setting.this
                                , "保存失败"
                                // 设置该Toast提示信息的持续时间
                                , Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }else {
                    Toast toast = Toast.makeText(Setting.this
                            , "未检测到sdcard"
                            , Toast.LENGTH_SHORT);
                    toast.show();
                }

        }});

        //定义URL阶段
        final EditText urlEditText = (EditText) findViewById(R.id.editText);

        Button okButton = (Button) findViewById(R.id.button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(urlEditText.getText().toString().equals("")){
                    Toast toast = Toast.makeText(Setting.this
                            , "所填内容为空"
                            , Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    strWrite = urlEditText.getText().toString();
                    if(writeFileData("URL",strWrite)){
                        Toast toast = Toast.makeText(Setting.this
                                , "保存成功"
                                , Toast.LENGTH_SHORT);
                        toast.show();
                    }else {
                        Toast toast = Toast.makeText(Setting.this
                                , "保存失败"
                                , Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }else {
                    Toast toast = Toast.makeText(Setting.this
                            , "未检测到sdcard"
                            , Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }


    //把信息写入sdCard
    public boolean writeFileData(String fileName,String message) {
        try {
            FileOutputStream fout = openFileOutput(fileName, MODE_PRIVATE);
            byte[] bytes = message.getBytes();
            fout.write(bytes);
            fout.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}