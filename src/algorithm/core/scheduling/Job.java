package algorithm.core.scheduling;

import java.util.ArrayList;
import java.util.List;

public class Job {
	private int id;
	private List<Operation> operationList;
	
	public Job() {
		operationList = new ArrayList<Operation>();
	}
	
	public void addOperation(Operation operation) {
		this.operationList.add(operation);
	}
	public Operation getOperation(int operationId) {
		return this.operationList.get(operationId);
	}
}
