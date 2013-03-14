package net.Company;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.me4502.MAPL.slick.SlickMAPL;

public class Engine extends BasicGame {

	public Engine(String title) {
		super(title);
		this.title = title;
		utilities = new EngineUtils(this);
	}

	/* Settings */
	public int AA = 0;
	public boolean VSync = true;
	public boolean Debug = false;
	public int targetFrames = 120;
	public boolean Fullscreen = false;
	public boolean Resizable = false;

	public String title = "Game Engine";

	public int width = 612;
	public int height = 384;
	public int cenX = width / 2;
	public int cenY = height / 2;

	public int mouseX;
	public int mouseY;

	public CompanyGame game;

	public static ConfigurationManager config;

	boolean hasInitialized = false;

	public static JFrame gameFrame;

	boolean paused = false;

	public static CanvasGameContainer app;

	//public static ScalableGame scaleable;

	private int speed = 1;

	public EngineUtils utilities;

	public void setResizable(boolean resizable) {
		gameFrame.setResizable(resizable);
	}

	public boolean getResizable() {
		return gameFrame.isResizable();
	}

	public static Engine setup(String title, CompanyGame game, int x, int y, boolean force) {
		try {
			new SlickMAPL();
			final Engine engine = new Engine(title);

			engine.game = game;
			engine.width = x;
			engine.height = y;
			engine.cenX = engine.width / 2;
			engine.cenY = engine.height / 2;

			JFrame frame = null;
			frame = new JFrame(engine.utilities.isInstalling() ? "Installing!" : "Loading!");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.getContentPane().add(new Label(engine.utilities.isInstalling() ? "Installing!" : "Loading!"), BorderLayout.CENTER);
			frame.pack();
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			int w = frame.getSize().width;
			int h = frame.getSize().height;
			int fx = (dim.width-w)/2;
			int fy = (dim.height-h)/2;
			frame.setLocation(fx, fy);
			frame.setAlwaysOnTop(true);

			System.out.println(engine.utilities.getAppDir());
			engine.utilities.downloadNatives(force);

			config = new ConfigurationManager(engine);
			config.load();
			while (engine.utilities.hasGotNatives == false) {
				if(!frame.isVisible())
					frame.setVisible(true);
			}

			frame.dispose();

			System.setProperty("org.lwjgl.librarypath", engine.utilities.getAppDir() + "/natives/" + engine.utilities.getOs() + "");

			gameFrame = new JFrame(title);
			gameFrame.setSize(engine.width, engine.height);
			gameFrame.setLocationRelativeTo(null);

			app = new CanvasGameContainer(engine);
			app.getContainer().setVSync(engine.VSync);
			app.getContainer().setMultiSample(engine.AA);
			app.getContainer().setVerbose(engine.Debug);
			app.getContainer().setTargetFrameRate(engine.targetFrames);
			app.getContainer().setShowFPS(engine.Debug);
			app.getContainer().setAlwaysRender(true);

			gameFrame.add(app);
			gameFrame.setResizable(true);

			gameFrame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					engine.closeRequested();
					gameFrame.dispose();
					app.getContainer().exit();
					System.exit(0);
				}
			});
			gameFrame.setVisible(true);

			app.start();

			return engine;
		} catch (UnsatisfiedLinkError e) {
			setup(title,game,x,y,true);
		} catch (SlickException e1) {
			e1.printStackTrace();
		}

		return null;
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) {
		if (!hasInitialized)
			return;
		game.render(arg0, arg1);
	}

	@Override
	public void init(GameContainer arg0) {

		//GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_WRAP_S,GL11.GL_REPEAT);
		game.init(arg0);
		hasInitialized = true;
	}

	@Override
	public void update(GameContainer arg0, int arg1) {
		if(Fullscreen != gameFrame.isUndecorated()) {
			gameFrame.setVisible(false);
			gameFrame.dispose();
			if(Fullscreen) {
				Toolkit tk = Toolkit.getDefaultToolkit();
				int xSize = (int) tk.getScreenSize().getWidth();
				int ySize = (int) tk.getScreenSize().getHeight();
				gameFrame.setSize(xSize,ySize);
				gameFrame.setLocation(0, 0);
			} else {
				gameFrame.setSize(width, height);
				gameFrame.setLocationRelativeTo(null);
			}
			gameFrame.setAutoRequestFocus(true);
			gameFrame.setUndecorated(Fullscreen);
			gameFrame.setVisible(true);
			gameFrame.toFront();
			new Thread() {
				@Override
				public void run() {
					synchronized(gameFrame) {
						gameFrame.setAlwaysOnTop(true);
						gameFrame.toFront();
						gameFrame.setAlwaysOnTop(false);
					}
				}
			}.start();
		}
		if (!hasInitialized)
			return;
		for(int i = 0; i < speed; i++) {
			if(mouseX != arg0.getInput().getMouseX() || mouseY != arg0.getInput().getMouseY()) {
				mouseX = arg0.getInput().getMouseX();
				mouseY = arg0.getInput().getMouseY();
			}
			game.update(arg0, arg1);
		}
	}

	@Override
	public boolean closeRequested() {
		config.save();
		return game.close();
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public boolean getPaused() {
		return paused;
	}

	public void setSpeedScale(int speed) {
		this.speed = speed;
	}

	public int getSpeedScale() {
		return speed;
	}

	/* Input Listeners */

	@Override
	public boolean isAcceptingInput() {
		return game.isAcceptingInput();
	}

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
		game.mouseClicked(arg0, arg1, arg2, arg3);
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		game.mouseDragged(arg0, arg1, arg2, arg3);
	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
		game.mouseMoved(arg0, arg1, arg2, arg3);
	}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
		game.mousePressed(arg0, arg1, arg2);
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		game.mouseReleased(arg0, arg1, arg2);
	}

	@Override
	public void mouseWheelMoved(int arg0) {
		game.mouseWheelMoved(arg0);
	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		game.keyPressed(arg0, arg1);
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		game.keyReleased(arg0, arg1);
	}
}