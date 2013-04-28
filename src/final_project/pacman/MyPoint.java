package final_project.pacman;

public class MyPoint {

	int x;
	int y;
	MyPoint parent;
	
	public MyPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	public MyPoint(int x, int y, MyPoint parent) {
		this.x = x;
		this.y = y;
		this.parent = parent;
	}
	public MyPoint(MyPoint p) {
		this.x = p.x;
		this.y = p.y;
		this.parent = p.parent;
	}

	public void setParent(MyPoint parent) {
		this.parent = parent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyPoint other = (MyPoint) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
	
}
