package com.ajceliano.ajworkorderapp;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Spinner spinner = (Spinner) findViewById(R.id.spinJobs);
// Create an ArrayAdapter using the string array and a default spinner layout


        //need to call web service to populate dropdown
        String url = "http://nec:81/api/WorkOrders";
        new HttpGetRequest(this).execute(url);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinJobs, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

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
}


class HttpGetRequest extends AsyncTask<String,Void,String> {
    private MainActivity mA;

    public HttpGetRequest(MainActivity mainActivity) {  // can take other params if needed
        this.mA = mainActivity;
    }

    protected String doInBackground(String... url) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(new HttpGet(url[0]));
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            response.getEntity().writeTo(out);
            String respVal = out.toString();
            out.close();
            return respVal;
        } catch (Exception e) {
            return "failed";
        }
    }
    protected void onPostExecute(String result) {
        EditText txtDes = (EditText) mA.findViewById(R.id.txtDescription);
        txtDes.setText(result);
    }
}