package algorithm.SLPSO;

public class edge {
	edge() {
		start=0;
		end=0;
		pe=1;
		statue=true;
	}
public 
	int start; 
	int end;
	double pe;//p(e)
	boolean statue;//if p<0.001 statue=false
	void copy(edge other)
	{
		this.start=other.start;
		this.end=other.end;
		this.pe=other.pe;
		this.statue=other.statue;
	}
	boolean equals(edge other)
	{
		if(this.start==other.start && this.end==other.end)
			return true;
		else if(this.end==other.start && this.start==other.end)
			return true;
		return false;
	}
}


