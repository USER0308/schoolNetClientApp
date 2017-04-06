package com.example.user0308.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;


public class MainActivity extends AppCompatActivity {

    //private final static String HTML_URL = "https://s.scut.edu.cn/a33.htm?wlanuserip=172.21.177.113&wlanacip=172.21.255.251&wlanacname=WX6108E-slot5-AC&redirect=&session=";
    //private final static String URL = "https://s.scut.edu.cn/a30.htm?wlanuserip=172.21.165.193&wlanacip=172.21.255.251&wlanacname=&redirect=&session=&vlanid=scut-student&ip=172.21.165.193&mac=000000000000";
    //172.21.177.113 172.21.177.113
    //enmular login
    //https://s.scut.edu.cn/a70.htm?wlanuserip=172.21.165.193&wlanacip=172.21.255.251&wlanacname=&redirect=&session=&vlanid=scut-student&ip=172.21.165.193&mac=000000000000
    //pc login
    //https://s.scut.edu.cn/a33.htm?wlanuserip=172.21.177.113&wlanacip=172.21.255.251&wlanacname=WX6108E-slot5-AC&redirect=&session=
    //mobile successfully login
    //Location: https://s.scut.edu.cn/a79.htm?source-address=172.21.165.193&WX6108E-slot5-AC=WX6108E-slot5-AC&ssid=scut-student&nasip=172.21.255.251&source-mac=BC-30-7E-11-6C-1C
    //pc copy and paste show successfully
    //https://s.scut.edu.cn/a30.htm?wlanuserip=172.21.177.113&wlanacip=172.21.255.251&wlanacname=&redirect=&session=&vlanid=scut-student&ip=172.21.177.113&mac=000000000000
    //https://s.scut.edu.cn/2.htm?wlanuserip=172.21.165.193&wlanacip=172.21.255.251&wlanacname=WX6108E-slot5-AC
    //multiPeople
    //private String URL = "https://s.scut.edu.cn/a30.htm?wlanuserip=172.21.177.113&wlanacip=172.21.255.251&wlanacname=&redirect=&session=&vlanid=scut-student&ip=172.21.177.113&mac=000000000000";

    private String strRead = null;
    private String URL=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button button = (Button) findViewById(R.id.button);
        //button.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        Toast.makeText(MainActivity.this,"Ok press", Toast.LENGTH_SHORT).show();
        //        EditText editText = (EditText) findViewById(R.id.editText);
        //        String URL = editText.getText().toString();
        //        setViews(URL);
        //    }
        //});

        loadURL();
        setViews();
    }

    //从文件中加载URL
    private void loadURL(){
        File file = new File(getApplicationContext().getFilesDir().getPath()+"/URL");
        //文件不存在,提示先点击+添加信息
        if(!file.exists()){
            Toast.makeText(MainActivity.this
                    , getApplicationContext().getFilesDir().getPath()+"/URL not exit, please click +"
                    , Toast.LENGTH_SHORT).show();
            return;
        }
        //文件存在,但读取过程在可能出错返回null值
        URL = readFileData("URL");
        //if(URL == null){
        //    Toast.makeText(MainActivity.this
        //            , "URL is null"
        //            , Toast.LENGTH_SHORT).show();
        //}
    }

    private void setViews() {
        //无法读取URL,直接返回
        if(URL==null){
            Toast.makeText(MainActivity.this
                    , "URL not exit, please click +"
                    , Toast.LENGTH_SHORT).show();
            return;
        }
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSaveFormData(true);
        webView.loadUrl(URL);
        webView.setWebViewClient(new MyWebViewClient());
    }

    //内部类继承自WebViewClient,实现shouldOverrideUrlLoading和onPageFinished函数
    private class MyWebViewClient extends WebViewClient{

        //把程序内点击产生的URL在程序内部处理,不通知系统由系统外部调用浏览器处理
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            //return super.shouldOverrideUrlLoading(view, request);
            view.loadUrl(request.toString());
            return true;
        }

        //加载页面完成时读取文件,得到name和password,加载js脚本完成自动填写
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            File file = new File(getApplicationContext().getFilesDir().getPath()+"/wasduy");
            if(!file.exists()){
                Toast.makeText(MainActivity.this
                        , getApplicationContext().getFilesDir().getPath()+"/wasduy not exit, please click +"
                        , Toast.LENGTH_SHORT).show();
                return;
            }
            strRead = readFileData("wasduy");
            if(strRead==null) {
                Toast.makeText(MainActivity.this
                        , "strRead is null"
                        , Toast.LENGTH_SHORT).show();
                return;
            }
            String[] info = strRead.split("\t");
            String js = String.format("javascript:document.getElementById('VipDefaultAccount').value = '%s'; document.getElementById('VipDefaultPassword').value = '%s';document.getElementById('login').focus();",info[0],info[1]);
            view.loadUrl(js);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        //页面错误处理,省略
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            //super.onReceivedError(view, request, error);

        }
    }

    //添加 + option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    //设置点击 + 后的动作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_add: {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,Setting.class);
                startActivity(intent);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    //读文件在,文件位置在./data/data/com.example.user0308.login/files/fileName下
    public String readFileData(String fileName){
        String str;
        try{
            FileInputStream fin = openFileInput(fileName);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            str = new String(buffer);
            fin.close();
            return str;
        }
        catch(Exception e){
            Toast.makeText(MainActivity.this
                    , "cannot open " + getApplicationContext().getFilesDir().getPath()+fileName
                    , Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }

}