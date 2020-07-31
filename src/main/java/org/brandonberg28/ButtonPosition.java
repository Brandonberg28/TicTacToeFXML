package org.brandonberg28;

    public class ButtonPosition {

        private int row;  
        private int col;

        public ButtonPosition() {
            row = -1;
            col = -1;
        }

        public void setRow(int r) {
            row = r;
        }

        public void setCol(int c) {
            col = c;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
    }