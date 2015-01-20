package algorithm.core.component;

public abstract class Gene {
	Object value;
	public int getInt() {
		return Integer.parseInt(this.value.toString());
	}
	public double getDouble() {
		return Double.parseDouble(this.value.toString());
	}
	public abstract String getString();
	public void set(Object value) {
		this.value = value;
	}
	@Override
	public abstract Gene clone();
}
