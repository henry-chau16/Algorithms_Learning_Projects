//Project class that stores profit and deadline values
public class Project {

	private int id;
	private int profit;
	private int deadline;
	
	public Project(int id, int profit, int deadline) {
		this.id=id;
		this.profit=profit;
		this.deadline=deadline;
	}
	
	//ACCESSORS
	
	public int getID() {
		return id;
	}
	public int getProfit() {
		return profit;
	}
	public int getDeadline() {
		return deadline;
	}
}
