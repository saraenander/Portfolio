package Projekt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SudokuSolverTest {
	int[][] m;
	SudokuSolver solver;
	
	@BeforeEach
	void setUp() throws Exception {
		m = new int[9][9];
		solver = new SudokuSolver();
	}
	
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public final void testSetGetValue() {
		solver.setValue(2,3,4);
		solver.setValue(5, 7, 22);
		assertEquals("Should be zero:", 0, solver.getValue(5, 7));
		assertEquals("wrong setValue:", 4, solver.getValue(2, 3));
		assertEquals("Empty space not zero", 0, solver.getValue(2, 4));
	}
	
	@Test
	public final void testCheckRow() {
		solver.setValue(2,3,4);
		assertTrue("Is not true", solver.checkRow(2,2));
		solver.setValue(2, 4, 1);
		assertFalse("Is not false", solver.checkRow(2,1));
		
	}
	@Test
	public final void testOk() {
		solver.setValue(2,3,4);
			assertTrue("Value works here", solver.ok(2,4,1));
			assertTrue("Value works here", solver.ok(2,3,1));
			assertTrue("Value works here", solver.ok(1,4,1));
			assertFalse("Value does not work here", solver.ok(2,4,4));
			assertFalse("Value does not work here", solver.ok(3,3,4));
			assertFalse("Value does not work here", solver.ok(1,4,4));
	}

	@Test
	public final void testCheckCol() {
		solver.setValue(2,3,4);
		assertTrue("Is not true", solver.checkCol(3,1));
		solver.setValue(5, 3, 1);
		assertFalse("Is not false", solver.checkCol(3,1));
		
	}
	
	@Test
	public final void testCheckSq() {
		solver.setValue(0, 0, 1);
		solver.setValue(1, 2, 2);
		assertTrue("Should be true", solver.checkSq(2,2,3));
		assertFalse("Should be false but is not", solver.checkSq(2,1,1));
		
	}
	@Test
	public final void testSolve() {
	solver.setValue(0, 2, 8);
	solver.setValue(0, 5, 9);
	solver.setValue(0, 7, 6);
	solver.setValue(0, 8, 2);
	solver.setValue(1, 8, 5);
	solver.setValue(2, 0, 1);
	solver.setValue(2, 2, 2);
	solver.setValue(2, 3, 5);
	solver.setValue(3, 3, 2);
	solver.setValue(3, 4, 1);
	solver.setValue(3, 7, 9);
	solver.setValue(4, 1, 5);
	solver.setValue(4, 6, 6);
	solver.setValue(5, 0, 6);
	solver.setValue(5, 7, 2);
	solver.setValue(5, 8, 8);
	solver.setValue(6, 0, 4);
	solver.setValue(6, 1, 1);
	solver.setValue(6, 3, 6);
	solver.setValue(6, 5, 8);
	solver.setValue(7, 0, 8);
	solver.setValue(7, 1, 6);
	solver.setValue(7, 4, 3);
	solver.setValue(7, 6, 1);
	solver.setValue(8, 6, 4);
	assertTrue("Should work", solver.solve());
	assertEquals("Rätt värde:", 3, solver.getValue(1,0));
}
	@Test
	public final void testSolveEmpty() {
		assertTrue("Should work", solver.solve());
	}
	@Test
	public final void testSolveUnsolvable(){
		solver.setValue(0, 2, 8);
		solver.setValue(0, 5, 9);
		solver.setValue(0, 7, 6);
		solver.setValue(0, 8, 2);
		solver.setValue(1, 8, 5);
		solver.setValue(2, 0, 1);
		solver.setValue(2, 2, 2);
		solver.setValue(2, 3, 5);
		solver.setValue(3, 3, 2);
		solver.setValue(3, 4, 1);
		solver.setValue(3, 7, 9);
		solver.setValue(4, 1, 5);
		solver.setValue(4, 6, 6);
		solver.setValue(5, 0, 6);
		solver.setValue(5, 6, 2);
		solver.setValue(5, 7, 8);
		solver.setValue(6, 0, 4);
		solver.setValue(6, 1, 1);
		solver.setValue(6, 3, 6);
		solver.setValue(6, 5, 8);
		solver.setValue(7, 0, 8);
		solver.setValue(7, 1, 6);
		solver.setValue(7, 4, 3);
		solver.setValue(7, 6, 1);
		solver.setValue(8, 6, 4);
		assertFalse("Should be false but is not", solver.solve());
	}
}
