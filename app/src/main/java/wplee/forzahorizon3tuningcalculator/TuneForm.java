package wplee.forzahorizon3tuningcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class TuneForm extends AppCompatActivity {
    public final static String TUNE_MESSAGE = "com.wplee.forzahorizon3tuningcalculator.TUNE_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tune_form);

        final Spinner unitSpinner = (Spinner) findViewById(R.id.unitSpinner);
        final TextView weightUnitTextView = (TextView) findViewById(R.id.weightUnitTextView);
        final EditText weightEditText = (EditText) findViewById(R.id.weightEditText);
        final SeekBar distribSeekBar = (SeekBar) findViewById(R.id.distribSeekBar);
        final EditText distribEditText = (EditText) findViewById(R.id.distribEditText);
        final Spinner classSpinner = (Spinner) findViewById(R.id.classSpinner);
        final Spinner drivetrainSpinner = (Spinner) findViewById(R.id.drivetrainSpinner);
        Button tuneButton = (Button) findViewById(R.id.tuneButton);

        // update units on item selected
        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    weightUnitTextView.setText(R.string.imperial_weight_unit);
                } else {
                    weightUnitTextView.setText(R.string.metric_weight_unit);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // update distrib textview on seekbar changed
        distribSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    distribEditText.setText(Integer.toString(progress));
                    distribEditText.setSelection(distribEditText.getText().length());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // update distrib seekbar on text changed
        distribEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    distribEditText.setText("0");
                    distribEditText.setSelection(distribEditText.getText().length());
                    distribSeekBar.setProgress(0);
                } else {
                    int progress = Integer.parseInt(s.toString());

                    if (progress > 100) {
                        distribSeekBar.setProgress(100);
                        distribEditText.setText("100");
                    } else {
                        if (s.charAt(0) == '0' && s.length() > 1) {
                            distribEditText.setText(s.subSequence(1, s.length()));
                            distribEditText.setSelection(distribEditText.getText().length());
                        }
                        distribSeekBar.setProgress(progress);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // set distrib to default value
        distribSeekBar.setProgress(50);
        distribEditText.setText("50");

        // open and pass information to result activity
        tuneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TuneResult.class);
                int[] message = {
                        unitSpinner.getSelectedItemPosition(),
                        Integer.parseInt(weightEditText.getText().toString()),
                        distribSeekBar.getProgress(),
                        classSpinner.getSelectedItemPosition(),
                        drivetrainSpinner.getSelectedItemPosition()
                };
                intent.putExtra(TUNE_MESSAGE, message);
                startActivity(intent);
            }
        });

        weightEditText.requestFocus();
    }
}
