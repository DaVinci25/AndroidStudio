package com.mygdx.bird;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Spike extends Actor {
    private boolean pointingUp;
    private AssetManager manager;
    private Rectangle bounds;
    private float speed;

    public Spike() {
        setSize(44, 22);
        setY(70);
        bounds = new Rectangle();
    }

    public void setPointingUp(boolean pointingUp) {
        this.pointingUp = pointingUp;
    }

    public void setManager(AssetManager manager) {
        this.manager = manager;
    }

    @Override
    public void act(float delta) {
        moveBy(-speed * delta, 0);
        bounds.set(getX(), getY(), getWidth(), getHeight());
        if(!isVisible())
            setVisible(true);
        if (getX() < -64)
            remove();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Dibuja el pincho con la textura correspondiente
        if (manager != null) {
            Texture spikeTexture = manager.get("pincho.png", Texture.class);
            batch.draw(spikeTexture, getX(), getY(), getWidth(), getHeight());
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

}
