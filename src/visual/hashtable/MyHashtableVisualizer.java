package visual.hashtable;

import simplegui.AbstractDrawable;
import simplegui.SGMouseListener;
import simplegui.SimpleGUI;

import java.awt.*;

/**
 * Visual Hashtable
 * Created by Connor Crawford on 11/9/15.
 */
public class MyHashtableVisualizer implements SGMouseListener {

    private SimpleGUI simpleGUI;
    private int cellSize = 60;
    private int buttonWidth = 0;
    private MyHashtable hashtable;

    public MyHashtableVisualizer() {
        hashtable = new MyHashtable();
        simpleGUI = new SimpleGUI(600,650, false);
        simpleGUI.registerToMouse(this);
        buttonWidth = simpleGUI.getWidth()/3;
        drawGrid();
        drawButtons();
    }

    /**
     * Computes the color of the cell given how many collisions have occurred at that cell
     * @param collisions the number of collisions at that cell
     * @return the color of the cell
     */
    private Color computeCellColor(int collisions){
        if (collisions == -1)
            return Color.BLACK;
        int r = (int)(255.0/7.0*collisions); // collisions = 0: r = 0. coll >=7: r>=255.
        r = (int)Math.min(r,255); // same as: if (r>255) r = 255;
        int g = 255-r; // green value: the 'opposite' of r
        int b = 0; // sets blue to zero, only red/green values are displayed
        return(new Color(r,g,b));
    }

    /**
     * Draws the grid of cells. Should only be called once in MyHashtableVisualizer's constructor
     */
    private void drawGrid() {
        int x = 0, y = 0, count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                simpleGUI.drawFilledBox(x, y, cellSize, cellSize, computeCellColor(-1), 1.0, "cell" + count);
                simpleGUI.drawBox(x-1, y-1, cellSize+1, cellSize+1, Color.WHITE, 1.0, 1, "cellborder" + count);
                y += cellSize;
                count++;
            }
            y = 0;
            x += cellSize;
        }
    }

    /**
     * Updates the colors of each cell in the grid
     */
    private void repaintGrid() {
        AbstractDrawable cell;
        for (int i = 0; i < MyHashtable.SIZE; i++) {
            Color newColor = computeCellColor(hashtable.getCollisionsAtIndex(i));
            cell = simpleGUI.getDrawable("cell" + i);
            cell.color = newColor;
        }
        simpleGUI.repaintPanel();
    }

    /**
     * Draws the custom button elements
     */
    private void drawButtons() {
        int x = 0, y = simpleGUI.getHeight() - 50;

        // Draw Reset button
        simpleGUI.drawFilledBox(x, y, buttonWidth, 50, Color.GRAY, 1.0, "Reset");
        simpleGUI.drawBox(x, y, buttonWidth, 50, Color.BLACK, 1.0, 1, "Reset");
        simpleGUI.drawText("Reset", x + (buttonWidth/2) - 15, y + 30);

        // Draw Next 10 button
        x += buttonWidth;
        simpleGUI.drawFilledBox(x, y, buttonWidth, 50, Color.GRAY, 1.0, "Next");
        simpleGUI.drawBox(x, y, buttonWidth, 50, Color.BLACK, 1.0, 1, "Next");
        simpleGUI.drawText("Next 10", x + (buttonWidth/2) - 20, y + 30);

        // Draw avg. num collisions
        x += buttonWidth;
        simpleGUI.drawBox(x, y, buttonWidth, 50, Color.BLACK, 1.0, 1, "CollBorder");
        simpleGUI.drawText(0.0 + "", x + (buttonWidth/2) - 5, y + 30, Color.BLACK, 1.0, "AvgCol");
    }

    @Override
    public void reactToMouseClick(int i, int i1) {
        if (i1 >= simpleGUI.getHeight() - 50) {
            if (i < buttonWidth) {
                hashtable = new MyHashtable();
                repaintGrid();
            } else if (i < buttonWidth * 2) {
                hashtable.insert10();
                repaintGrid();
            }
            AbstractDrawable collText = simpleGUI.getDrawable("AvgCol");
            int x = collText.posX, y = collText.posY;
            simpleGUI.eraseSingleDrawable("AvgCol");
            simpleGUI.drawText(hashtable.getAvgCollisions() + "", x, y, Color.BLACK, 1.0, "AvgCol");
        }
    }
}
