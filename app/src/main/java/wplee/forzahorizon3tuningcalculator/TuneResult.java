package wplee.forzahorizon3tuningcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TuneResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tune_result);

        TextView camberFrontValueTextView = (TextView) findViewById(R.id.camberFrontValueTextView);
        TextView camberRearValueTextView = (TextView) findViewById(R.id.camberRearValueTextView);
        TextView casterValueTextView = (TextView) findViewById(R.id.casterValueTextView);
        TextView rollbarsFrontValueTextView = (TextView) findViewById(R.id.rollbarsFrontValueTextView);
        TextView rollbarsRearValueTextView = (TextView) findViewById(R.id.rollbarsRearValueTextView);
        TextView springsFrontValueTextView = (TextView) findViewById(R.id.springsFrontValueTextView);
        TextView springsRearValueTextView = (TextView) findViewById(R.id.springsRearValueTextView);
        TextView reboundFrontValueTextView = (TextView) findViewById(R.id.reboundFrontValueTextView);
        TextView reboundRearValueTextView = (TextView) findViewById(R.id.reboundRearValueTextView);
        TextView bumpFrontValueTextView = (TextView) findViewById(R.id.bumpFrontValueTextView);
        TextView bumpRearValueTextView = (TextView) findViewById(R.id.bumpRearValueTextView);
        TextView diffAccelFrontValueTextView = (TextView) findViewById(R.id.diffAccelFrontValueTextView);
        TextView diffAccelRearValueTextView = (TextView) findViewById(R.id.diffAccelRearValueTextView);
        TextView diffDecelFrontValueTextView = (TextView) findViewById(R.id.diffDecelFrontValueTextView);
        TextView diffDecelRearValueTextView = (TextView) findViewById(R.id.diffDecelRearValueTextView);
        TextView splitValueTextView = (TextView) findViewById(R.id.splitValueTextView);
        Button backButton = (Button) findViewById(R.id.backButton);

        // process received information
        Intent intent = getIntent();
        int[] message = intent.getIntArrayExtra(TuneForm.TUNE_MESSAGE);
        int unit = message[0];
        int weight = message[1];
        int distrib = message[2];
        int carClass = message[3];
        int drivetrain = message[4];

        // compute camber
        if (carClass == 0) {
            camberFrontValueTextView.setText("-3.50");
            camberRearValueTextView.setText("-2.50");
        } else if (carClass == 1) {
            camberFrontValueTextView.setText("-2.30");
            camberRearValueTextView.setText("-1.20");
        } else {
            camberFrontValueTextView.setText("-1.20");
            camberRearValueTextView.setText("-0.50");
        }

        // compute caster
        if (carClass == 0) {
            casterValueTextView.setText("5-6");
        } else if (carClass == 1) {
            casterValueTextView.setText("6-6.5");
        } else {
            casterValueTextView.setText("7");
        }

        // compute rollbars
        double rollbarsFrontValue = 65.0 * (distrib / 100.0);
        double rollbarsRearValue = 65.0 * (1.0 - (distrib / 100.0));
        rollbarsFrontValueTextView.setText(String.format("%.2f", rollbarsFrontValue));
        rollbarsRearValueTextView.setText(String.format("%.2f", rollbarsRearValue));

        // compute springs
        double springsFrontValue, springsRearValue;
        if (unit == 0) {
            springsFrontValue = (weight * (distrib / 100.0)) / 2.0;
            springsRearValue = (weight * (1.0 - (distrib / 100.0))) / 2.0;
        } else {
            springsFrontValue = (((weight * 2.205) / 2.0) * (distrib / 100.0)) / 5.71;
            springsRearValue = (((weight * 2.205) / 2.0) - (((weight * 2.205) / 2.0) * (distrib / 100.0))) / 5.71;
        }
        springsFrontValueTextView.setText(String.format("%.2f", springsFrontValue));
        springsRearValueTextView.setText(String.format("%.2f", springsRearValue));

        //compute rebound
        double reboundFrontValue, reboundRearValue;
        if (unit == 0) {
            reboundFrontValue = (springsFrontValue / 100.0) + 1.0;
            reboundRearValue = (springsRearValue / 100.0) + 1.0;
        } else {
            reboundFrontValue = ((((weight * 2.205) / 2.0) * (distrib / 100.0)) / 100.0) + 1.0;
            reboundRearValue = ((((weight * 2.205) / 2.0) - (((weight * 2.205) / 2.0) * (distrib / 100.0))) / 100.0) + 1.0;
        }
        reboundFrontValueTextView.setText(String.format("%.2f", reboundFrontValue));
        reboundRearValueTextView.setText(String.format("%.2f", reboundRearValue));

        //compute bump
        double bumpFrontValue = reboundFrontValue * 0.66;
        double bumpRearValue = reboundRearValue * 0.66;
        bumpFrontValueTextView.setText(String.format("%.2f", bumpFrontValue));
        bumpRearValueTextView.setText(String.format("%.2f", bumpRearValue));

        //compute differential
        if (drivetrain == 0) {
            diffAccelFrontValueTextView.setText("29");
            diffAccelRearValueTextView.setText("0");
            diffDecelFrontValueTextView.setText("59");
            diffDecelRearValueTextView.setText("18");
            splitValueTextView.setText("69");
        } else if (drivetrain == 1) {
            diffAccelRearValueTextView.setText("35-60");
            diffDecelRearValueTextView.setText("8-16");
        } else {
            diffAccelFrontValueTextView.setText("25-45");
            diffDecelFrontValueTextView.setText("8-16");
        }

        // go back to previous activity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
