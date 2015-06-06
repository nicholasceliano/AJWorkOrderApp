package com.ajceliano.ajworkorderapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ajceliano.ajworkorderapp.Helpers.Formatting;
import com.ajceliano.ajworkorderapp.Helpers.SpinnerFunctions;
import com.ajceliano.ajworkorderapp.Obj.NewWorkOrder;
import com.ajceliano.ajworkorderapp.Obj.RefData;
import com.ajceliano.ajworkorderapp.Obj.RefData_List;
import com.ajceliano.ajworkorderapp.Obj.WorkOrder;
import com.ajceliano.ajworkorderapp.Obj.WorkOrder_List;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by Nick on 2/22/2015.
 */

class GetReferenceData extends AsyncTask<String,Void,String> {
    private aNewWorkOrder nWO;
    private String webServiceCallExtension;
    private String spinVal = null;

    public GetReferenceData(aNewWorkOrder newWorkOrder) {  // can take other params if needed
        this.nWO = newWorkOrder;
    }

    public GetReferenceData(aNewWorkOrder newWorkOrder, String spinnerValue) {  // can take other params if needed
        this.nWO = newWorkOrder;
        this.spinVal = spinnerValue;
    }

    protected String doInBackground(String... webAPIExtension) {
        this.webServiceCallExtension = webAPIExtension[0];
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(new HttpGet(GlobalVars.baseAPI_URI + this.webServiceCallExtension));
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
            String refDataJSON = "{ refData:" + Formatting.FormatIncomingNET_JSON(result) + " }";
            Gson gson = new Gson();
            RefData_List data = gson.fromJson(refDataJSON, RefData_List.class);
            List<RefData> d = data.GetRefData();

            if (webServiceCallExtension == "Jobs")
                SpinnerFunctions.PopulateRefDataSpinnerValues(nWO, R.id.spinJobs, d, spinVal, "Jobs");//load into dropdown

            if (webServiceCallExtension == "Sites")
                SpinnerFunctions.PopulateRefDataSpinnerValues(nWO, R.id.spinSite, d, spinVal, "Sites");
            //else if(webServiceCallExtension == "Users")
            //else if (webServiceCallExtension == "Devices"
        }
        catch (Exception e){
            String msg = "Load " + webServiceCallExtension + " Data Failed";
            Toast toast = Toast.makeText(nWO, msg, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}

class GetDeviceUser extends AsyncTask<String,Void,String> {
    private aLoadingPage loadPage;

    public GetDeviceUser(aLoadingPage LoadingPage) {
        this.loadPage = LoadingPage;
    }

    protected String doInBackground(String... webAPIExtension) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(new HttpGet(GlobalVars.baseAPI_URI + webAPIExtension[0] + "/" + GlobalVars.android_ID));
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            response.getEntity().writeTo(out);
            String respVal = out.toString();
            out.close();
            return respVal;
        } catch (Exception e) {
            return null;
        }
    }

    protected void onPostExecute(String result) {
        //set usrName Variable
        GlobalVars.dUsr = Formatting.RemoveQuotes(Formatting.FormatIncomingNET_JSON(result));

        //Pause for display image //this is retarded but w/e
        try { Thread.sleep(1000); }
        catch (InterruptedException e) { return; }

        //Change program from loading screen
        Intent intent=new Intent(loadPage,aWorkOrdersList.class);
        loadPage.startActivity(intent);
    }
}

class LoadUserWorkOrders extends AsyncTask<String,Void,String>{
    private String webServiceCallExtension;
    private aWorkOrdersList wOL;

    public LoadUserWorkOrders(aWorkOrdersList workOrdersList) {
        this.wOL = workOrdersList;
    }

    protected String doInBackground(String... webAPIExtension) {
        this.webServiceCallExtension = webAPIExtension[0];
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(new HttpGet(GlobalVars.baseAPI_URI + this.webServiceCallExtension + "/" + GlobalVars.android_ID));
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            response.getEntity().writeTo(out);
            String respVal = out.toString();
            out.close();
            return respVal;
        } catch (Exception e) {
            return null;
        }
    }

    protected void onPostExecute(String result) {
        try{
            String workOrderJSON = "{ workOrders:" + Formatting.FormatIncomingNET_JSON(result) + " }";
            Gson gson = new Gson();
            WorkOrder_List data = gson.fromJson(workOrderJSON, WorkOrder_List.class);
            //TODO: populate list grid
            wOL.BuildWorkOrderTable(data.GetWorkOrder());
        }
        catch (Exception e){
            String msg = "Load " + webServiceCallExtension + " Data Failed";
            Toast toast = Toast.makeText(wOL, msg, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}

class SubmitNewWorkOrder extends AsyncTask<NewWorkOrder,Void,Boolean> {
    private aNewWorkOrder nWO;
    private String webServCall;

    public SubmitNewWorkOrder(aNewWorkOrder newWorkOrder, String webServiceCallExtension) {  // can take other params if needed
        this.nWO = newWorkOrder;
        this.webServCall = webServiceCallExtension;
    }

    protected Boolean doInBackground(NewWorkOrder... newWorkOrder) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postReq = new HttpPost(GlobalVars.baseAPI_URI + webServCall);

            com.ajceliano.ajworkorderapp.Obj.NewWorkOrder wO = newWorkOrder[0];
            postReq.setHeader("Content-type", "application/json");
            String jsonPost = String.format("{ ID:%1$s, JobID: %2$s, SubmittedUserID: %3$s, DeviceGUID: '%4$s', RegWorkHours: %5$s, OvertimeWorkHours: %6$s, Subject: '%7$s', Description: '%8$s', LastUpdatedBy: '%9$s' }", wO.GetID(), wO.GetJobID(), 1, wO.GetDeviceGUID(), wO.GetRegWorkHours(), wO.GetOvertimeWorkHours(), wO.GetSubject(), wO.GetDescription(), wO.GetLastUpdatedBy());
            postReq.setEntity(new StringEntity(jsonPost));

            HttpResponse response = httpClient.execute(postReq);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    protected void onPostExecute(Boolean success) {
        //Tell the user that the request has been submitted successfully
        String msg = success ? "Work Order Submitted" : "Failed: Error Submitting";
        Toast toast = Toast.makeText(nWO, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}