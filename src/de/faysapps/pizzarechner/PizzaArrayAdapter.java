package de.faysapps.pizzarechner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This class is used to customize the rows of the ListView in MainActivity. The
 * respective row view is created in getView (overridden)
 *
 */
public class PizzaArrayAdapter extends ArrayAdapter<Pizza> {
	private final Context context;
	private final Pizza[] pizzas;
	private final boolean inEditingMode;

	public PizzaArrayAdapter(Context context, int resource, Pizza[] objects, boolean inEditingMode) {
		super(context, resource, objects);
		this.context = context;
		this.pizzas = objects;
		this.inEditingMode = inEditingMode;
		// TODO Auto-generated constructor stub
	}

	@Override
	//creates and returns the row at position (first parameter)
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//inflate view from layout resource pizza_list_row.xml
		View view = inflater.inflate(R.layout.pizza_list_row, parent, false);
		//finding the views inside the list row by caling findViewById on the listRow
		ImageView pizzaFormIV = (ImageView) view.findViewById(R.id.pizzaFormIV);
		TextView pizzaSizeTV = (TextView) view.findViewById(R.id.pizzaSizeTV);
		TextView pizzaCostTV = (TextView) view.findViewById(R.id.pizzaCostTV);
		CheckBox cB = (CheckBox) view.findViewById(R.id.checkBox1);
		
		Pizza currentPizza = pizzas[position];
		
		/*
		 * filling the view with pizza vaules now
		 */
		if (currentPizza.getDiameter() == 0) {
			pizzaFormIV.setImageResource(R.drawable.pizza_rect);
		}
		pizzaSizeTV.setText(currentPizza.printSize());
		pizzaCostTV.setText(currentPizza.printPrize());
		cB.setVisibility((inEditingMode) ? View.VISIBLE : View.GONE);
		
		return view;
	}

}
