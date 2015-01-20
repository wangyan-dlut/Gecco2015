package experiment.jsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

import algorithm.core.scheduling.*;

public class Jsp {
	private int jobSize;
	private int machineSize;
	private List<Job> jobList;
	
	public Jsp() {
		jobList = new ArrayList<Job>();
	}
	public void setJobSize(int jobSize) {
		this.jobSize = jobSize;
	}
	public int getJobSize() {
		return jobSize;
	}
	public void setMachineSize(int machineSize) {
		this.machineSize = machineSize;
	}
	public int getMachineSize() {
		return machineSize;
	}
	public int getMachineID(int jobId, int operationId) {
		return jobList.get(jobId).getOperation(operationId).getRessourceAttributeID(0);
	}
	public double getProcessingTime(int jobId, int operationId) {
		return jobList.get(jobId).getOperation(operationId).getProcessingTime();
	}
	
	public Job getJob(int id) {
		return jobList.get(id);
	}
	
	public void txtDataImport(String fileName) {
		try {
			BufferedReader re = new BufferedReader(new FileReader(fileName));
		    StreamTokenizer st = new StreamTokenizer(re);
		    st.commentChar('#');
		    st.nextToken();
		    jobSize = (int)st.nval;
		    st.nextToken();
		    machineSize = (int)st.nval;
		    for(int i=0; i<jobSize; i++) {
	    		Job job = new Job();
		    	for(int j=0; j<machineSize; j++) {
		    		Operation operation = new Operation();
		    		st.nextToken();
		    		operation.addNewRessource("machine");
		    		operation.setRessourceAttributeID(0, (int)st.nval);
		    		st.nextToken();
		    		operation.setProcessingTime(st.nval);
		    		operation.setID(j);
		    		job.addOperation(operation);
		    	}
		    	jobList.add(job);
		    }			
		} catch(IOException e) {
			System.out.println("JSP TXT Data File Improt Error");
		}
	}
	
}
