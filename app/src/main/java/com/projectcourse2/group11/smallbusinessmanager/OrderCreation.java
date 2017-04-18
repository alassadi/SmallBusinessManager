package com.projectcourse2.group11.smallbusinessmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import com.projectcourse2.group11.smallbusinessmanager.model.Address;
import com.projectcourse2.group11.smallbusinessmanager.model.Order;
import com.projectcourse2.group11.smallbusinessmanager.model.Worker;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivana on 5/2/2017.
 */

public class OrderCreation extends Activity implements View.OnClickListener {
    private Button buttonOK;
    private Button buttonCancel;
    private List<Worker> workerList = new ArrayList<>();
    private NumberPicker workerView;
    private EditText descriptionView;
    private Worker selectedWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Hardcoded for testing purposes
        workerList.add(new Worker("197210102312","Erdogan","Tayyip","911","dick_Tator@yomama.org",new Address("street","city","12345","Country")));
        workerList.add(new Worker("197210103232","Phat","American","911","dat_Wall@trump.org",new Address("street","city","12345","Country")));

        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_order);

        buttonOK = (Button)findViewById(R.id.buttonOK);
        buttonCancel = (Button)findViewById(R.id.buttonCancel);
        descriptionView = (EditText) findViewById(R.id.orderDescription);
        workerView = (NumberPicker) findViewById(R.id.workerPicker);
        String[] workersNames = new String[workerList.size()];
        for(int i = 0; i<workerList.size();i++){
            workersNames[i]=workerList.get(i).getFirstName()+" "+workerList.get(i).getLastName();
        }
        workerView.setMinValue(0);
        workerView.setMaxValue(workerList.size() - 1);
        workerView.setDisplayedValues(workersNames);
        workerView.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        NumberPicker.OnValueChangeListener myValChangedListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                selectedWorker=workerList.get(newVal);
            }
        };

        workerView.setOnValueChangedListener(myValChangedListener);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
                Intent MainIntent = new Intent(OrderCreation.this, MainActivity.class);
                startActivity(MainIntent);
            }});

    }

    @Override
    public void onClick(View v) {
        if (v==buttonOK){
            createOrder();
        }
    }

    private void createOrder() {
        String description = descriptionView.getText().toString().trim();
        Order order = new Order(description,selectedWorker);

    }
}