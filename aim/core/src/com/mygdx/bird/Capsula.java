package com.mygdx.bird;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Capsula extends Actor {
    private AssetManager manager;
    private Rectangle bounds;
    private boolean pointingUp;
    public Capsula() {
        setSize(80, 80);
        bounds = new Rectangle();
    }

    public void setManager(AssetManager manager) {
        this.manager = manager;
    }
    public void setDirection(boolean pointingUp) {
        this.pointingUp = pointingUp;
    }


    @Override
    public void act(float delta) {
        moveBy(-200 * delta, 0); // Mueve la cápsula hacia la izquierda con una velocidad de 200 unidades por segundo
        bounds.set(getX(), getY(), getWidth(), getHeight()); // Actualiza los límites de la cápsula
        if (getX() < -getWidth()) // Si la cápsula sale completamente de la pantalla por la izquierda
            remove(); // Elimina la cápsula
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Dibuja la cápsula con la textura correspondiente
        if (manager != null) {
            Texture capsulaTexture = manager.get("capsula.png", Texture.class);
            batch.draw(capsulaTexture, getX(), getY(), getWidth(), getHeight());
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
