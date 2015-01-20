package algorithm.core.scheduling;

public class Resource {
	private int id;
	private String name;
	
	//Resource Attribute
	private String attribute;
	private int attributeID;
	
	//Resource Constraint
	private int maxNumbers;
	private int minNumbers;
	
	public void setID(int id) {
		this.id=id;
	}
	public int getID() {
		return this.id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getAttribute() {
		return this.attribute;
	}
	
	public void setAttributeID(int attributeID) {
		this.attributeID = attributeID;
	}
	public int getAttributeID() {
		return this.attributeID;
	}
	
	public void setMaxNumbers(int maxNumbers) {
		this.maxNumbers = maxNumbers;
	}
	public int getMaxNumbers() {
		return this.maxNumbers;
	}

	public void setMinNumbers(int minNumbers) {
		this.minNumbers = minNumbers;
	}
	public int getMinNumbers() {
		return this.minNumbers;
	}
}
