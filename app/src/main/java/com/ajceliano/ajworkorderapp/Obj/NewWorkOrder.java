package com.ajceliano.ajworkorderapp.Obj;

import android.util.EventLogTags;

import java.util.Date;

/**
 * Created by Nick on 2/22/2015.
 */
public class NewWorkOrder {
    private Integer ID;
    private Integer JobID;
    private String DeviceGUID;
    private String Subject;
    private String Description;
    private String LastUpdatedBy;

    ////Setters
    public void SetID(Integer val){
        this.ID = val;
    }
    public void SetJobID(Integer val){
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
    public void SetLastUpdatedBy(String val){
        this.LastUpdatedBy = val;
    }

    ////Getters
    public Integer GetID(){
        return this.ID;
    }
    public Integer GetJobID(){
        return this.JobID;
    }
    public String GetDeviceGUID(){
        return this.DeviceGUID;
    }
    public String GetSubject(){
        return this.Subject;
    }
    public String GetDescription(){
        return this.Description;
    }
    public String GetLastUpdatedBy(){
        return this.LastUpdatedBy;
    }
}
