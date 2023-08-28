package br.edu.utfpr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AutoClickerWithKeyListener {
    public static volatile boolean isRunning = true;

    public static void main(String[] args) {
        int targetX = 457;
        int targetY = 23;

        RobotClicker clicker = new RobotClicker(targetX, targetY);
        Thread clickerThread = new Thread(clicker);

        KeyListener keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_X) {
                    isRunning = false;
                }
            }
        };

        Thread keyListenerThread = new Thread(() -> {
            JFrame frame = new JFrame();
            frame.setSize(300, 25);
            frame.addKeyListener(keyListener);
            frame.setVisible(true);
        });

        clickerThread.start();
        keyListenerThread.start();

        try {
            clickerThread.join();
            keyListenerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class RobotClicker implements Runnable {
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
