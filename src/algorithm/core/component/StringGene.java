package algorithm.core.component;

public class StringGene extends Gene{
	String value;
	public StringGene() {
		value = "";
	}
	public void set(String value) {
		this.value = value;
	}
	@Override
	public String getString() {
		return this.value;
	}

	@Override
	public Gene clone() {
		Gene res = new StringGene();
		res.set(value);
		return res;
	}


}
