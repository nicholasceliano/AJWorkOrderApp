package com.ajceliano.ajworkorderapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ajceliano.ajworkorderapp.Obj.WorkOrder;
import com.google.gson.Gson;

import java.util.List;

public class aWorkOrdersList extends Activity {
    aWorkOrdersList wOL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_orders_list);
        wOL = this;

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

    protected void BuildWorkOrderTable(List<WorkOrder> woList) {
        TableLayout tbl = (TableLayout) findViewById(R.id.tblWorkOrders);
        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);


        //TODO: Need to fix all this formatting
        //add header row
        TextView htxtID = new TextView(this);
        htxtID.setWidth(50);
        TextView htxtJob = new TextView(this);
        htxtJob.setWidth(50);
        TextView htxtSubmittedDate = new TextView(this);
        htxtSubmittedDate.setWidth(50);
        TextView htxtSubject = new TextView(this);
        htxtSubject.setWidth(50);
        TextView htxtReviewed = new TextView(this);
        htxtReviewed.setWidth(50);
        TextView htxtLastUpdatedDate = new TextView(this);
        htxtLastUpdatedDate.setWidth(50);
        TextView htxtLastUpdatedBy = new TextView(this);
        htxtLastUpdatedBy.setWidth(50);
        TextView htxtBlankCol = new TextView(this);
        htxtBlankCol.setWidth(50);

        htxtID.setText("ID");
        htxtJob.setText("Job");
        htxtSubmittedDate.setText("Submitted Date");
        htxtSubject.setText("Subject");
        htxtReviewed.setText("Reviewed Status");
        htxtLastUpdatedDate.setText("Last Update Date");
        htxtLastUpdatedBy.setText("Last Update By");
        htxtLastUpdatedBy.setText("Last Update By");
        htxtBlankCol.setText("");

        row.addView(htxtID);
        row.addView(htxtJob);
        row.addView(htxtSubmittedDate);
        row.addView(htxtSubject);
        row.addView(htxtReviewed);
        row.addView(htxtLastUpdatedDate);
        row.addView(htxtLastUpdatedBy);
        row.addView(htxtBlankCol);

        tbl.addView(row, 0);

        for (int i = 0; i < woList.size(); i++ ) {
            TableRow row2 = new TableRow(this);
            TableRow.LayoutParams lp2 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row2.setLayoutParams(lp2);

            final WorkOrder woItem = woList.get(i);

            TextView txtID = new TextView(this);
            TextView txtJob = new TextView(this);
            TextView txtSubmittedDate = new TextView(this);
            TextView txtSubject = new TextView(this);
            TextView txtReviewed = new TextView(this);
            TextView txtLastUpdatedDate = new TextView(this);
            TextView txtLastUpdatedBy = new TextView(this);
            ImageButton btnEdit = new ImageButton(this);

            txtID.setText(woItem.GetID().toString());
            txtJob.setText(woItem.GetJob());
            txtSubmittedDate.setText(woItem.GetSubmittedDate());
            txtSubject.setText(woItem.GetSubject());
            txtReviewed.setText(woItem.GetReviewed() == null ? "Not Reviewed" : woItem.GetReviewed().toString());
            txtLastUpdatedDate.setText(woItem.GetLastUpdatedDate());
            txtLastUpdatedBy.setText(woItem.GetLastUpdatedBy());
            btnEdit.setImageResource(R.drawable.abc_btn_check_material);

            row2.addView(txtID);
            row2.addView(txtJob);
            row2.addView(txtSubmittedDate);
            row2.addView(txtSubject);
            row2.addView(txtReviewed);
            row2.addView(txtLastUpdatedDate);
            row2.addView(txtLastUpdatedBy);
            row2.addView(btnEdit);

            row2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //opens a populated NewWorkOrders page
                    Intent intent=new Intent(wOL,aNewWorkOrder.class);
                    intent.putExtra("woEdit", new Gson().toJson(woItem));
                    wOL.startActivity(intent);
                }
            });

            tbl.addView(row2, i + 1);
        }
    }
}
