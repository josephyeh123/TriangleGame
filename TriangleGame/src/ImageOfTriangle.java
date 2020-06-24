import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JRadioButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.sun.net.httpserver.Authenticator.Success;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JSeparator;
import java.awt.Color;

public class ImageOfTriangle extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private ArrayList<String> movesInStrings;
	private ArrayList<Move> diffSteps;
	private Board game;
	private int bestCase;
	int tracker;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					ImageOfTriangle window = new ImageOfTriangle();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ImageOfTriangle() {
		initialize();
		JPanel n = new JPanel();
	}
	
    public void paint(Graphics g) {
        super.paint(g);  // fixes the immediate problem.
        Graphics2D g2 = (Graphics2D) g;
        Line2D lin = new Line2D.Float(100, 100, 250, 260);
        g2.draw(lin);
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1040, 580);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		movesInStrings = new ArrayList<>();
		diffSteps = new ArrayList<>();
		JRadioButton[][] havePin = new JRadioButton[5][5];
		int firstPinInRow = 297;
		int xSpaceApart = 98;
		int ySpaceApart = 88;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < i + 1; j++) {
				char letter = (char) ('a' + i);
				String name = "" + letter + (j + 1); 
				JRadioButton buttonToAdd = new JRadioButton(name);
				buttonToAdd.setBounds(firstPinInRow + j * xSpaceApart, ySpaceApart * (i + 1), 50,50);
				havePin[i][j] = buttonToAdd;
				frame.getContentPane().add(buttonToAdd);
			}
			firstPinInRow = firstPinInRow - 55;
		}

		
		JLabel currentMove = new JLabel("");
		currentMove.setFont(new Font("Tahoma", Font.PLAIN, 16));
		currentMove.setHorizontalAlignment(SwingConstants.CENTER);
		currentMove.setBounds(555, 281, 290, 129);
		frame.getContentPane().add(currentMove);
		
		JButton nextStep = new JButton("Next Step");
		nextStep.setBounds(725, 155, 123, 33);
		frame.getContentPane().add(nextStep);
		
		JLabel bestSolution = new JLabel("");
		bestSolution.setBounds(611, 74, 300, 70);
		frame.getContentPane().add(bestSolution);
		
		
		// Perform the next button action
		nextStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tracker + 1 < movesInStrings.size()) {
					currentMove.setText("next move is " + movesInStrings.get(tracker + 1));
					setCurrentBoard(diffSteps.get(tracker), havePin, true);
					tracker++;
				}
				else {
					setCurrentBoard(diffSteps.get(tracker), havePin, true);
					currentMove.setText("Done!");
				}
			}
		});
		
		JButton prevStep = new JButton("Previous Step");
		prevStep.setBounds(555, 155, 130, 33);
		frame.getContentPane().add(prevStep);
		
		// previous button action
		prevStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tracker > 0) {
					tracker--;
					currentMove.setText(movesInStrings.get(tracker));
					setCurrentBoard(diffSteps.get(tracker), havePin, false);
				}
				else {
					currentMove.setText("Not Possible");
				}
			}
		});
		
		JButton solve = new JButton("Solve!");
		solve.setBounds(661, 40, 89, 23);
		frame.getContentPane().add(solve);	
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.BLACK);
		separator.setForeground(Color.BLACK);
		separator.setBounds(154, 488, 290, -27);
		frame.getContentPane().add(separator);
		
		// Performs the solve button action
		solve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Calculating");
				game = new Board(havePin);
				tracker = 0;
				Stopwatch sp = new Stopwatch();
				sp.start();
				bestCase = game.findSolution();
				sp.stop();
				System.out.println(sp.time());
				System.out.println(game.toString());
				solveAction(bestSolution);
				if(movesInStrings.size() > 0) {
					currentMove.setText("first move is " + movesInStrings.get(tracker));
				}
				else {
					currentMove.setText("No pins on the board");
				}
			}
		});
	}
	
	private void setCurrentBoard(Move curMove, JRadioButton[][] havePin, boolean isNext) {
		if(isNext) {
			havePin[curMove.getCurrentRow()][curMove.getCurrentCol()].setSelected(false);
			havePin[curMove.getNewRow()][curMove.getNewCol()].setSelected(true);
			havePin[curMove.getRemoveRow()][curMove.getRemoveCol()].setSelected(false);
		}
		else {
			havePin[curMove.getCurrentRow()][curMove.getCurrentCol()].setSelected(true);
			havePin[curMove.getNewRow()][curMove.getNewCol()].setSelected(false);
			havePin[curMove.getRemoveRow()][curMove.getRemoveCol()].setSelected(true);
		}
	}
	
	private void solveAction(JLabel bestSolution) {
		bestSolution.setText("The Best Solution have " + bestCase +  " pins remaining");
		movesInStrings = game.translateMovesToStrings();
		diffSteps = game.getDiffSteps();
	}
	
	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
