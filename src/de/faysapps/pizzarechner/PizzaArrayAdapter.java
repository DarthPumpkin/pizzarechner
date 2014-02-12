package de.faysapps.pizzarechner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * This class is used to customize the rows of the ListView in MainActicity. The
 * respective row view is created in getView (overridden)
 *
 */
public class PizzaArrayAdapter extends ArrayAdapter<Pizza> {
	private final Context context;
	private final Pizza[] pizzas;

	public PizzaArrayAdapter(Context context, int resource, Pizza[] objects) {
		super(context, resource, objects);
		this.context = context;
		this.pizzas = objects;
		// TODO Auto-generated constructor stub
	}

	@Override
	//creates and returns the row at position (first parameter)
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//inflate view from layout resource pizza_list_row.xml
		View view = inflater.inflate(R.layout.pizza_list_row, parent, false);
		
		Pizza pizza = pizzas[position];
		//fill the view here ...
		//get the textViews and/or image views contained in each row with findViewById(...)
		//and fill them with pizza values
		
		return view;
	}

}
