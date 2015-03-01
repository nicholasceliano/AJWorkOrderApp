package com.ajceliano.ajworkorderapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.ajceliano.ajworkorderapp.Obj.NewWorkOrder;
import com.ajceliano.ajworkorderapp.Obj.WorkOrder;
import com.google.gson.Gson;

public class aNewWorkOrder extends Activity {
    public aNewWorkOrder mA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mA = this;
        setContentView(R.layout.activity_new_work_order);
        //TODO: Fix App Launch Icon to the AJ

        final EditText subject = (EditText) findViewById(R.id.txtSubject);
        final EditText desc = (EditText) findViewById(R.id.txtDescription);
        Spinner spinJobs = (Spinner) findViewById(R.id.spinJobs);
        Integer woID = null;
        String woLastUpdateBy = null;

        //if Edit Work Order
        Intent i = getIntent();
        String woEditJSON = i.getStringExtra("woEdit");

        if (woEditJSON != null){
            WorkOrder woEdit = new Gson().fromJson(i.getStringExtra("woEdit"), WorkOrder.class);
            //need to fill in controls with data
            woID = woEdit.GetID();
            new GetReferenceData(this, woEdit.GetJob()).execute("Jobs");
            subject.setText(woEdit.GetSubject());
            desc.setText(woEdit.GetDescription());
            woLastUpdateBy = GlobalVars.dUsr;
        } else{
            //Populate Dropdown List
            new GetReferenceData(this).execute("Jobs");
        }
        final Integer woIDFinal = woID;
        final String woLastUpdateByFinal = woLastUpdateBy;

        //Button Listeners
        findViewById(R.id.btnSubmitWorkOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewWorkOrder wO = new NewWorkOrder();
                wO.SetID(woIDFinal);
                wO.SetJobID(GlobalVars.newJobID);
                wO.SetDeviceGUID(GlobalVars.android_ID);
                wO.SetSubject(subject.getText().toString());
                wO.SetDescription(desc.getText().toString());
                wO.SetLastUpdatedBy(woLastUpdateByFinal);

                new SubmitNewWorkOrder(mA, "WorkOrders").execute(wO);

                Intent intent = new Intent(aNewWorkOrder.this, aWorkOrdersList.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent=new Intent(aNewWorkOrder.this,aWorkOrdersList.class);
                startActivity(intent);
            }
        });

        ((EditText) findViewById(R.id.txtSubject)).addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) {
                for (int i = s.length(); i > 0; i--) {
                    if (s.subSequence(i - 1, i).toString().equals("\n"))
                        s.replace(i - 1, i, "");
                }
                String myTextString = s.toString();
            }
        });
    }
}