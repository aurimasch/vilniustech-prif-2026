package edu.vilniustech.hello;

public class Ghost {

    private int row;
    private int col;
    private int dr;
    private int dc;

    public Ghost(int row, int col, int dr, int dc) {
        this.row = row;
        this.col = col;
        this.dr = dr;
        this.dc = dc;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getDr() {
        return dr;
    }

    public int getDc() {
        return dc;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setDirection(int dr, int dc) {
        this.dr = dr;
        this.dc = dc;
    }

    public boolean isAt(int row, int col) {
        return this.row == row && this.col == col;
    }
}
