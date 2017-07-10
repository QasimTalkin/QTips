package com.example.qasim.qtips;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
    double totalamount, billamount;
    int splitNumber;
    double tipPerc;
    String symbol = "CAD$";
    EditText amount_Total ; //finded view casted to edit text
    EditText split_number ;
    EditText tip_perc;
    String prefname = "myPrefs";
    SharedPreferences sharedPref;


    //DatabaseHelper tipcalcDB;



    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent si = new Intent(MainActivity.this, Setting2.class);
            startActivity(si);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);// lunch activity main
        amount_Total = (EditText) findViewById(R.id.amount_value);
        split_number = (EditText) findViewById(R.id.split_value);
        tip_perc = (EditText) findViewById(R.id.perc_value);
        tip_perc.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});



        sharedPref = getSharedPreferences(prefname, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        tipPerc = (double) sharedPref.getInt("idName", 0);
        symbol = sharedPref.getString("Symb", "CAD$");
        editor.apply();


    }


        public void calculate_tip (View v) {

                if (split_number.getText().toString().isEmpty()){
                    splitNumber = 1;
                }
                else{

                    splitNumber = Integer.valueOf(split_number.getText().toString());
                }

                if (tip_perc.getText().toString().isEmpty()){
                    tipPerc= (double) sharedPref.getInt("idName", 0);
                }
                else{
                    tipPerc = Integer.valueOf(tip_perc.getText().toString());
                }

                if((TextUtils.isEmpty(amount_Total.getText().toString()))|| ((Double.valueOf(amount_Total.getText().toString()))==0.0) ) {
                    amount_Total.setError("Please enter an amount greater than 0");
                }


                if((TextUtils.isEmpty(tip_perc.getText().toString()))|| ((Integer.valueOf(tip_perc.getText().toString()))==0.0) || tipPerc>100 || tipPerc==0) {
                    if (!(tipPerc < 100 && tipPerc >= 0)) {
                        tip_perc.setError("Please enter Tip valuue between 1 to 100");
                    }
                }


                if (!((TextUtils.isEmpty(amount_Total.getText().toString())) || ((Double.valueOf(amount_Total.getText().toString())) == 0.0) || tipPerc>100) ) {
                    totalamount = Double.valueOf(amount_Total.getText().toString());// asigning value to work with
                    billamount = (totalamount+(totalamount*(tipPerc/100)));
                    AlertDialog fResult = new AlertDialog.Builder(MainActivity.this).create();
                    AlertDialog.Builder finalResults = new AlertDialog.Builder(this);
                    finalResults.setMessage("Your Total Bill With Tip " + symbol + billamount +  "\n\n Individual Amount " + symbol+ billamount/splitNumber + "\n\n The % value used for Tip was " + tipPerc+"%")
                            .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setTitle("Results")
                            .setIcon(R.drawable.money)
                            .create();
                    finalResults.show();

                }
        }    // calculate_tip

        public void suggest_tip(View view) {


            if (split_number.getText().toString().isEmpty()){
                splitNumber = 1;
            }
            else{

                splitNumber = Integer.valueOf(split_number.getText().toString());
            }

            if (tip_perc.getText().toString().isEmpty()){
                tipPerc= (double) sharedPref.getInt("idName", 0);
            }
            else{
                tipPerc = Integer.valueOf(tip_perc.getText().toString());
            }


            if((TextUtils.isEmpty(amount_Total.getText().toString()))|| ((Double.valueOf(amount_Total.getText().toString()))==0.0) ) {
                amount_Total.setError("Please enter an amount greater than 0");
            }


//            if((TextUtils.isEmpty(tip_perc.getText().toString()))|| ((Integer.valueOf(tip_perc.getText().toString()))==0.0) || tipPerc>0) {
//                tip_perc.setError("Use the Rating bar for tip suggestion.");
//            }

            else if (!((TextUtils.isEmpty(amount_Total.getText().toString())) || ((Double.valueOf(amount_Total.getText().toString())) == 0.0))|| tipPerc>=0 ) {
                totalamount = Double.valueOf(amount_Total.getText().toString());// asigning value to work with
                Intent intent1 = new Intent(MainActivity.this, Rate_cal2.class);
                Bundle b = new Bundle();
                b.putDouble("amount", totalamount);
                b.putInt("split", splitNumber);
                b.putString("Symb", symbol);
                intent1.putExtras(b);
                startActivity(intent1);
                finish();
            }


        }







    }











