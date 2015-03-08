package com.ajceliano.ajworkorderapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ajceliano.ajworkorderapp.Obj.WorkOrder;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        final TextView lblAndroidID = (TextView)findViewById(R.id.lblAndroidID);
        lblAndroidID.setText(GlobalVars.android_ID);

        if (GlobalVars.dUsr.equals("null")) {
            welcomeMsg.setText("Device must be linked to system before use");
            //Disable controls on the page

        } else {
            welcomeMsg.setText("Welcome " + GlobalVars.dUsr);

            //Load Page Data //Load the past 10 work orders
            new LoadUserWorkOrders(this).execute("WorkOrders");

            //Button Listeners
            findViewById(R.id.btnSettings).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //make the android ID visible
                    lblAndroidID.setVisibility(lblAndroidID.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                }
            });

            findViewById(R.id.btnRefresh).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new LoadUserWorkOrders(wOL).execute("WorkOrders");
                }
            });

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

        //Remove Rows if there is more than 1
        if (tbl.getChildCount() > 1)
            tbl.removeViews(1, tbl.getChildCount() - 1);

        if (woList.size() == 0){
            //Build friendly Row
            TableRow r = new TableRow(this);
            r.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));

            TextView txtNoResults = new TextView(this);
            txtNoResults.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, .09f));
            FormatTextView(txtNoResults);
            txtNoResults.setBackground(getResources().getDrawable(R.drawable.table_row_odd_background));
            txtNoResults.setGravity(Gravity.CENTER);

            txtNoResults.setText("No Work Orders Pending");

            r.addView(txtNoResults);
            tbl.addView(r, 1);
        }

        for (int i = 0; i < woList.size(); i++ ) {
            Integer rowNum = i + 1;
            TableRow r = new TableRow(this);
            r.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));

            final WorkOrder woItem = woList.get(i);

            TextView txtID = new TextView(this);
            TextView txtJob = new TextView(this);
            TextView txtSubmittedDate = new TextView(this);
            TextView txtSubject = new TextView(this);
            TextView txtReviewed = new TextView(this);

            txtID.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, .09f));
            txtJob.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, .25f));
            txtSubmittedDate.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, .2f));
            txtSubject.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, .25f));
            txtReviewed.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, .21f));

            FormatTextView(txtID);
            FormatTextView(txtJob);
            FormatTextView(txtSubmittedDate);
            FormatTextView(txtSubject);
            FormatTextView(txtReviewed);

            if (rowNum % 2 == 0) {
                txtID.setBackground(getResources().getDrawable(R.drawable.table_row_even_background));
                txtJob.setBackground(getResources().getDrawable(R.drawable.table_row_even_background));
                txtSubmittedDate.setBackground(getResources().getDrawable(R.drawable.table_row_even_background));
                txtSubject.setBackground(getResources().getDrawable(R.drawable.table_row_even_background));
                txtReviewed.setBackground(getResources().getDrawable(R.drawable.table_row_even_background));
            } else{
                txtID.setBackground(getResources().getDrawable(R.drawable.table_row_odd_background));
                txtJob.setBackground(getResources().getDrawable(R.drawable.table_row_odd_background));
                txtSubmittedDate.setBackground(getResources().getDrawable(R.drawable.table_row_odd_background));
                txtSubject.setBackground(getResources().getDrawable(R.drawable.table_row_odd_background));
                txtReviewed.setBackground(getResources().getDrawable(R.drawable.table_row_odd_background));
            }

            String formattedSubDate;
            try {
                Date date = new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(woItem.GetSubmittedDate());
                formattedSubDate = new SimpleDateFormat("MMM dd, yyyy").format(date);
            }
            catch (Exception e){
                formattedSubDate = woItem.GetSubmittedDate();
            }

            txtID.setText(woItem.GetID().toString());
            txtJob.setText(woItem.GetJob());
            txtSubmittedDate.setText(formattedSubDate);
            txtSubject.setText(woItem.GetSubject());
            txtReviewed.setText(woItem.GetReviewed() == null ? "Not Reviewed" : "Rejected");

            r.addView(txtID);
            r.addView(txtJob);
            r.addView(txtSubmittedDate);
            r.addView(txtSubject);
            r.addView(txtReviewed);

            r.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //opens a populated NewWorkOrders page
                    Intent intent = new Intent(wOL, aNewWorkOrder.class);
                    intent.putExtra("woEdit", new Gson().toJson(woItem));
                    wOL.startActivity(intent);
                }
            });

            tbl.addView(r, rowNum);
        }
    }

    private void FormatTextView(TextView tv) {
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setSingleLine(true);
        tv.setTextSize(17);
        tv.setPadding(5,5,5,5);
    }
}
