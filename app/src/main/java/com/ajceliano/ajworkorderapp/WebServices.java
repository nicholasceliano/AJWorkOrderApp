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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Nick on 2/22/2015.
 */

class GetReferenceData extends AsyncTask<String,Void,String> {
    private MainActivity mA;
    private String webServiceCallExtension;

    public GetReferenceData(MainActivity mainActivity) {  // can take other params if needed
        this.mA = mainActivity;
    }

    protected String doInBackground(String... webAPIExtension) {
        this.webServiceCallExtension = webAPIExtension[0];
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(new HttpGet(mA.baseAPI_URI + this.webServiceCallExtension));
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
        try{
            String refDataJSON = "{ refData:" + FormatIncomingNET_JSON(result) + " }";
            Gson gson = new Gson();
            RefData_List data = gson.fromJson(refDataJSON, RefData_List.class);
            List<RefData> d = data.GetRefData();

            if (webServiceCallExtension == "Jobs")
                SpinnerFunctions.PopulateRefDataSpinnerValues(mA, R.id.spinJobs, d);//load into dropdown
            //else if(webServiceCallExtension == "Users")
            //else if (webServiceCallExtension == "Devices"
        }
        catch (Exception e){
            String msg = "Load " + webServiceCallExtension + " Data Failed";
            Toast toast = Toast.makeText(mA, msg, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private String FormatIncomingNET_JSON(String json){
        return json.substring(0, json.length() -1).substring(1).replace("\\", "");
    }
}

class SubmitNewWorkOrder extends AsyncTask<NewWorkOrder,Void,Boolean> {
    private MainActivity mA;
    private String webServCall;

    public SubmitNewWorkOrder(MainActivity mainActivity, String webServiceCallExtension) {  // can take other params if needed
        this.mA = mainActivity;
        this.webServCall = webServiceCallExtension;
    }

    protected Boolean doInBackground(NewWorkOrder... newWorkOrder) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postReq = new HttpPost(mA.baseAPI_URI + webServCall);

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