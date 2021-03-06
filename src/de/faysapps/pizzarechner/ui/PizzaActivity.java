package de.faysapps.pizzarechner.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import de.faysapps.pizzarechner.R;
import de.faysapps.pizzarechner.model.Pizza;
import de.faysapps.pizzarechner.model.Shapes;

public class PizzaActivity extends Activity implements OnClickListener {
	
	EditText diameterET, lengthET, widthET, prizeET;
	TextView diameterTV, lengthTV, widthTV;
	Button button1;
	Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pizza);
		
        diameterET = (EditText) findViewById(R.id.diameterET);
        lengthET = (EditText) findViewById(R.id.lengthET);
        widthET = (EditText) findViewById(R.id.widthET);
        button1 = (Button) findViewById(R.id.calculateButton);
        prizeET = (EditText) findViewById(R.id.prizeET);
        diameterTV = (TextView) findViewById(R.id.diameterTV);
        lengthTV = (TextView) findViewById(R.id.lengthTV);
        widthTV = (TextView) findViewById(R.id.widthTV);

		lengthTV.setVisibility(View.GONE);
		widthTV.setVisibility(View.GONE);
		lengthET.setVisibility(View.GONE);
		widthET.setVisibility(View.GONE); 
        
        button1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		double diameter, length, width, prize = 0;
		Pizza pizza = null;
		try {
			prize = Double.parseDouble(prizeET.getText().toString());
		} catch (NumberFormatException e) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			builder.setTitle("Fehler");
			builder.setMessage(e.getMessage());
			builder.create().show();
			return;
		}
		if (!diameterET.getText().toString().equals("")) {	//diameter
			diameter = Double.parseDouble(diameterET.getText().toString());
			pizza = new Pizza(Shapes.circle(diameter), prize);
		} else {	//length and width
			length = Double.parseDouble(lengthET.getText().toString());
			width = Double.parseDouble(widthET.getText().toString());
			pizza = new Pizza(Shapes.rectangle(width, length), prize);
		}
		Log.d("pizzarechner", pizza.toString());
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("pizza", pizza);
		setResult(RESULT_OK, intent);
		finish();
	}
	
	public void onRadioButtonClicked(View view) {
		boolean checked = ((RadioButton) view).isChecked();
		switch (view.getId()) {
		case R.id.roundRB:
			if (checked) {
				lengthTV.setVisibility(View.GONE);
				widthTV.setVisibility(View.GONE);
				lengthET.setVisibility(View.GONE);
				widthET.setVisibility(View.GONE);
				diameterTV.setVisibility(View.VISIBLE);
				diameterET.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.rectangularRB:
			if (checked) {
				diameterTV.setVisibility(View.GONE);
				diameterET.setVisibility(View.GONE);
				lengthTV.setVisibility(View.VISIBLE);
				widthTV.setVisibility(View.VISIBLE);
				lengthET.setVisibility(View.VISIBLE);
				widthET.setVisibility(View.VISIBLE);
			}
			break;
		default:
			Log.d("pizzarechner", "Unbekannter RadioButton wurde gedr�ckt\n at PizzaActivity:51");
		}
	}

}
