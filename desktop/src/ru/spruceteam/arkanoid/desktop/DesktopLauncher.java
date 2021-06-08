package ru.spruceteam.arkanoid.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.spruceteam.arkanoid.Core;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = config.height = 800;
		config.title = "Arkanoid by Sherstobitov";
		config.vSyncEnabled = true;
		config.addIcon("icon.png", Files.FileType.Internal);
		new LwjglApplication(new Core(), config);
	}
}
