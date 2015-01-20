package algorithm.core.scheduling;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
	private double makespan;
	
	List<Operation> operationList;
	List<Integer> jobList;
	List<Integer> numOfResourcesList;
	
	
	public Schedule() {
		operationList = new ArrayList<Operation>();
		jobList = new ArrayList<Integer>();
		numOfResourcesList = new ArrayList<Integer>();
	}
	
	public void addOperation(Operation operation) {
		operationList.add(operation);
	}
	
	public void addOperation(Operation operation, int jobId) {
		operationList.add(operation);
		jobList.add(jobId);
	}
	
	public int getSize() {
		return operationList.size();
	}
	
	public Operation getOperation(int id) {
		return operationList.get(id);
	}
	
	public int getJobID(int id) {
		return jobList.get(id);
	}
	
	public void autoProcess() {
		for(int j=0; j<operationList.get(0).getResourceSize(); j++) {
			// Max Number of Resource
			int maxNumOfResource = 0;
			for(int i=0; i<operationList.size(); i++) {
				if(operationList.get(i).getRessourceAttributeID(j)>maxNumOfResource) {
					maxNumOfResource = operationList.get(i).getRessourceAttributeID(j);
				}
			}
			maxNumOfResource++;
			numOfResourcesList.add(maxNumOfResource);
			System.out.println("Max # of Resource: "+maxNumOfResource);
		}
	}	
	
	public void setMakespan(double makespan) {
		this.makespan = makespan;
	}
	public double getMakespan() {
		return this.makespan;
	}
	
}
