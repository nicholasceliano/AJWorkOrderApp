package com.ajceliano.ajworkorderapp.Helpers;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ajceliano.ajworkorderapp.GlobalVars;
import com.ajceliano.ajworkorderapp.Obj.RefData;
import com.ajceliano.ajworkorderapp.aNewWorkOrder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 00619461 on 2/27/2015.
 */
public class SpinnerFunctions {

    public static void PopulateRefDataSpinnerValues(final aNewWorkOrder mA, final int spinnerID, final List<RefData> data) {
        final Spinner s = (Spinner) mA.findViewById(spinnerID);

        List<String> valList = new ArrayList();
        valList.add("Select Job");

        for (Iterator<RefData> i = data.iterator(); i.hasNext(); ) {
            RefData item = i.next();
            valList.add(item.GetValue());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mA, android.R.layout.simple_spinner_item, valList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

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
}


