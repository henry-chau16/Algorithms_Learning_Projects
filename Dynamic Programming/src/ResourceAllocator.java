import java.util.*;

public class ResourceAllocator {
	
	public ResourceAllocator() {
	}
	
	public static int maxProfit(List<Project> proj, int m) {
		
		//sort proj list by deadline (ascending order) using counting sort for linear
		//avg time complexity
		Project[] sorted=countingSortList(proj);
		
		//create table with projects as rows and work slots (deadlines*# of workers m)
		//size of table is (final deadline*m + 1) x n
		int k=(sorted[sorted.length-1].getDeadline()*m)+1;
		
		int [][] table=new int[sorted.length][k];//table to memoize optimal project selection and sums
		int [] spots=new int[k];//keeps track of deadlines for related table decisions
		int count = 1;
		int deadline = 1;
		//accounts for m worker slots each deadline
		for(int j=1; j<k; j++) {
			if(count<m) {
				spots[j]=deadline;
				count++;
			}
			else {
				spots[j]=deadline;
				count=1;
				deadline++;
			}
		}
		
		//populate table right to left, top to bottom, saving the sum for each line in 
		//index 0 of each row
		
		//if proj value is within deadline, and is greater than same index in row above,
		//populate with value, and continue down the row to the left with value from 
		//prev row
		
		int i=0;
		int compare;
		int sum;
		int current;
		
		while(i<sorted.length) {
			int j=k-1;
			current=sorted[i].getProfit();
			sum=0;
			while(j>=0) {
				if(j==0) {
					table[i][j]=sum; //summing each row
				}
				else {
					if(sorted[i].getDeadline()<spots[j]) {
					table[i][j]=0;
					}
					else {
						if(i==0) {
							compare=0;
						}
						else {
							compare=table[i-1][j];
						}
					
						if(current>compare) { // if value is higher than prev row
											  // fill table element with value and continue
											  // down left in row with value "replaced" 
											  // from prev row
							table[i][j]=current;
							sum+=current;
							current=compare;
						}
						else {
							sum+=compare;
							table[i][j]=compare;
						}
					}
				}
				j--;
			}
			i++;
		}
		for(int a=0; a<sorted.length; a++) {// print table
			System.out.println(Arrays.toString(table[a]));
		}
		//after all the table elements are populated, last row index 0 is the max profit
		return table[sorted.length-1][0];
	}
	
	//uses counting sort to sort list of projects by deadline (ascending order)
	//returns a sorted array of projects
	public static Project[] countingSortList(List<Project> a) {
		Project[] b = new Project[a.size()];
		
		int k=0;
		
		for(int i=0; i<a.size(); i++) {
			
			int m = a.get(i).getDeadline();
			if(m>k) {
				k=m;
			}
		}
		
		int[] c = new int[k+1];
		
		for(int i=0; i<k+1; i++) {
			c[i]=0;
		}
		
		for(int i=0; i<a.size(); i++) {
			c[a.get(i).getDeadline()]++;
		}
		for(int i=1; i<k+1; i++) {
			c[i]=c[i]+c[i-1];
		}
		for(int i=a.size()-1; i>=0; i--) {
			b[c[a.get(i).getDeadline()]-1]=a.get(i);
			c[a.get(i).getDeadline()]--;
		}
		
		return b;
	}
}
