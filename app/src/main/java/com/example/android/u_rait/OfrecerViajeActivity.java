package com.example.android.u_rait;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class OfrecerViajeActivity extends AppCompatActivity implements View.OnClickListener{
    private Button tiempo;
    Calendar mcurrentTime = Calendar.getInstance();
    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
    int minute = mcurrentTime.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofrecer_viaje);

        tiempo = (Button) findViewById(R.id.textViewTime);
        setTiempo(hour, minute);
        tiempo.setOnClickListener(this);
    }

    private void setTiempo(int selectedHour,int selectedMinute){
        String hourScheledule = "AM";
        if(selectedHour >12){
            selectedHour= selectedHour %12;
            hourScheledule = "PM";
        }
        tiempo.setText( selectedHour + ":" + selectedMinute + " " + hourScheledule);
    }

    @Override
    public void onClick(View view) {
        if(view == tiempo){
            setTiempo();
        }
    }

    private void setTiempo(){
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                setTiempo(selectedHour,selectedMinute);
            }
        }, hour, minute, false);//No 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
}
