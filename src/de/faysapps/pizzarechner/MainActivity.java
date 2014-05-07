package de.faysapps.pizzarechner;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.ojalgo.optimisation.Expression;
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.GenericSolver;
import org.ojalgo.optimisation.Variable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener {
	
	private Button button;
	private EditText personsET;
	private ArrayList<Pizza> pizzas;
	private boolean inEditingMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        inEditingMode = false;
        pizzas = new ArrayList<Pizza>();
        button = (Button) findViewById(R.id.calculateButton);
        personsET = (EditText) findViewById(R.id.personsET);
        button.setOnClickListener(this);
    }


    @Override
	public boolean onPrepareOptionsMenu(Menu menu) {
    	MenuItem addButton = menu.getItem(0);
    	MenuItem editButton = menu.getItem(1);
    	MenuItem deleteButton = menu.getItem(2);
    	MenuItem editingDoneButton = menu.getItem(3);
    	if (inEditingMode) {
    		addButton.setVisible(false);
    		editButton.setVisible(false);
    		deleteButton.setVisible(true);
    		editingDoneButton.setVisible(true);
    	} else {
    		addButton.setVisible(true);
    		editButton.setVisible(true);
    		deleteButton.setVisible(false);
    		editingDoneButton.setVisible(false);
    	}
		return super.onPrepareOptionsMenu(menu);
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
		} else if (item.getItemId() == R.id.action_edit) {
			inEditingMode = true;
			invalidateOptionsMenu();
			updateList();
		} else if (item.getItemId() == R.id.action_delete_pizza) {
			ListView lv = (ListView) findViewById(R.id.pizzaLV);
			for (int i = 0; i < lv.getChildCount(); i++) {
				View v = lv.getChildAt(i);
				CheckBox cb = (CheckBox) v.findViewById(R.id.checkBox1);
				if (cb.isChecked()) {
					pizzas.remove(i);
				}
			}
			updateList();
		} else if (item.getItemId() == R.id.action_editing_done) {
			inEditingMode = false;
			invalidateOptionsMenu();
			updateList();
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
			updateList();
		}
	}
	
	private void updateList() {
		ListView pizzaLV = (ListView) findViewById(R.id.pizzaLV);
		Pizza[] pizzaArray = new Pizza[pizzas.size()];
		pizzaLV.setAdapter(new PizzaArrayAdapter(
				this, R.layout.pizza_list_row, pizzas.toArray(pizzaArray), inEditingMode));
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
		int persons = 0;
		try {
			persons = Integer.parseInt(personsET.getText().toString());
		} catch (NumberFormatException e) {
			builder.setTitle("Fehler");
			builder.setMessage("Unültige PersonenZahl");
		}
		/*
		 * using ojAlgo (http://ojalgo.org/) for linear optimization.
		 * using v33 because this is the latest version targeting Java 5,
		 * later versions require Java 7 which is not supported by Android.
		 * Also modified org.ojalgo.optimisation.integer.IntegerSolver because
		 * it used native-Java calls not supported by Dalvik VM
		 */
		//each variable represents the amount of the respective pizza
		final Variable pizzaVars[] = new Variable[pizzas.size()];
		for (int i = 0; i < pizzas.size(); i++) {
			pizzaVars[i] = Variable.make("Pizza" + i)	//name
					.integer(true)						//we dont want half pizzas
					.lower(new BigDecimal(0))			//no negative amounts
					.weight(new BigDecimal(pizzas.get(i).getPrize()));	//prize
		}
		//creating a model that includes the above variables
		final ExpressionsBasedModel model = new ExpressionsBasedModel(pizzaVars);
		//assigning size to each variable
		final Expression area = model.addExpression("area")
				.lower(new BigDecimal(persons * Pizza.STANDARD_SIZE));
				//thats how much it needs (at least) to feed all persons
		for (int i = 0; i < pizzas.size(); i++) {
			area.setLinearFactor(pizzaVars[i], pizzas.get(i).getArea());
		}
		//the actual minimizing
		GenericSolver solver = model.getDefaultSolver();
		solver.solve();
		
		String message = "";	//message to be displayed in dialog
		double overallCost = 0;
		
		for (int i = 0; i < pizzas.size(); i++) {
			Variable tempVar = pizzaVars[i];
			if (tempVar.getValue().intValue() == 0) continue;
			message += tempVar.getValue() + "x " + pizzas.get(i).printSize() 
					+ " je " + pizzas.get(i).printPrize() + "\n";
			overallCost += tempVar.getValue().doubleValue() * pizzas.get(i).getPrize();
		}
		DecimalFormat f = new DecimalFormat("#0.00"); 
		message += "Gesamtkosten: " + f.format(overallCost) + "\u20AC" + "\n";
		message += "Pro Person: " + f.format(overallCost / persons) + "\u20AC";
		
		
		builder.setTitle("Ihr Ergebnis");
		builder.setMessage(message);
		builder.create().show();
	}
    
}
