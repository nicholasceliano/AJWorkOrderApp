package com.ajceliano.ajworkorderapp.Obj;

import android.util.EventLogTags;

import java.util.Date;

/**
 * Created by Nick on 2/22/2015.
 */
public class NewWorkOrder {
    private int JobID;
    private String DeviceGUID;
    private String Subject;
    private String Description;

    ////Setters
    public void SetJobID(int val){
        this.JobID = val;
    }
    public void SetDeviceGUID(String val){
        this.DeviceGUID = val;
    }
    public void SetSubject(String val){
        this.Subject = val;
    }
    public void SetDescription(String val){
        this.Description = val;
    }

    ////Getters
    public int GetJobID(){
        return this.JobID;
    }
    public String GetDeviceGUID(){
        return this.DeviceGUID;
    }
    public String GetSubject(){
        return this.Subject;
    }
    public  String GetDescription(){
        return this.Description;
    }
}
