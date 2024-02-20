public class PersonSorter {

	// Constants representing sorting mode by the field of class person.
	public static final int BY_LAST_NAME 		= 0;
	public static final int BY_DATE_OF_BIRTH 	= 1;

	public static Person[] sortBy(Person[] A, int byValue) {
		switch (byValue) {
		case BY_LAST_NAME:
			return sortByLastName(A);
		case BY_DATE_OF_BIRTH:
			return sortByDateOfBirth(A);
		default:
			return A;
		}
	}
	

//Insertion Sort---------------------------------------------------------------------------	
	
	//same Insertion Sort algorithm in problem 1 applied to the Person object
	private static Person[] sortByDateOfBirth(Person[] A) {
		Person [] P = A.clone();
		
		for (int i=1; i<P.length; i++) {
            int j=i-1;
            Person check=P[i];
            
            while (j>=0 && P[j].getDateOfBirth().compareTo(check.getDateOfBirth())>0) { // uses compareTo instead of base compare operators
                P[j+1]=P[j];
                j--;
            }
            P[j+1]=check;
        }
		
		return P;
	}
	
//Merge Sort---------------------------------------------------------------------------------	
	
	//same merge algorithm from problem 1 applied to Person object
	private static void merge(Person[] P, int a, int mid, int b) {

		if (b-a<=1) {
			if (P[a].getLastName().compareTo(P[b].getLastName())>0) {
				Person c=P[a];
				P[a]=P[b];
				P[b]=c;
			}
		}
		else {
			int c1=a;
			int c2=mid+1;
			Person temp[]=new Person[b-a+1];
			
			for (int i=0; i<temp.length; i++) {
				if(c1 > mid) {
					temp[i]=P[c2];
					c2++;
				}
				else if (c2 > b) {
					temp[i]=P[c1];
					c1++;
				}
				else {
					if(P[c1].getLastName().compareTo(P[c2].getLastName())<=0) {
						temp[i]=P[c1];
						c1++;
					}
					else {
						temp[i]=P[c2];
						c2++;
					}
				}
			}
			for (int i=0; i<temp.length; i++) {
				P[a+i]=temp[i];
			}
		}	
	}
	
	//recursive merge_sort method that calls that continues to split the array in half until only sub-arrays of size 1
	private static void merge_sort(Person[] P, int a, int b) {
		int midpoint=(a+b)/2;
		if ((b-a)>1) {
			//left
			merge_sort(P, a, midpoint);
			//right
			merge_sort(P, midpoint+1, b);
		}
		//merge left and right
		merge(P, a, midpoint, b);
	}
	
	//Calls recursive merge_sort and returns sorted array
	private static Person[] sortByLastName(Person[] A) {
		Person [] P = A.clone();
		
		merge_sort(P, 0, P.length-1);
		
		return P;
	}
	
	
//Quick Sort--------------------------------------------------------------------------
	
	//compare method that first compares by last name, then by DOB if last names are same. returns int -1 if first
	//element comes before, 1 if comes after, and 0 if same
	private static int compare(Person[] P, int i, int b) {
		if(P[i].getLastName().compareTo(P[b].getLastName())<0) {// i < b (last name)
			return -1;
		}
		else if(P[i].getLastName().compareTo(P[b].getLastName())>0){// i > b (last name)
			return 1;
		}
		else {// i == b (last name)
			if(P[i].getDateOfBirth().compareTo(P[b].getDateOfBirth())<0) {// i < b (DOB)
				return -1;
			}
			else if(P[i].getDateOfBirth().compareTo(P[b].getDateOfBirth())>0) {// i > b (DOB)
				return 1;
			}
			else {// i == b (for both last name and DOB)
				return 0;
			}
		}
	}
	
	//partition method that moves every element smaller than pivot (element b) left of q and everything greater
	// than element b right of q, then sets element b to index q
	private static int partition(Person[] P, int a, int b) {
		int q = a;// q cursor that denotes right bound of the left (less than element b) partition
		int i = a;// cursor to iterate through sub-array
		Person temp;// used for element swapping
		
		while (i < b) {//loops through sub-array up until element b-1
			if (compare(P, i, b)<0) {//if element < b, swap into partition
				if(q==i) {//simple case where both cursors are the same, no swap is needed, just index incrementing
					i++;
					q++;
				}
				else {//swap element at i into partition at q, increment both q and i
					temp=P[i];
					P[i]=P[q];
					P[q]=temp;
					i++;
					q++;
				}
			}
			else {//element at i is > b so nothing is done
				i++;
			}
		}
		
		//set pivot element at index q
		temp=P[b];
		P[b]=P[q];
		P[q]=temp;
		
		return q;// returns index of pivot (q)
	}
	
	//recursive quicksort method that partitions array and then calls itself to partition each left and right sub-array
	private static void quick_sort(Person[] P, int a, int b) {
		if (a<b) {
			int q=partition(P, a, b);

			quick_sort(P, a, q-1);//left sub-array
			quick_sort(P, q+1, b);//right sub-array
			
		}
	}
	
	//calls recursive quicksort and returns sorted array P
	public static Person[] sortByLastNameAndDateOfBirth(Person[] A) {
		Person [] P = A.clone();
		
		quick_sort(P, 0, P.length-1);
		
		return P;
	}

}
