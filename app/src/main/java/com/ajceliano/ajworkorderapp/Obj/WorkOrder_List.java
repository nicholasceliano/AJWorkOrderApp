package com.ajceliano.ajworkorderapp.Obj;

import java.util.List;

/**
 * Created by Nick on 2/28/2015.
 */
public class WorkOrder_List {
    private List<WorkOrder> workOrders;

    ////Getters
    public List<WorkOrder> GetWorkOrder(){
        return this.workOrders;
    }

    ////Setters
    public void SetWorkOrder(WorkOrder val){
        this.workOrders.add(val);
    }
}
