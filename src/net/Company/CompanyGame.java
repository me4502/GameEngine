package net.Company;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class CompanyGame {

	public Engine engine;
	
	public CompanyGame(Engine engine)
	{
		this.engine = engine;
	}
	
	public CompanyGame()
	{
	}
	
	public void render(GameContainer arg0, Graphics arg1)
	{
	}
	
	public void init(GameContainer arg0) 
	{
	}

	public void update(GameContainer arg0, int arg1) 
	{
	}
	
	public boolean close()
	{
		return true;
	}
	
	//Input section
	
	public void mousePressed(int arg0, int arg1, int arg2) 
	{
	}
	
	public void mouseReleased(int arg0, int arg1, int arg2) 
	{
	}
	
	public void mouseWheelMoved(int arg0) 
	{
	}
	
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) 
	{
	}

	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) 
	{
	}

	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) 
	{
	}
	
	public boolean isAcceptingInput() 
	{
		return true;
	}
	
	public void configLoad(String[] args)
	{
	}
	
	public String[] configSave()
	{
		return null;
	}
	
	public void keyPressed(int arg0, char arg1) 
	{
	}

	public void keyReleased(int arg0, char arg1) 
	{
	}

	public void inputEnded() {
	}

	public void inputStarted() {
	}
}