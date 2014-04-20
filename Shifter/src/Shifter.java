import java.util.LinkedList;
import java.util.Queue;

public class Shifter
{
	public static final int limit = 100;
	public static final int ap = 10;
	public static final int catchp = 2;
	public static final int placep = 1;
	public static final int shiftp = 4;
	public static final int size = 5;

	private boolean[][] lockedBoard;
	private int[] scores;
	private int[] catcherPositions;
	private int[] catcherValues;
	private int[][] board;
	private int turnPoints;
	private int turn;
	private boolean finished;

	public Shifter()
	{
		newGame();
	}

	public void newGame()
	{
		board = new int[size][size];
		catcherPositions = new int[] { 2, 2 };
		catcherValues = new int[2];
		scores = new int[2];
		finished = false;
		turn = 0;
		nextTurn();
	}

	public boolean placer(int x, int y)
	{
		if (turnPoints >= placep) {
			turnPoints--;
			board[x][y]++;
			lockedBoard[x][y] = true;
			return true;
		}
		return false;
	}

	public boolean catcher(int side, int dir)
	{
		if (turnPoints >= catchp) {
			turnPoints -= catchp;
			catcherPositions[side] = (size + catcherPositions[side] + dir)
					% size;
			return true;
		}
		return false;
	}

	public boolean shifter(int dir)
	{
		if (turnPoints >= shiftp) {
			if (move(dir)) {
				turnPoints -= shiftp;
				return true;
			}
		}
		return false;
	}

	public boolean nextTurn()
	{
		turn++;
		scores[turn % 2] += catcherValues[0] + catcherValues[1];
		if (scores[turn % 2] >= limit) {
			finished = true;
			return true;
		}
		turnPoints = ap;
		lockedBoard = new boolean[size][size];
		catcherValues = new int[2];
		return false;
	}

	public boolean move(int dir)
	{
		int[][] old = getBoard();
		// combine shit
		// 0 = right 1 = down 2 = left 3 = up
		if (dir % 2 == 0) {
			for (int r = 0; r < size; r++) {
				Queue<Integer> pieces = new LinkedList<Integer>();
				boolean encounteredLock = false;
				for (int a = 0; !encounteredLock && a < size; a++) {
					int value = board[r][dir / 2 == 1 ? a : size - 1 - a];
					boolean locked = lockedBoard[r][dir / 2 == 1 ? a : size - 1
							- a];
					if (locked)
						encounteredLock = true;
					else if (value != 0) {
						pieces.add(value);
						board[r][dir / 2 == 1 ? a : size - 1 - a] = 0;
					}
				}
				if (!pieces.isEmpty()) {
					if (dir == 0 && r == catcherPositions[0]
							&& catcherValues[0] == 0) {
						catcherValues[0] = pieces.remove();
					}
					for (int a = 0; !pieces.isEmpty() && a < size; a++) {
						board[r][dir / 2 == 1 ? a : size - 1 - a] = pieces
								.remove();
					}
				}
			}
		} else {
			for (int c = 0; c < size; c++) {
				Queue<Integer> combinations = new LinkedList<Integer>();
				boolean encounteredLock = false;
				for (int a = 0; !encounteredLock && a < size; a++) {
					int value = board[dir / 2 == 1 ? a : size - 1 - a][c];
					boolean locked = lockedBoard[dir / 2 == 1 ? a : size - 1
							- a][c];
					if (locked)
						encounteredLock = true;
					else if (value != 0) {
						combinations.add(value);
						board[dir / 2 == 1 ? a : size - 1 - a][c] = 0;
					}
				}
				if (!combinations.isEmpty()) {
					if (dir == 1 && c == catcherPositions[1]
							&& catcherValues[1] == 0) {
						catcherValues[1] = combinations.remove();
					}
					for (int a = 0; !combinations.isEmpty() && a < size; a++) {
						board[dir / 2 == 1 ? a : size - 1 - a][c] = combinations
								.remove();
					}
				}
			}
		}
		for (int r = 0; r < 5; r++)
			for (int c = 0; c < 5; c++)
				if (old[r][c] != board[r][c])
					return true;
		return false;
	}

	public int getScore(int player)
	{
		return scores[player];
	}

	public int[][] getBoard()
	{
		int[][] clone = new int[size][size];
		for (int x = 0; x < size; x++)
			for (int y = 0; y < size; y++)
				clone[x][y] = board[x][y];
		return clone;
	}

	public boolean[][] getLockedBoard()
	{
		boolean[][] clone = new boolean[size][size];
		for (int x = 0; x < size; x++)
			for (int y = 0; y < size; y++)
				clone[x][y] = lockedBoard[x][y];
		return clone;
	}

	public int[] getCatcher(int side)
	{
		int[] data = { catcherPositions[side], catcherValues[side] };
		return data;
	}

	public int getTurn()
	{
		return turn;
	}

	public int getTurnPoints()
	{
		return turnPoints;
	}

	public boolean isFinished()
	{
		return finished;
	}
}
