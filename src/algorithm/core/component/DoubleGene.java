package algorithm.core.component;

public class DoubleGene extends Gene{
	double value;
	public DoubleGene() {
		value = 0;
	}
	public void set(double value) {
		this.value = value;
	}
	@Override
	public double getDouble() {
		return this.value;
	}
	@Override
	public Gene clone(){
		DoubleGene res = new DoubleGene();
		res.set(value);
		return res;
	}
	@Override
	public String getString(){
		return ""+value;
	}
}
