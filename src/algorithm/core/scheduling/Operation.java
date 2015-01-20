package algorithm.core.scheduling;

import java.util.ArrayList;
import java.util.List;

public class Operation {
	private int id;
	
	private double startingTime;
	private double endingTime;
	private double processingTime;

	private List<Integer> importOperationIdList;
	private List<Integer> exportOperationIdList;
	private List<Resource> resourceList;
	
	public Operation() {
		importOperationIdList = new ArrayList();
		exportOperationIdList = new ArrayList();
		resourceList = new ArrayList<Resource>();
	}
	
	public void setID(int id) {
		this.id=id;
	}
	public int getID() {
		return this.id;
	}
	
	public void setStartingTime(double startingTime) {
		this.startingTime = startingTime;
	}
	public double getStartingTime() {
		return this.startingTime;
	}
	
	public void setEndingTime(double endingTime) {
		this.endingTime = endingTime;
	}
	public double getEndingTime() {
		return this.endingTime;
	}
	
	public void setProcessingTime(double processingTime) {
		this.processingTime = processingTime;
	}
	public double getProcessingTime() {
		return this.processingTime;
	}
	
	//ToDo  importOperationID, exportOperationID, resource
	public void addResource(Resource resource) {
		resourceList.add(resource);
	}
	public Resource getResource(int resourcId) {
		return resourceList.get(resourcId);
	}
	public int getResourceSize() {
		return resourceList.size();
	}
	
	public void addNewRessource(String name) {
		int tempId = resourceList.size();
		Resource re = new Resource();
		re.setName(name);
		re.setID(tempId);
		resourceList.add(re);
	}
	
	public void setRessourceAttributeID(int resourceId, int attributeId) {
		resourceList.get(resourceId).setAttributeID(attributeId);
	}
	public int getRessourceAttributeID(int resourceId) {
		return resourceList.get(resourceId).getAttributeID();
	}
	
}
