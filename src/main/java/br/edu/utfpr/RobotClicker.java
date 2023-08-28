package br.edu.utfpr;

import java.awt.*;
import java.awt.event.InputEvent;
public class RobotClicker implements Runnable {
    private final int targetX;
    private final int targetY;

    public RobotClicker(int targetX, int targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }

    @Override
    public void run() {
        try {
            Robot robot = new Robot();
            int i = 0;
            robot.delay(5000);
            while (AutoClickerWithKeyListener.isRunning) {
                robot.mouseMove(targetX, targetY);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(20);
                i++;

                if (i == 50) {
                    AutoClickerWithKeyListener.isRunning = false;
                }
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
