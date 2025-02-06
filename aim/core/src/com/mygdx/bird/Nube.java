package com.mygdx.bird;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Nube extends Actor {
    private boolean pointingUp;
    private AssetManager manager;
    private Rectangle bounds;

    public Nube() {
        setSize(100, 100);
        setY(80);
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
        moveBy(-200 * delta, 0); // Mueve la nube hacia la izquierda con una velocidad de 200 unidades por segundo
        bounds.set(getX(), getY(), getWidth(), getHeight()); // Actualiza los límites de la nube
        if (getX() < -getWidth()) // Si la nube sale completamente de la pantalla por la izquierda
            remove(); // Elimina la nube
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Dibuja el nube con la textura correspondiente
        if (manager != null) {
            Texture nubeTexture = manager.get("nube.png", Texture.class);
            batch.draw(nubeTexture, getX(), getY(), getWidth(), getHeight());
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
