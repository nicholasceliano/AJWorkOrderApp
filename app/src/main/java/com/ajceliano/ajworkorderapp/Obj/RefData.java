package com.ajceliano.ajworkorderapp.Obj;

/**
 * Created by Nick on 2/22/2015.
 */

public class RefData{
    private Integer ID;
    private String Value;
    private Boolean Enabled;

    ////Getters
    public Integer GetID(){
        return this.ID;
    }
    public String GetValue(){
        return this.Value;
    }
    public Boolean GetEnabled(){
        return this.Enabled;
    }

    ////Setters
    public void SetID(Integer id){
        this.ID = id;
    }
    public void SetValue(String val){
        this.Value = val;
    }
    public void SetEnabled(Boolean val){
        this.Enabled = val;
    }
}