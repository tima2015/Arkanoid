package ru.spruceteam.arkanoid;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.spruceteam.arkanoid.screen.menu.MenuScreen;

public class Core extends Game {

	private static final String TAG = "Core";

	public static Core getCore(){
		return (Core) Gdx.app.getApplicationListener();
	}

	private final AssetManager manager = new AssetManager();
	private final GameSettings settings = new GameSettings();
	private Skin skin;
	private BitmapFont font;
	private MenuScreen menuScreen;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		settings.load();
		font = new BitmapFont();
		manager.setLoader(TiledMap.class, new TmxMapLoader());
		manager.load("misc.txt", TextureAtlas.class);
		manager.load("logo.png", Texture.class);
		manager.load("audio/click.wav", Sound.class);
		manager.load("audio/checked.wav", Sound.class);
		manager.load("audio/mainmenu.mp3", Music.class);
		manager.load("map/1.tmx", TiledMap.class);
		//manager.load("skin.json", Skin.class);
		while (!manager.update(10))
			Gdx.app.debug(TAG, "create: loading progress is " + manager.getProgress());
		//skin = manager.get("skin.json", Skin.class);
		skin = new Skin(Gdx.files.internal("skin.json"));
		setScreen(menuScreen = new MenuScreen());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	public AssetManager getManager() {
		return manager;
	}

	public Skin getSkin() {
		return skin;
	}

	public GameSettings getSettings() {
		return settings;
	}

	public MenuScreen getMenuScreen() {
		return menuScreen;
	}

	@Override
	public void dispose() {
		super.dispose();
		settings.save();
		manager.dispose();
		skin.dispose();
		font.dispose();
	}
}
