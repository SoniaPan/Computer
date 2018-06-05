package com.example.charlotte.comtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView responseText;

    final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendRequest = (Button) findViewById(R.id.send_request);
        responseText = (TextView) findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this);

    }

   // @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send_request){
            sendRequestWithOKHttp();
        }


    }

    private void sendRequestWithOKHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://172.20.10.9:8080/ppp/f/Com/ComInfo/TestJSONObject")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponse(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        ComInfo comInfo=new ComInfo();
        double Memory=comInfo.getMemory();
        double cpuComRatio=comInfo.getCpuComRatio();
        double usePercent=comInfo.getUsePercent();

        Intent intent=new Intent();
        //Bundle bundle=new Bundle();
//        bundle.putDouble("Memory",Memory);
//        bundle.putDouble("cpuComRatio",cpuComRatio);
//        bundle.putDouble("usePercent",usePercent);

//        intent.putExtras(bundle);
        intent.setClass(MainActivity.this,PieChartActivity.class);

        startActivity(intent);
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示在界面上
                responseText.setText(response);
            }
        });
    }
}
