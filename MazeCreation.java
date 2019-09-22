package pathfinderUpdated;

import java.awt.*;
import javax.swing.*;

public class MazeCreation {
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		Maze m = new Maze(150, 100, 100, 50, 10);
		m.generate();
		frame.add(m);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setBackground(Color.white);
		frame.setSize(m.xDim * m.blockSize+20, m.yDim * m.blockSize+45);
	}
}
