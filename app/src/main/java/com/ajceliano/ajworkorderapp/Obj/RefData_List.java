package com.ajceliano.ajworkorderapp.Obj;

import java.util.List;

/**
 * Created by Nick on 2/22/2015.
 */
public class RefData_List{
    private List<RefData> refData;

    ////Getters
    public List<RefData> GetRefData(){
        return this.refData;
    }

    ////Setters
    public void SetRefData(RefData val){
        this.refData.add(val);
    }
}
