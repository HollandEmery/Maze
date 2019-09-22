package pathfinderUpdated;

import java.util.*;
import java.util.List;

import javax.swing.JComponent;

import java.awt.*;
import java.sql.Time;

public class Maze extends JComponent {
	int xDim, yDim;
	Cell[][] maze;
	int yFin, xFin;
	int startY, startX;
	int blockSize;
	ArrayList<Cell> arr;
	
	public Maze(int x, int y, int fX, int sX, int block) {
		xDim = x;
		yDim = y;
		startY = 0;
		yFin = yDim - 1;
		if(fX < xDim && fX >=0)
			xFin = fX;
		else
			xFin = xDim - 1;
		
		if(sX >=0 && sX < xDim) {
			startX = sX;
		}else {
			startX = 0;
		}
		
		blockSize = block;
	}

	public void paint(Graphics g) {
		this.drawMaze(g);
		this.drawPath(g, arr);
	}

	public void generate() {
		maze = new Cell[xDim][yDim];
		int xStart, yStart;
		Random rand = new Random();
		for (int x = 0; x < xDim; x++)
			for (int y = 0; y < yDim; y++)
				maze[x][y] = new Cell(x, y);

		if (xFin < xDim && yFin == yDim-1) 
			maze[xFin][yFin].setFinish();

		xStart = rand.nextInt(xDim);
		yStart = rand.nextInt(yDim);
		chooseDir(xStart, yStart);
		arr = this.pathfind();
		if(arr == null) {
			System.out.println("Path not found");
		}
	}

	public void chooseDir(int x, int y) {
		Random rand = new Random();
		maze[x][y].visit();
		ArrayList<String> a = new ArrayList<String>();
		if ((x + 1 >= xDim || maze[x + 1][y].hasVisited())
				&& (x - 1 < 0 || maze[x - 1][y].hasVisited())
				&& (y + 1 >= yDim || maze[x][y + 1].hasVisited())
				&& (y - 1 < 0 || maze[x][y - 1].hasVisited()))
			return;

		if (x + 1 < xDim && !maze[x + 1][y].hasVisited())
			a.add("right");
		if (x - 1 >= 0 && !maze[x - 1][y].hasVisited())
			a.add("left");
		if (y + 1 < yDim && !maze[x][y + 1].hasVisited())
			a.add("down");
		if (y - 1 >= 0 && !maze[x][y - 1].hasVisited())
			a.add("up");

		int r = rand.nextInt(a.size());

		if (a.get(r).equals("right")) {
			maze[x][y].setRight(false);
			maze[x + 1][y].setLeft(false);
			chooseDir(x + 1, y);
		} else if (a.get(r).equals("left")) {
			maze[x][y].setLeft(false);
			maze[x - 1][y].setRight(false);
			chooseDir(x - 1, y);
		} else if (a.get(r).equals("up")) {
			maze[x][y].setUp(false);
			maze[x][y - 1].setDown(false);
			chooseDir(x, y - 1);
		} else if (a.get(r).equals("down")) {
			maze[x][y].setDown(false);
			maze[x][y + 1].setUp(false);
			chooseDir(x, y + 1);
		}
		chooseDir(x,y);
	}

	public void drawMaze(Graphics g) {
		g.drawRect(1, 1, xDim*blockSize+1, yDim*blockSize+1);
		/*g.drawLine(1, 2, 1, yDim*blockSize+1);
		g.drawLine(2, 1, xDim*blockSize+1, 1);
		g.drawLine(xDim*blockSize+2, 1, xDim*blockSize+2, yDim*blockSize+2);
		g.drawLine(1, yDim*blockSize+2, xDim*blockSize+2, yDim*blockSize+2);*/
		for (int x = 0; x < xDim; x++) {
			for (int y = 0; y < yDim; y++) {
				if (maze[x][y].isUp()) {
					g.drawLine((blockSize * x)+2, (blockSize * y)+2,
							blockSize * (x + 1)+1, (blockSize * y)+2);
				}
				if (maze[x][y].isDown()) {
					g.drawLine((blockSize * x)+2, blockSize * (y + 1)+1,
							blockSize * (x + 1)+1, blockSize * (y + 1)+1);
				}
				if (maze[x][y].isLeft()) {
					g.drawLine((blockSize * x)+2, (blockSize * y)+2, (blockSize * x)+2,
							blockSize * (y + 1)+1);
				}
				if (maze[x][y].isRight()) {
					g.drawLine(blockSize * (x + 1)+1, (blockSize * y)+2,
							blockSize * (x + 1)+1, blockSize * (y + 1)+1);
				}
				if(maze[x][y].isFinish()) {
					g.setColor(Color.GREEN);
					g.fillRect(blockSize*x+3, blockSize*(y+1)+1, blockSize-2, 2);
					g.setColor(Color.BLACK);
				}
			}
		}
		g.setColor(Color.BLUE);
		g.fillRect(startX*blockSize+3, 1, blockSize-2, 2);
		g.setColor(Color.black);
	}
	
	public void drawPath(Graphics g, ArrayList<Cell> a) {
		if(a == null)
			return;
		g.setColor(Color.blue);
		for(int i = 0; i < a.size(); i++) {
			int x = a.get(i).getX(), y = a.get(i).getY();
			if(i == a.size()-1) {
				g.drawLine(x*blockSize+(blockSize/2)+2, y*blockSize+(blockSize/2)+2, x*blockSize+(blockSize/2)+2, (y+1)*blockSize);
			}
			if(i == 0) {
				g.drawLine(x*blockSize+(blockSize/2)+2, y+2, x*blockSize+(blockSize/2)+2, blockSize/2+2);
			}else if(a.get(i-1).getX() == x - 1) {
				g.drawLine(x*blockSize-(blockSize/2)+2, y*blockSize+(blockSize/2)+2, x*blockSize+(blockSize/2)+2, y*blockSize+(blockSize/2)+2);
			}else if(a.get(i-1).getX() == x + 1) {
				g.drawLine(x*blockSize+(blockSize/2)+2, y*blockSize+(blockSize/2)+2, x*blockSize+(3*blockSize/2)+2, y*blockSize+(blockSize/2)+2);
			}else if(a.get(i-1).getY() == y - 1) {
				g.drawLine(x*blockSize+(blockSize/2)+2, y*blockSize-(blockSize/2)+2, x*blockSize+(blockSize/2)+2, y*blockSize+(blockSize/2)+2);
			}else if(a.get(i-1).getY() == y + 1) {
				g.drawLine(x*blockSize+(blockSize/2)+2, y*blockSize+(blockSize/2)+2, x*blockSize+(blockSize/2)+2, y*blockSize+(3*blockSize/2)+2);
			}
		}
	}
	
	public ArrayList<Cell> pathfind() {
		ArrayList<Cell> queue = new ArrayList<Cell>();
		ArrayList<Cell> closed = new ArrayList<Cell>();
		maze[startX][startY].setWeight(0);
		maze[startX][startY].setTotal((Math.abs(xFin-startX))+Math.abs((yFin-startY)));
		queue.add(maze[startX][startY]);
		int cX, cY;
		while(!queue.get(0).isFinish()) {
			Cell curr = queue.get(0);
			Cell temp;
			queue.remove(0);
			closed.add(curr);
			cX = curr.getX();
			cY = curr.getY();
			if(!curr.isDown()) {
				temp = maze[cX][cY+1];
				if(!closed.contains(temp)) {
					int total = curr.getWeight()+1+(Math.abs(xFin-temp.getX())+Math.abs(yFin-temp.getY()));
					if(queue.contains(temp)) {
						if(temp.getTotal() > total)
							temp.setTotal(total);;
					}
					else {
						temp.setTotal(total);
						temp.setWeight(curr.getWeight()+1);
						queue.add(temp);
					}
				}
			}
			if(!curr.isUp()) {
				temp = maze[cX][cY-1];
				if(!closed.contains(temp)) {
					int total = curr.getWeight()+1+(Math.abs(xFin-temp.getX())+Math.abs(yFin-temp.getY()));
					if(queue.contains(temp)) {
						if(temp.getTotal() > total)
							temp.setTotal(total);;
					}
					else {
						temp.setTotal(total);;
						temp.setWeight(curr.getWeight()+1);
						queue.add(temp);
					}
				}
			}
			if(!curr.isLeft()) {
				temp = maze[cX-1][cY];
				if(!closed.contains(temp)) {
					int total = curr.getWeight()+1+(Math.abs(xFin-temp.getX())+Math.abs(yFin-temp.getY()));
					if(queue.contains(temp)) {
						if(temp.getTotal() > total)
							temp.setTotal(total);;
					}
					else {
						temp.setTotal(total);
						temp.setWeight(curr.getWeight()+1);
						queue.add(temp);
					}
				}
			}
			if(!curr.isRight()) {
				temp = maze[cX+1][cY];
				if(!closed.contains(temp)) {
					int total = curr.getWeight()+1+(Math.abs(xFin-temp.getX())+Math.abs(yFin-temp.getY()));
					if(queue.contains(temp)) {
						if(temp.getTotal() > total)
							temp.setTotal(total);
					}
					else {
						temp.setTotal(total);
						temp.setWeight(curr.getWeight()+1);
						queue.add(temp);
					}
				}
			}
			Collections.sort(queue);
		}
		closed.add(queue.get(0));
		
		ArrayList<Cell> path = new ArrayList<Cell>();
		
		cX = xFin;
		cY = yFin;
		path.add(maze[cX][cY]);
		int value = maze[cX][cY].getWeight();
		while(value != 0) {//value!=0) {
			value = maze[cX][cY].getWeight();
			if(cX-1 >= 0 && !maze[cX][cY].isLeft() && maze[cX-1][cY].getWeight() == value - 1) {
				value--;
				cX--;
				path.add(0,maze[cX][cY]); 
			}else if(cX+1 < xDim && !maze[cX][cY].isRight() && maze[cX+1][cY].getWeight() == value - 1) {
				value--;
				cX++;
				path.add(0,maze[cX][cY]); 
			}else if(cY-1 >= 0 && !maze[cX][cY].isUp() && maze[cX][cY-1].getWeight() == value - 1) {
				value--;
				cY--;
				path.add(0,maze[cX][cY]);
			}else if(cY+1 < yDim && !maze[cX][cY].isDown() && maze[cX][cY+1].getWeight() == value -1) {
				value--;
				cY++;
				path.add(0,maze[cX][cY]);
			}else {
				return null;
			}
		}
		
		return path;
	}
}
