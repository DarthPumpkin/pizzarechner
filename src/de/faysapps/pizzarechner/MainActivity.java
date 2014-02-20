package de.faysapps.pizzarechner;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.ojalgo.optimisation.Expression;
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Optimisation;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;

public class MainActivity extends Activity implements OnClickListener {
	
	private TableLayout table;
	private Button button;
	private EditText personsET;
	private ArrayList<Pizza> pizzas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        pizzas = new ArrayList<Pizza>();
        button = (Button) findViewById(R.id.calculateButton);
        personsET = (EditText) findViewById(R.id.personsET);
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
		} else if (item.getItemId() == R.id.action_edit) {
			//do edit stuff
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
//			ListView pizzaLV = (ListView) findViewById(R.id.pizzaLV);
//			pizzaLV.setAdapter(new PizzaArrayAdapter(
//					this, R.layout.pizza_list_row, (Pizza[]) pizzas.toArray()));
			
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
		try {
			int persons = Integer.parseInt(personsET.getText().toString());
			
			/*
			 * using ojAlgo (http://ojalgo.org/) for linear optimization
			 */
			//each variable represents the amount of the respective pizza
			final Variable pizzaVars[] = new Variable[pizzas.size()];
			for (int i = 0; i < pizzas.size(); i++) {
				pizzaVars[i] = Variable.make("Pizza" + i)	//name
						.integer(true)						//we dont want half pizzas
						.lower(new BigDecimal(0))			//no negative amounts
						.weight(new BigDecimal(pizzas.get(i).getPrize()));	//prize
			}
			
			//creating a model that includes the obove variables
			final ExpressionsBasedModel model = new ExpressionsBasedModel();
			for (Variable pizzaVar : pizzaVars) {
				model.addVariable(pizzaVar);
			}
			
			//assigning size to each variable
			final Expression area = model.addExpression("area")
					.lower(new BigDecimal(persons * Pizza.STANDARD_SIZE));
					//thats how much it needs (at least) to feed all persons
			for (int i = 0; i < pizzas.size(); i++) {
				area.setLinearFactor(pizzaVars[i], pizzas.get(i).getArea());
			}

			//the actual minimizing
			model.minimise();
			
			builder.setTitle("Ihr Ergebnis");
			builder.setMessage(model.toString());
		} catch (NumberFormatException e) {
			builder.setTitle("Fehler");
			builder.setMessage("Unültige PersonenZahl");
		}
		builder.create().show();
	}
    
}
