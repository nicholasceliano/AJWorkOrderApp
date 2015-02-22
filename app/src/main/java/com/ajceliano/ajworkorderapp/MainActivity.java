package com.ajceliano.ajworkorderapp;

import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ajceliano.ajworkorderapp.Obj.NewWorkOrder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;


public class MainActivity extends ActionBarActivity {

    public MainActivity mA;
    private String android_ID;
    private static String baseAPI_URI = "http://nec:81/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mA = this;
        setContentView(R.layout.activity_main);
        android_ID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        PopulateJobs();

        findViewById(R.id.btnSubmitWorkOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText subject = (EditText)findViewById(R.id.txtSubject);
                EditText desc = (EditText)findViewById(R.id.txtDescription);

                NewWorkOrder wO = new NewWorkOrder();
                wO.SetJobID(1);
                wO.SetDeviceGUID(android_ID);
                wO.SetSubject(subject.getText().toString());
                wO.SetDescription(desc.getText().toString());

                new SubmitNewWorkOrder(mA, baseAPI_URI + "WorkOrders").execute(wO);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void PopulateJobs() {
        new HttpGetRequest(this).execute(baseAPI_URI + "Jobs");
    }
}