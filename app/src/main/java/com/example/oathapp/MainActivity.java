package com.example.oathapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oathapp.requesting.User;
import com.example.oathapp.requesting.UserResponse;
import com.example.oathapp.requesting.VKParser;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    WebView webView;
    TextView textView;

    String app_token = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        webView = findViewById(R.id.webView2);
        textView = findViewById(R.id.textView);

        webView.loadUrl("https://oauth.vk.com/authorize?client_id=51472961&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=friends&response_type=token&v=5.131");
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("ACCESSING_URL", url);
                // https://oauth.vk.com/blank.html#access_token=vk1.a.lpKVFo4InMsot3gIIFzsNHT2Uuhnk0ySXF0KFuXd_fb3JphvFuRlpf-Lo2Oy5_yZsavQlvNgyndOJs-w9Oun_PMKbEFOw18Q7_vZQeSwIWhHcNc9QcRUuJIJtohNJMSwV48ym9i82iYtLakFquHAZxRpgVhhQ6GBg4_V9SGTPu_QvAAo44w2_aIzSGTlTYlx&expires_in=86400&user_id=233303833
                if (url.contains("https://oauth.vk.com/blank.html")) {
                    {
                        String cum = url.replace("https://oauth.vk.com/blank.html#", "");
                        String[] ass = cum.split("&");
                        cum = ass[0].replace("access_token=", "");
                        view.setVisibility(View.GONE);
                        app_token = cum;
                        loadData(ass[2].replace("user_id=", ""));
                        Log.d("ACCESS_TOKEN", cum);
                    }
                }
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });


    }

    public void loadData(String id) {
        VKParser vkParser = new VKParser();
        Log.d("LOADDATA",app_token.toString()+"|"+id.toString());
        vkParser.getaPie().getById(id, app_token, "5.131").enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    updateData(response.body().getResponse());
                } else {
                    Toast.makeText(MainActivity.this, "Something go wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {

                Toast.makeText(MainActivity.this, "RequestError", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateData(List<User> response) {
        User usr = response.get(0);
        if (usr == null) {
            return;
        }
        textView.setText("Пользователь:"+usr.getFirstName()+" "+ usr.getLastName());

    }


}