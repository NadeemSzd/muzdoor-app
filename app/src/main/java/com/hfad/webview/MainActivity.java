package com.hfad.webview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    private static int SPLASH_TIME_OUT = 3000; // 2 seconds
    private boolean isConnected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkConnectivity();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if(isConnected)
                {
                    Intent homeIntent = new Intent(MainActivity.this, homeActivity.class);
                    startActivity(homeIntent);
                }
                else
                {

                    Intent homeIntent = new Intent(MainActivity.this, noInternet.class);
                    startActivity(homeIntent);

                   /* AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("There is no Wifi or Mobile data is available")
                            .setCancelable(false)
                            .setPositiveButton("Reload", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                   checkConnectivity();
                                   if(isConnected)
                                   {
                                       Intent homeIntent = new Intent(MainActivity.this, homeActivity.class);
                                       startActivity(homeIntent);
                                   }
                                }
                            })
                            .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    finish();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.setTitle("No Internet Connection!");
                    dialog.show();*/
                }
            }
        },SPLASH_TIME_OUT);

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
