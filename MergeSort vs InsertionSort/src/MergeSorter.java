public class MergeSorter {
	
	//constructor
	public MergeSorter() {	
	}
	
	//public sort method that calls the recursive merge_sort
	public void sort(int arr[]) {
		
		merge_sort(arr, 0, arr.length-1);
	}
	
	//private recursive merge_sort method that continues to split the array in half (left to right) until sub-arrays are of single elements
	private void merge_sort(int arr[], int a, int b) {
		int midpoint=(a+b)/2;
		if ((b-a)>1) {  // if sub-array has 2 elements, each index,a and b, are their own sub-array with size 1.
			
			merge_sort(arr, a, midpoint); //left side recursive call
			
			merge_sort(arr, midpoint+1, b); //right side recursive call
		}
		
		merge(arr, a, midpoint, b);//merge left and right
		
	}
	
	//private merge method that combines two sub-arrays into a sorted merged sub-array.
	private void merge(int arr[], int a, int mid, int b) {
		
		if (b-a<=1) { // simple case of 2 sub-arrays of size 1 (skips longer unnecessary merge algorithm)
			if (arr[a]>arr[b]) { // only reorders if a element > b element
				int c=arr[a];
				arr[a]=arr[b];
				arr[b]=c;
			}
		}
		else { // merging sub-arrays of size > 1
			int c1=a; // left array cursor
			int c2=mid+1; // right array cursor
			int temp[]=new int[b-a+1];// temporary array to store and transfer merged sub-array elements
			
			for (int i=0; i<temp.length; i++) { //loops through each element of temp to merge all elements in sub-arrays
				if(c1 > mid) {// no more elements in first sub-array
					temp[i]=arr[c2];
					c2++;
				}
				else if (c2 > b) {// no more elements in second sub-array
					temp[i]=arr[c1];
					c1++;
				}
				else {
					if(arr[c1]<=arr[c2]) {// element in first sub-array smaller is added
						temp[i]=arr[c1];
						c1++;
					}
					else {// element in second sub-array is smaller and is added
						temp[i]=arr[c2];
						c2++;
					}
				}
			}
			for (int i=0; i<temp.length; i++) {// transfers elements from the sorted merge back to original array
				arr[a+i]=temp[i];
			}
			
		}
		
	}
}
