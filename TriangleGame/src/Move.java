public class Move {
	private int currentRow;
	private int currentCol;
	private int newRow;
	private int newCol;
	private int removeRow;
	private int removeCol;

	public Move(int currentRow, int currentCol, int newRow, int newCol) {
		this.currentRow = currentRow;
		this.currentCol = currentCol;
		this.newRow = newRow;
		this.newCol = newCol;
	}
	
	public int getCurrentRow() {
		return currentRow;
	}
	
	public int getCurrentCol() {
		return currentCol;
	}
	
	public int getNewRow() {
		return newRow;
	}
	
	public int getNewCol() {
		return newCol;
	}

	public void setRemoved(int removeRow, int removeCol) {
		this.setRemoveRow(removeRow);
		this.setRemoveCol(removeCol);
	}

	public String getString() {
		return ("" + (char) ('a' + currentRow) + "" + (currentCol + 1) + " to  " + (char) (newRow + 'a') + ""
				+ (newCol + 1));
	}

	public int getRemoveRow() {
		return removeRow;
	}

	public void setRemoveRow(int removeRow) {
		this.removeRow = removeRow;
	}

	public int getRemoveCol() {
		return removeCol;
	}

	public void setRemoveCol(int removeCol) {
		this.removeCol = removeCol;
	}
}