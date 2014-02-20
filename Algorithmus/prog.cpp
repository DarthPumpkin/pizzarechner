#include <iostream>
#include <vector>

using namespace std;

vector<Pizza> getOrder(vector<Pizza> possiblePizza, int persons)
{
	/*
	 * Sorting possiblePizza using price per square cm, lowest index is cheapest pizza
	 * Using bubblesort
	 */
	Pizza swap;
	for ( int i = 0; i < possiblePizza.size(); i++ ) {
		for ( int j = 1, j < possiblePizza.size(); i++ ) {
			if ( possiblePizza[j-1].getPrizePerSquareCm() > possiblePizza[j].getPrizePerSquareCm() ) {
				swap = possiblePizza[j-1];
				possiblePizza[j-1] = possiblePizza[j];
				possiblePizza[j] = swap;
			}
		}
	}
	vector<Pizza> order;
	double reachedSize = 0;
	/*
 	 * Calculating the amount of size the pizzas to order must have, inclunding a tolerance of 1/16 
	 * so that not another big pizza is ordered if only one little piece of pizza is missing
	 *
	 * The tolerance is ignored when it comes to choose the pizza to add to order
	 */
	double minSize = STANDARD_SIZE*persons ;
	double tolerance = ( STANDARD_SIZE*persons )*(1/16);
		
	while ( (minSize-tolerance) > reachedSize ) {
		/*
		 * If 0 pizzas ordered or difference between reached size and size to reach 
		 * bigger than size of cheapest pizza, add cheapest pizza to order
		 */
		if ( reachedSize == 0 || (minSize-reachedSize ) > (possiblePizza[0].getArea() ) ) {
			order.push_back( possiblePizza[0] );
			reachedSize += possiblePizza[0].getArea();
		}
		else {
			/*
			 * Find smaller and cheaper pizza to fulfill rest of needed area
			 */
			double cheapPrize = possiblePizza[0].getPrize();
			double cheapSize = possiblePizza[0].getArea();
			Pizza newPizza = 0;
			for ( int i = 1; i < possiblePizza.size(); ++i ) {
				if ( possiblePizza[i].getPrize() < cheapPrize ) {	//Pizza is cheaper
					if ( possiblePizza[i].getArea() < cheapSize ) {		//Pizza is smaller
						if ( possiblePizza[i].getArea() >= minSize-reachedSize ) {	//Pizza is big enough for needed area
							newPizza = possiblePizza[i];
							cheapPrize = possiblePizza[i].getPrize();
							cheapSize = possiblePizza[i].getArea();
						}
					}
				}
			}
			if (newPizza != 0 ) {
				order.push_back( newPizza );
				reachedSize += newPizza.getArea();
			}
			else {
				order.push_back( possiblePizza[0] );
				reachedSize += possiblePizza[0].getArea();
			}
		}
	}
	return order;
}

