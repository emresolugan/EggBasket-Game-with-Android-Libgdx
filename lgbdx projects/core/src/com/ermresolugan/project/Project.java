package com.ermresolugan.project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class Project extends Game {

	private SpriteBatch page;
	private OrthographicCamera camera;
	private Texture basketTexture;         // resimler
	private Texture eggTexture;
	private Rectangle recBasket; 				//  kovanın kapladığı alanı belirtmek için
	private ArrayList<Rectangle> eggs;
	private long lasteggs;
	private Sound eggvoice;

	@Override
	public void create () {    //Nesnelerin oluşturulması create fonksiyonu
		//setScreen(new MainMenu());
		eggs = new ArrayList<Rectangle>();
		CreateEgg();
		camera = new OrthographicCamera();
		//X ekseni 800px - Y ekseni 480px
		camera.setToOrtho(false,800,480);

		page = new SpriteBatch();

		basketTexture = new Texture("Basket.png");
		eggTexture = new Texture("Egg.png");
		eggvoice = Gdx.audio.newSound(Gdx.files.internal("shot.wav"));

		recBasket = new Rectangle();

		recBasket.width = 64;
		recBasket.height = 64;

		// X ekseninde ortalama
		recBasket.setX(400-64);
		//Y ekseninde ise 20px yukarıya konumlanmasını belirtiyoruz
		recBasket.setY(20);




	}

	@Override
	public void render () {

		if(TimeUtils.nanoTime()-lasteggs > 1000000000)
			CreateEgg(); // 1 saniye aralıklar ile yumurta atmayı yap

		Iterator<Rectangle> iter = eggs.iterator();   //aşağı salma yumurtayı
		while(iter.hasNext())
		{
			Rectangle egg = iter.next();
			egg.y -= 200 * Gdx.graphics.getDeltaTime();
			if(egg.y + 64 < 0)
				iter.remove();
			if(egg.overlaps(recBasket))
			{
				eggvoice.play();
				iter.remove();
			}
		}

		if(Gdx.input.isTouched())
		{
			Vector3 touchposition = new Vector3();
			touchposition.set(Gdx.input.getX(),Gdx.input.getY(),0);
			camera.unproject(touchposition);
			recBasket.x = touchposition.x - 64/2;

		}

		Gdx.gl.glClearColor(0, 0, 0.2f, 1);  //arka plan renk
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		//Burada sayfaya kameranın görüntüsünü aktarıyoruz
		page.setProjectionMatrix(camera.combined);

		//Burada ise sayfamızın çizim kodlarını başlatıyoruz
		//Çizdireceğimiz ögeleri begin ile end fonksiyonları arasına yazacağız
		page.begin();

		//draw fonksiyonu ( texture, x konumu, y konumu, genişlik, yükseklik ) parametrelerini alıyor
		//Gerekli parametreleri oluşturduğumuz dikdörtgenin yardımıyla giriyoruz
		page.draw(basketTexture,recBasket.x,recBasket.y,recBasket.width,recBasket.height);
		for(Rectangle egg: eggs)
			page.draw(eggTexture, egg.x, egg.y);
		page.end();


	}
	
	@Override
	public void dispose () {  		//Oyun kapandıktan sonra nesnelerimizi temizliyoruz

		page.dispose();
		basketTexture.dispose();
		eggTexture.dispose();

	}

	private void CreateEgg() {

		Rectangle egg = new Rectangle();
		egg.x = MathUtils.random(0, 800 - 64);
		egg.y = 480;
		egg.width = 64;
		egg.height = 64;
		eggs.add(egg);
		lasteggs = TimeUtils.nanoTime();

	}
}
