package com.ajceliano.ajworkorderapp;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class aNewWorkOrder extends Activity {
    public aNewWorkOrder mA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mA = this;
        setContentView(R.layout.activity_new_work_order);
        //TODO: Fix App Launch Icon to the AJ


        //Populate page Data
        new GetReferenceData(this).execute("Jobs");

        //Button Listeners
        findViewById(R.id.btnSubmitWorkOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText subject = (EditText)findViewById(R.id.txtSubject);
                EditText desc = (EditText)findViewById(R.id.txtDescription);

                com.ajceliano.ajworkorderapp.Obj.NewWorkOrder wO = new com.ajceliano.ajworkorderapp.Obj.NewWorkOrder();
                wO.SetJobID(GlobalVars.newJobID);
                wO.SetDeviceGUID(GlobalVars.android_ID);
                wO.SetSubject(subject.getText().toString());
                wO.SetDescription(desc.getText().toString());

                new SubmitNewWorkOrder(mA, "WorkOrders").execute(wO);

                Intent intent=new Intent(aNewWorkOrder.this,aWorkOrdersList.class);
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
    }
}