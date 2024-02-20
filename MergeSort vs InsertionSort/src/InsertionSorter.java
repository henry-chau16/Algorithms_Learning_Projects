public class InsertionSorter {

	//constructor
	public InsertionSorter() {
	}
	
	//public sort method that uses insertion sort to sort input array
	public void sort(int arr[], int a, int b) {
        for (int i=a+1; i<b; i++) { //loops through array, j = left comparison element, i = cursor
            int j=i-1;
            int check=arr[i]; // stores element i for inner comparison loop
            
            while (j>=0 && arr[j]>check) { // swap j if its bigger than check and continue moving left and repeating if element is bigger than check
                arr[j+1]=arr[j];
                j--;
            }
            arr[j+1]=check;
        }
	}
}
