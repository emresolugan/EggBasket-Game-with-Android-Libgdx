package com.ermresolugan.project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by emre on 20.3.2017.
 */
public class MainMenu implements Screen {

    private Stage stage;
    private Table table;
    private TextureAtlas atlas;
    private Skin skin;
    private ScrollPane scrollPane;
    private TextButton play,back;
    private BitmapFont fontXS,fontS;
    private List<String> list;
    @Override
    public void show() {
        //Sahne oluşturma
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        //TextureAtlas ve Skin oluşturma
        atlas = new TextureAtlas("ui/atlas.pack");
        skin = new Skin();

        //Font oluşturma
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/abeezee.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size=150;
        fontS = generator.generateFont(parameter);

        parameter.size=80;
        fontXS = generator.generateFont(parameter);

        generator.dispose();

        //Fontları skin nesnesine ekleme
        skin.add("font-xs", fontXS);
        skin.add("font-s", fontS);

        //Skin nesnesine TextureAtlas ve JSON ekleme
        skin.addRegions(atlas);
        skin.load(Gdx.files.internal("ui/uiSkin.json"));

        //Tablo oluşturma ve sınırlarını belirleme
        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Liste oluşturma ve listenin elemanlarını belirleme
        list = new List<String>(skin);
        list.setItems(new String[] {"bir", "iki", "üç","dört","vs."});

        //ScrollPane oluşturma ve listemize ekleme
        scrollPane = new ScrollPane(list,skin);

        //Butonları oluşturma ve ClickListener ekleme
        play = new TextButton("PLAY",skin);
        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(list.getSelected());
            }
        });
        play.pad(15);

        back = new TextButton("BACK",skin);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });
        play.pad(10);


        //Tabloya nesneleri ekleme
        table.add().width(table.getWidth() / 3);
        table.add("SELECT LEVEL").width(table.getWidth() / 3).center();
        table.add().width(table.getWidth()/3).row();
        table.add(scrollPane).expandY();
        table.add(play);
        table.add(back).bottom().right();

        //Tabloyu sahneye ekleme
        stage.addActor(table);




    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Sahneyi ekrana çizdirme
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        stage.dispose();
        atlas.dispose();
        skin.dispose();
    }
}
