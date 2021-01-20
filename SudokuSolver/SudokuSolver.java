package Projekt;

public class SudokuSolver {
	int[][] m;



	/**
	 * Creates a sudoku solver object with a matrix of integers m
	 * 
	 */
	public SudokuSolver() {
		m=new int[9][9];

	}

	/**
	 * Sets val val at the specified row r and col c
	 * 
	 * @param r
	 *            row
	 * @param c
	 *            column
	 * @param val
	 *            value
	 */
	public void setValue(int r, int c, int val) {
		if (val > 9) {
			return;
		} else {
			m[r][c] = val;
		}
	}

	/**
	 * Gets and returns value at the specified row r and col c.
	 * 
	 * @param r
	 *            row
	 * @param c
	 *            column
	 * @return value at row r and column c
	 * 
	 */
	public int getValue(int r, int c) {
		return m[r][c];
	}

	/**
	 * Checks column c for value val. Returns true if it does not exist else returns
	 * false.
	 * 
	 * @param c
	 *            column
	 *@param val 	
	 *value            
	 * 
	 * @return true if the value does not exist in column
	 * 
	 */
	public boolean checkCol(int c, int val) {
		for (int i = 0; i < 9; i++) {
			if (m[i][c] == val) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks row r for value val. Returns true if it does not exist else returns
	 * false.
	 * 
	 * @param r
	 *            row
	 * @param val
	 * value
	 * 
	 * @return true if the val does not exist in row
	 * 
	 */
	public boolean checkRow(int r, int val) {
		for (int i = 0; i < 9; i++) {
			if (m[r][i] == val) {
				return false;
			}
		}
		return true;

	}

	/**
	 * Checks the 3x3 square that contains row r and column c for value val. Returns
	 * true if it does not exist, else returns false.
	 * 
	 * @param r
	 *            row
	 *        @param c 
	 *        column    
	 *        
	 * @param val
	 *            value
	 * 
	 * @return true if the val does not exist in square
	 * 
	 */
	public boolean checkSq(int r, int c, int val) {
		int col = c - c % 3;
		int row = r - r % 3;
		for (int i = row; i < row + 3; i++) {
			for (int j = col; j < col + 3; j++) {
				if (getValue(i, j) == val) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if value val exists on row r, column c or in the 3x3 square that
	 * contains row r and column c. Returns true if it does not exist, else returns
	 * false.
	 * 
	 * @param r
	 *            row
	 * @param c
	 *            column
	 * @param val
	 *            value
	 * 
	 * @return true if the val does not exist in row r, column c or in the 3x3
	 *         square that contains the row r and column c
	 * 
	 */
	public boolean ok(int r, int c, int val) {
		if (checkRow(r, val) && checkCol(c, val) && checkSq(r, c, val)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Solves a sudoku with backtracking. Returns true if the sudoku is solvable, else returns false. 
	 * 
	 * 
	 * @return true if sudoku is solvable.
	 * 
	 */
	public boolean solve() {
		return solve(0,0);
	}
	private boolean solve(int r, int c) {

		if (getValue(r, c) == 0) {
			for (int val = 1; val <= 9; val++) {
				if (ok(r, c, val)) {
					setValue(r, c, val);

					int ctemp = c + 1;
					int rtemp = r;
					if (ctemp == 9) {
						ctemp = 0;
						rtemp++;
					}
					if (rtemp == 9) {
						return true;
					}

					if (solve(rtemp, ctemp)) {
						return true;
					}
				}
			}
			setValue(r, c, 0);
			return false;

		} else {
			int ctemp = c + 1;
			int rtemp = r;
			if (ctemp == 9) {
				ctemp = 0;
				rtemp++;
			}
			if (rtemp == 9) {
				return true;
			}
			return solve(rtemp, ctemp);
		}
	}
}
