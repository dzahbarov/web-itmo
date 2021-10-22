package ru.itmo.wp.model.TicTacToe;

/**
 * @author dzahbarov
 */
public class State {
    private final int size = 3;
    private Phase phase = Phase.RUNNING;
    private boolean crossesMove = true;
    private final Cell[][] cells = new Cell[3][3];

    public int getSize() {
        return size;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Phase getPhase() {
        return phase;
    }

    public boolean isCrossesMove() {
        return crossesMove;
    }

    public void makeMove(int row, int col) {
        cells[row][col] = crossesMove ? Cell.X : Cell.O;
        phase = checkPhase();
        crossesMove = !crossesMove;
    }

    private Phase checkPhase() {
        int cntHorizontalX = 0;
        int cntVerticalX = 0;

        int cntHorizontal0 = 0;
        int cntVertical0 = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cells[i][j] == Cell.X) cntHorizontalX++;
                if (cells[i][j] == Cell.O) cntHorizontal0++;
                if (cells[j][i] == Cell.X) cntVerticalX++;
                if (cells[j][i] == Cell.O) cntVertical0++;
            }
            if (cntHorizontalX == 3 || cntVerticalX == 3) return Phase.WON_X;
            if (cntHorizontal0 == 3 || cntVertical0 == 3) return Phase.WON_X;
            cntHorizontal0 = cntHorizontalX = cntVerticalX = cntVertical0 = 0;
        }

        int mainDiagX = 0;
        int mainDiag0 = 0;

        int notMainDiagX = 0;
        int notMainDiag0 = 0;

        for (int i = 0; i < size; i++) {
            if (cells[i][i] == Cell.X) mainDiagX++;
            if (cells[i][i] == Cell.O) mainDiag0++;
            if (cells[i][size - i - 1] == Cell.X) notMainDiagX++;
            if (cells[i][size - i - 1] == Cell.O) notMainDiag0++;
        }
        if (mainDiagX == 3 || notMainDiagX == 3) return Phase.WON_X;
        if (mainDiag0 == 3 || notMainDiag0 == 3) return Phase.WON_O;
        int cnt = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cells[i][j] != null) cnt++;
            }
        }
        if (cnt == 9) return Phase.DRAW;
        return Phase.RUNNING;
    }


}
