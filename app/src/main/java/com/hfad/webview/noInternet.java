package com.hfad.webview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebViewClient;

public class noInternet extends AppCompatActivity
{
    private boolean isConnected = false;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                checkConnectivity();
                if(isConnected)
                {
                    isConnected = false;
                    Intent intent = new Intent(noInternet.this,homeActivity.class);
                    startActivity(intent);
                }
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });
    }

    public void checkConnectivity()
    {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if(activeNetwork!=null)
        {
            isConnected = true;
        }
    }


}
