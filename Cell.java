package pathfinderUpdated;

public class Cell implements Comparable{
	private boolean up, down, right, left, visit, finish;
	private int weight, total, x, y;

	public Cell(int x, int y) {
		up = true;
		down = true;
		left = true;
		right = true;
		visit = false;
		finish = false;
		weight = 0;
		total = 0;
		this.x = x;
		this.y = y;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void visit() {
		visit = true;
	}

	public boolean hasVisited() {
		return visit;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish() {
		finish = true;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return y;
	}
	@Override
	public int compareTo(Object o) {
		int a = ((Cell) o).getTotal();
		if(this.total == a)
			return (this.total-this.weight)-(((Cell)o).getTotal()-((Cell)o).getWeight());
		return this.getTotal()-a;
	}

}
