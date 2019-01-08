import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;

import java.net.*;

public class hh extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	private boolean[][] lights;
	private boolean[][] lightsClicked;
	private static boolean showSolution = false;
	int stepCounter = 0;
	static int WINDOW_SIZE = 500;

	public static void main(String[] args) {
		// inialitizing Jframe
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Lights Out!");
		frame.setResizable(false);
		frame.setVisible(true);

		// Initializintg the panel
		hh panel = new hh();

		panel.addMouseListener(panel);
		panel.setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
		panel.setMinimumSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));

		JButton solveButton = new JButton("Solve");
		solveButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    showSolution = true;
		    solveButton.setText("Solution shown");
		    frame.repaint();
		    }
		});

		solveButton.setVisible(true);

		Container c = frame.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(panel, BorderLayout.CENTER);
		c.add(solveButton, BorderLayout.PAGE_START);

		frame.pack();
	}

	// To generate a random number between two values
	int randomRange(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}

	public hh() {
		// initialize the two arrays
		lights = new boolean[5][5];
		lightsClicked = new boolean[5][5];
		// randomly turn on 10 lights

		while (true) {
			int x = randomRange(0, 4);
			int y = randomRange(0, 4);

			if(lightsClicked[x][y] != true) {
				toggle(x, y);
				lightsClicked[x][y] = true;
			}
			// System.out.println((x + 1) + " " + (y + 1) + "\n");

			if (countBools(lights) == 10)
				break;
		}
		// display best answer to the question
		displaySolution();
		System.out.println("It will take atleast: " + countBools(lightsClicked) + " steps to complete");

	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int boxWidth = WINDOW_SIZE / 5;
		int boxHeight = WINDOW_SIZE / 5;

		int y = 0;
		for (int row = 0; row < 5; row++) {
			int x = 0;
			for (int col = 0; col < 5; col++) {
				if (showSolution && lightsClicked[row][col]) {
					g.setColor(Color.PINK);
				} 
				else if (showSolution&&!lightsClicked[row][col]) {
					if (lights[row][col]) 
						g.setColor(Color.WHITE);
					else
						g.setColor(Color.DARK_GRAY);
				}
				else if (lights[row][col]) {
					g.setColor(Color.WHITE);
				} else {
					g.setColor(Color.DARK_GRAY);
				}
				g.fillRect(x, y, boxWidth, boxHeight);

				g.setColor(Color.GREEN);
				g.drawRect(x, y, boxWidth, boxHeight);
				x += boxWidth;
			}
			y += boxHeight;
		}
	}

	// called when the mouse is pressed - determines what row/column the user
	// has clicked

	public void mousePressed(MouseEvent e) {
		// boolean finished = true;
		int mouseX = e.getX();
		int mouseY = e.getY();

		int panelWidth = getWidth();
		int panelHeight = getHeight();

		int boxWidth = panelWidth / lights[0].length;
		int boxHeight = panelHeight / lights.length;

		int col = mouseX / boxWidth;
		int row = mouseY / boxHeight;

		toggle(row, col);
		repaint();
		stepCounter++;
		
		if (0 == countBools(lights)) {
			endGame();
		} else
			System.out.println("You already took: " + stepCounter + " steps");

	}

	public void endGame() {
		// display ending messages
		System.out.println("You Win!");
		System.out.println("You took: " + stepCounter + " steps");
		System.out.print('\n');
		// displaySolution();
		System.exit(0);
	}

	public void displaySolution() {
		for (boolean[] list : lightsClicked) {
			for (boolean light : list) {
				System.out.print((light ? "1" : "0") + " ");
			}
			System.out.print('\n');
		}
	}

	// called to "toggle" the selected row and column, as well as the four
	// adjacent lights
	public void toggle(int row, int col) {

		lights[row][col] = !lights[row][col];
		lightsClicked[row][col] = !lightsClicked[row][col];
		if (row - 1 >= 0) {
			lights[row - 1][col] = !lights[row - 1][col];

		}
		if (row + 1 <= 4) {
			lights[row + 1][col] = !lights[row + 1][col];

		}
		if (col - 1 >= 0) {
			lights[row][col - 1] = !lights[row][col - 1];

		}
		if (col + 1 <= 4) {
			lights[row][col + 1] = !lights[row][col + 1];

		}

	}

	public int countBools(boolean[][] input) {
		int count = 0;
		for (boolean[] list : input) {
			for (boolean item : list) {
				if (item) {
					count++;
				}
			}
		}
		return count;
	}

	public void solve(boolean user) {
		showSolution = true;
		repaint();
	}

}