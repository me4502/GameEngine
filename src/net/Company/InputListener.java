package net.Company;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;

public class InputListener implements MouseListener, KeyListener {

	public InputListener() {
	}

	@Override
	public void inputEnded() {
	}

	@Override
	public void inputStarted() {
	}

	@Override
	public boolean isAcceptingInput() {
		return Engine.game.isAcceptingInput();
	}

	@Override
	public void setInput(Input arg0) {
	}

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
		Engine.game.mouseClicked(arg0, arg1, arg2, arg3);
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		Engine.game.mouseClicked(arg0, arg1, arg2, arg3);
	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
		Engine.game.mouseClicked(arg0, arg1, arg2, arg3);
	}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
		Engine.game.mousePressed(arg0, arg1, arg2);
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		Engine.game.mouseReleased(arg0, arg1, arg2);
	}

	@Override
	public void mouseWheelMoved(int arg0) {
		Engine.game.mouseWheelMoved(arg0);
	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		Engine.game.keyPressed(arg0, arg1);
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		Engine.game.keyReleased(arg0, arg1);
	}
}
