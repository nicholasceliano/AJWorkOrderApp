package com.ajceliano.ajworkorderapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class aWorkOrdersList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_orders_list);

        //Load label
        TextView welcomeMsg = (TextView)findViewById(R.id.lblWelcomeMessage);
        if (GlobalVars.dUsr.equals("null")) {
            welcomeMsg.setText("Device must be linked to system before use");
            //Disable controls on the page

        } else {
            welcomeMsg.setText("Welcome " + GlobalVars.dUsr);

            //Load Page Data //Load the past 10 work orders
            new LoadUserWorkOrders(this).execute("WorkOrders");

            //Button Listners
            findViewById(R.id.btnNewWorkOrder).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(aWorkOrdersList.this,aNewWorkOrder.class);
                    startActivity(intent);
                }
            });
        }
    }
}
