package com.ajceliano.ajworkorderapp;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ajceliano.ajworkorderapp.Obj.NewWorkOrder;
import com.ajceliano.ajworkorderapp.Obj.RefData;
import com.ajceliano.ajworkorderapp.Obj.RefData_List;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 2/22/2015.
 */
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
        String refDataJSON = "{ refData:" + FormatIncomingNET_JSON(result) + " }";

        Gson gson = new Gson();
        RefData_List data = gson.fromJson(refDataJSON, RefData_List.class);
        List<RefData> d = data.GetRefData();

        EditText txtDes = (EditText) mA.findViewById(R.id.txtDescription);
        txtDes.setText(result);
    }

    private String FormatIncomingNET_JSON(String json){
        return json.substring(0, json.length() -1).substring(1).replace("\\", "");
    }
}

class SubmitNewWorkOrder extends AsyncTask<NewWorkOrder,Void,Boolean> {
    private MainActivity mA;
    private String postURL;

    public SubmitNewWorkOrder(MainActivity mainActivity, String url) {  // can take other params if needed
        this.mA = mainActivity;
        this.postURL = url;
    }

    protected Boolean doInBackground(NewWorkOrder... newWorkOrder) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postReq = new HttpPost(postURL);

            NewWorkOrder wO = newWorkOrder[0];
            postReq.setHeader("Content-type", "application/json");
            postReq.setEntity(new StringEntity(String.format("{ JobID: %1$s, DeviceGUID: '%2$s', Subject: '%3$s', Description: '%4$s' }", wO.GetJobID(), wO.GetDeviceGUID(), wO.GetSubject(), wO.GetDescription())));

            HttpResponse response = httpClient.execute(postReq);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    protected void onPostExecute(Boolean success) {
        //Tell the user that the request has been submitted successfully
        String msg = success ? "Work Order Submitted" : "Failed: Error Submitting";
        Toast toast = Toast.makeText(mA, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}