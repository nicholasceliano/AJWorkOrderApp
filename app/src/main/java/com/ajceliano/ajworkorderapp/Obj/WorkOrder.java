package com.ajceliano.ajworkorderapp.Obj;

import java.io.Serializable;

/**
 * Created by Nick on 2/28/2015.
 */
public class WorkOrder {
    private Integer ID;
    private String Job;
    private String SubmittedByFirstName;
    private String SubmittedByLastName;
    private String SubmittedDate;
    private String Subject;
    private String Description;
    private Boolean Reviewed;
    private String LastUpdatedBy;
    private String LastUpdatedDate;

    ////Setters
    public void SetID(Integer val){
        this.ID = val;
    }
    public void SetJob(String val){
        this.Job = val;
    }
    public void SetSubmittedByFirstName(String val){
        this.SubmittedByFirstName = val;
    }
    public void SetSubmittedByLastName(String val){
        this.SubmittedByLastName = val;
    }
    public void SetSubmittedDate(String val){
        this.SubmittedDate = val;
    }
    public void SetSubject(String val){
        this.Subject = val;
    }
    public void SetDescription(String val){
        this.Description = val;
    }
    public void SetReviewed(Boolean val){
        this.Reviewed = val;
    }
    public void SetLastUpdatedBy(String val){
        this.LastUpdatedBy = val;
    }
    public void SetLastUpdatedDate(String val){
        this.LastUpdatedDate = val;
    }

    ////Getters
    public Integer GetID(){
        return this.ID;
    }
    public String GetJob(){
        return this.Job;
    }
    public String GetSubmittedByFirstName(){
        return this.SubmittedByFirstName;
    }
    public String GetSubmittedByLastName(){
        return this.SubmittedByLastName;
    }
    public String GetSubmittedDate(){
        return this.SubmittedDate;
    }
    public String GetSubject(){
        return this.Subject;
    }
    public String GetDescription(){
        return this.Description;
    }
    public Boolean GetReviewed(){
        return this.Reviewed;
    }
    public String GetLastUpdatedBy(){
        return this.LastUpdatedBy;
    }
    public String GetLastUpdatedDate(){
        return this.LastUpdatedDate;
    }
}
