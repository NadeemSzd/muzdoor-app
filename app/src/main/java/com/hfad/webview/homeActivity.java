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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class homeActivity extends AppCompatActivity
{
    private WebView webView;

    private boolean isConnected = false;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                webView = findViewById(R.id.webView);
                webView.setWebViewClient(new WebViewClient());
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("https://muzdoor.com");
                swipeRefreshLayout.setRefreshing(false);
            }
        },2000);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                checkConnectivity();
                if(isConnected)
                {
                    reloadScreen();
                    isConnected = false;
                }
                else
                {
                    /*reloadScreen();
                    AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity.this);
                    builder.setMessage("There is no Wifi or Mobile data is available")
                            .setCancelable(false)
                            .setPositiveButton("Reload", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    checkConnectivity();
                                    if(isConnected)
                                    {
                                        webView.clearFormData();
                                        webView.reload();
                                        reloadScreen();
                                    }
                                }
                            })
                            .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.setTitle("No Internet Connection!");
                    dialog.show();*/
                    Intent homeIntent = new Intent(homeActivity.this, noInternet.class);
                    startActivity(homeIntent);
                }
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

    public void reloadScreen()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                webView.clearFormData();
                webView.reload();
                swipeRefreshLayout.setRefreshing(false);
            }
        },3000);
    }

    @Override
    public void onBackPressed()
    {
        if(webView.canGoBack())
        {
            webView.goBack();
        }
        else {

            AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity.this);
            builder.setTitle("Exit App")
                    .setMessage("Do you want to exit the app!")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
            //  super.onBackPressed();
        }
    }
}
