package net.Company;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Engine extends BasicGame {

	public Engine(String title) {
		super(title);
	}

	/* Settings */
	public static int AA = 0;
	public static boolean VSync = true;
	public static boolean Debug = false;
	public static int targetFrames = 120;
	public static boolean Fullscreen = false;

	public static String title = "Game Engine";

	public static int width = 612;
	public static int height = 384;
	public static int cenX = width / 2;
	public static int cenY = height / 2;

	public static int mouseX;
	public static int mouseY;

	public static CompanyGame game;

	public static AppGameContainer app;

	public static ConfigurationManager config;

	boolean hasInitialized = false;

	public static void setup(String title, CompanyGame game, int x, int y) {
		try {
			Engine.game = game;
			Engine.title = title;
			Engine.width = x;
			Engine.height = y;
			cenX = width / 2;
			cenY = height / 2;
			System.out.println(EngineUtils.getDirectory());
			config = new ConfigurationManager();
			config.load();
			while (EngineUtils.hasGotNatives == false) {

			}
			System.setProperty(
					"org.lwjgl.librarypath",
					EngineUtils.getDirectory() + "/natives/"
							+ EngineUtils.getOs() + "");
			app = new AppGameContainer(new Engine(title));
			app.setDisplayMode(width, height, Fullscreen);
			app.setVSync(VSync);
			app.setMultiSample(AA);
			app.setVerbose(Debug);
			app.setTargetFrameRate(targetFrames);
			app.setShowFPS(Debug);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		if (!hasInitialized)
			return;
		game.render(arg0, arg1);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		arg0.getInput().addMouseListener(new InputListener());
		arg0.getInput().addKeyListener(new InputListener());
		game.init(arg0);
		hasInitialized = true;
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		if (!hasInitialized)
			return;
		mouseX = arg0.getInput().getMouseX();
		mouseY = arg0.getInput().getMouseY();
		game.update(arg0, arg1);
	}

	@Override
	public boolean closeRequested() {
		config.save();
		return game.close();
	}
}
