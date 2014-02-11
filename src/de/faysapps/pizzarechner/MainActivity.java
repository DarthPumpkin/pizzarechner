package de.faysapps.pizzarechner;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends Activity implements OnClickListener {
	
	private TableLayout table;
	private Button button;
	private ArrayList<Pizza> pizzas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        pizzas = new ArrayList<Pizza>();
        button = (Button) findViewById(R.id.calculateButton);
        button.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_buttons, menu);
        return true;
    }


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_add) {
			Intent intent = new Intent(this, PizzaActivity.class);
			startActivityForResult(intent, 0);
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			Pizza pizza = (Pizza) bundle.get("pizza");
			pizzas.add(pizza);
			table = (TableLayout) findViewById(R.id.pizzaTable);
        	TableRow row = new TableRow(this);
        	TextView rowText = new TextView(this);
        	rowText.setText(pizza.toString());
        	row.addView(rowText);
        	table.addView(row);
		}
	}


	@Override
	public void onClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.setTitle("Ihr Ergebnis");
		builder.setMessage("Hier sollte das Ergebnis stehen");
		builder.create().show();
	}
    
}
