package com.ajceliano.ajworkorderapp.Helpers;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ajceliano.ajworkorderapp.GlobalVars;
import com.ajceliano.ajworkorderapp.Obj.RefData;
import com.ajceliano.ajworkorderapp.R;
import com.ajceliano.ajworkorderapp.aNewWorkOrder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 00619461 on 2/27/2015.
 */
public class SpinnerFunctions {

    public static void PopulateRefDataSpinnerValues(final aNewWorkOrder nWO, final int spinnerID, final List<RefData> data, String selectedVal, String listName) {
        final Spinner s = (Spinner) nWO.findViewById(spinnerID);

        List<String> valList = new ArrayList();
        valList.add("Select " + listName);

        for (Iterator<RefData> i = data.iterator(); i.hasNext(); ) {
            RefData item = i.next();
            valList.add(item.GetValue());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(nWO, android.R.layout.simple_spinner_item, valList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        if (selectedVal != null)
            SetSpinnerValue(s, selectedVal);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //arg 3 is index, if 0 it means they clicked "--please select--"
                if (arg3 != 0) {
                    String sText = s.getSelectedItem().toString();

                    for (Iterator<RefData> i = data.iterator(); i.hasNext(); ) {
                        RefData item = i.next();
                        if (item.GetValue() == sText) {
                            //if s.getId() == "spinJobs")
                                GlobalVars.newJobID = item.GetID();
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //this is what happens if we select nothing.
                return;
            }
        });
    }
    
    public static void SetSpinnerValue(Spinner s, String compareValue){
        int index = 0;
        for (int i=0;i<s.getCount();i++){
            if (s.getItemAtPosition(i).toString().equalsIgnoreCase(compareValue)){
                index = i;
                break;
            }
        }
        s.setSelection(index);
    }


    public static Number ConvertHoursWorkedToDecimal(String val){
        if (val.equals(":30"))
            return .5;
        else if (val.equals("1:00"))
            return 1;
        else if (val.equals("1:30"))
            return 1.5;
        else if (val.equals("2:00"))
            return 2;
        else if (val.equals("2:30"))
            return 2.5;
        else if (val.equals("3:00"))
            return 3;
        else if (val.equals("3:30"))
            return 3.5;
        else if (val.equals("4:00"))
            return 4;
        else if (val.equals("4:30"))
            return 4.5;
        else if (val.equals("5:00"))
            return 5;
        else if (val.equals("5:30"))
            return 5.5;
        else if (val.equals("6:00"))
            return 6;
        else if (val.equals("6:30"))
            return 6.5;
        else if (val.equals("7:00"))
            return 7;
        else if (val.equals("7:30"))
            return 7.5;
        else if (val.equals("8:00"))
            return 8;
        else
            return 0;
    }
}


