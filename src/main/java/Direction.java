public class Direction {
    private int rowDirection;
    private int columnDirection;

    public Direction(int rowDirection, int columnDirection) {
        this.columnDirection = columnDirection;
        this.rowDirection = rowDirection;
    }


    public int getColumnDirection() {
        return columnDirection;
    }

    public void setColumnDirection(int columnDirection) {
        this.columnDirection = columnDirection;
    }

    public int getRowDirection() {
        return rowDirection;
    }

    public void setRowDirection(int rowDirection) {
        this.rowDirection = rowDirection;
    }
}
