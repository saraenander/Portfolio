package Projekt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.Font;

public class SolverWindow {
	SudokuSolver solver;

	public static final Color VERY_LIGHT_BLUE = new Color(51, 204, 255);
	public static final Color RED = new Color(201, 104, 55);
	private ArrayList<JTextField> fieldList = new ArrayList<JTextField>();
	private ArrayList<Integer> rowList = new ArrayList<Integer>();
	private ArrayList<Integer> colList = new ArrayList<Integer>();

	public SolverWindow(SudokuSolver solver) {
		this.solver = solver;
		SwingUtilities.invokeLater(() -> createWindow(solver, "Sudoku solver", 100, 300));
	}

	private void createWindow(SudokuSolver solver, String title, int width, int height) {
		Container pane;
		int c;
		int r;
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane = frame.getContentPane(); // Pane är en behållarkomponent till vilken de övriga komponenterna (listvy,
		GridLayout gl = new GridLayout(9, 9); // 9 rows, 9 columns
		// knappar etc) ska läggas till

		frame.pack();
		frame.setVisible(true);
		frame.setSize(500, 600);
		frame.setMinimumSize(new Dimension(500, 600));
		frame.setMaximumSize(new Dimension(500, 600));
		JButton solve = new JButton("Lös sudoku");
		JButton clear = new JButton("Rensa");
		JPanel panel1 = new JPanel();
		JPanel buttonPanel = new JPanel();
		c = 0;
		r = 0;
		for (int i = 0; i < 81; i++) {
			JTextField j = new JTextField(40);
			j.setColumns(1);
			// set font for JTextField
			Font font = new Font("Courier", Font.PLAIN, 30);
			j.setFont(font);
			j.setHorizontalAlignment(JTextField.CENTER);

			if (c == 9) {
				c = 0;
				r++;
			}
			if ((c < 3 || c > 5 && c < 9) && (r < 3 || r > 5)) {
				j.setBackground(VERY_LIGHT_BLUE);
			}

			if ((r > 2 && r < 6) && (c > 2 && c < 6)) {
				j.setBackground(VERY_LIGHT_BLUE);
			}
			fieldList.add(j);
			panel1.add(j);
			rowList.add(r);
			colList.add(c);
			c++;
		}

		buttonPanel.add(solve);
		buttonPanel.add(clear);
		panel1.setLayout(gl);
		pane.setLayout(new BorderLayout());
		pane.add(panel1);
		pane.add(buttonPanel, BorderLayout.SOUTH);

		clear.addActionListener(ActionPerformed -> {
			clear();
		});

		solve.addActionListener(ActionPerformed -> {	
			int value=0;
			for (int i = 0; i < 81; i++) {
				int row = rowList.get(i);
				int col = colList.get(i);
				JTextField ruta = fieldList.get(i);
				String text = ruta.getText();
				if (text.equals("")) {
					value = 0;
					solver.setValue(row, col, value);
				} else {
					for (char tecken : text.toCharArray()) {
						if (Character.isDigit(tecken) == false || tecken == 0 && tecken != ' '
								|| Integer.parseInt(text) > 9 || Integer.parseInt(text) < 1) {
							JOptionPane.showMessageDialog(frame,
									"Endast siffror mellan 1 och 9 är tillåtna. \n Tryck på OK för att försöka igen.",
									"Error", JOptionPane.PLAIN_MESSAGE, null);
							clearSolver();
							return;
						} else {
							value = Integer.parseInt(text);
							if (solver.ok(row, col, value) == false) {
								JOptionPane.showMessageDialog(frame, "Olösbart! Tryck OK för att försöka igen.",
										"Error", JOptionPane.PLAIN_MESSAGE, null);
								clearSolver();
								return;
							} else {
								solver.setValue(row, col, value);
							}
						}
					}
				}
			}
				if (solver.solve()) {
					for (int i = 0; i < 81; i++) {
						fieldList.get(i).setText(Integer.toString(solver.getValue(rowList.get(i), colList.get(i))));
					}
					for (int i = 0; i < 81; i++) {
						solver.setValue(rowList.get(i), colList.get(i), 0);
					}
				}

				else {
					JOptionPane.showMessageDialog(frame, "Olösbart! Tryck OK för att försöka igen.", "Error",
							JOptionPane.PLAIN_MESSAGE, null);
					clearSolver();
				}
		});
	}
	
	public void clearSolver() {
		for (int i = 0; i < 81; i++) {
			solver.setValue(rowList.get(i), colList.get(i), 0);
		}
	}
	
	
	public void clear() {
		for (JTextField field : fieldList) {
			field.setText("");
			clearSolver();
		}
	}

}
