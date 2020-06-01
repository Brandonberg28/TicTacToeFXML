package org.brandonberg28;

    public class ButtonPosition {

        public int row;  //these being public, doesnt that make these tightly coupled with PrimaryController and no good?
        public int col;

        public ButtonPosition() {
            row = -1;
            col = -1;
        }

        /*
        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }*/
    }