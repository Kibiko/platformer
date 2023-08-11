package com.platform.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import static components.Constants.screenResHeight;
import static components.Constants.screenResWidth;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setIdleFPS(60);
		config.useVsync(true);
		config.setForegroundFPS(60);
		config.setTitle("Platformer");

		config.setWindowedMode(screenResWidth,screenResHeight);
//		config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());

		new Lwjgl3Application(new Platformer(), config);
	}
}
