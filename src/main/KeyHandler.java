package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed = false,
            downPressed= false,
            leftPressed = false,
            rightPressed = false,
            qPressed = false,
            sPressed = false,
            spacePressed = false;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_UP)
        {
            upPressed = true;
        }
        if(code == KeyEvent.VK_DOWN)
        {
            downPressed = true;
        }
        if(code == KeyEvent.VK_LEFT)
        {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_RIGHT)
        {
            rightPressed = true;
        }
        if(code == KeyEvent.VK_Q)
        {
            qPressed = true;
        }
        if(code == KeyEvent.VK_S)
        {
            sPressed = true;
        }
        if(code == KeyEvent.VK_SPACE)
        {
            spacePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_UP)
        {
            upPressed = false;
        }
        if(code == KeyEvent.VK_DOWN)
        {
            downPressed = false;
        }
        if(code == KeyEvent.VK_LEFT)
        {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_RIGHT)
        {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_Q)
        {
            qPressed = false;
        }
        if(code == KeyEvent.VK_S)
        {
            sPressed = false;
        }
        if(code == KeyEvent.VK_SPACE)
        {
            spacePressed = false;
        }
    }
}
