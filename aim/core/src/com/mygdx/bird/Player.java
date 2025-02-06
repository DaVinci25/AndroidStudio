package com.mygdx.bird;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {
    Rectangle bounds;
    AssetManager manager;
    float speedy, gravity;
    boolean onGround; // Variable para rastrear si el jugador está en el suelo

    Player() {
        setX(200);
        setY(70); // Posición inicial ajustada al suelo
        setSize(64, 45);
        bounds = new Rectangle();
        speedy = 0;
        gravity = 900;
        onGround = true; // Al inicio, el jugador está en el suelo
    }

    void impulso() {
        if ((getY() == 70) || (getY() == 80)) { // Permitir el salto si el jugador está en el suelo y en la posición y = 70 o y = 80
            speedy = 400f;
            onGround = true;
        }
    }




    @Override
    public void act(float delta) {
        //Actualiza la posición del jugador con la velocidad vertical
        moveBy(0, speedy * delta);
        //Actualiza la velocidad vertical con la gravedad
        speedy -= gravity * delta;
        bounds.set(getX(), getY(), getWidth(), getHeight());

        // Detecta si el jugador toca el suelo
        if (getY() <= 70) { // Ajuste para considerar que el suelo está en Y = 70
            setY(70); // Asegura que el jugador no se hunda en el suelo
            speedy = 0; // Detiene la velocidad vertical
            onGround = true; // Marca que el jugador está en el suelo
        }

        // Reinicia la capacidad de saltar si el jugador está en el suelo
        if (onGround && speedy <= 70) { // Verifica que el jugador esté en el suelo y no esté subiendo
            onGround = false; // Reinicia la capacidad de saltar
        }
        //Actualiza la posición del jugador con la velocidad vertical
        moveBy(0, speedy * delta);
        //Actualiza la velocidad vertical con la gravedad
        speedy -= gravity * delta;
        bounds.set(getX(), getY(), getWidth(), getHeight());

        // Detecta si el jugador toca el suelo
        if (getY() <= 70) { // Ajuste para considerar que el suelo está en Y = 70
            setY(70); // Asegura que el jugador no se hunda en el suelo
            speedy = 0; // Detiene la velocidad vertical
            // Restablece la gravedad a su valor normal cuando el jugador toca el suelo
            setGravity(900); // Establece la gravedad a su valor normal
        }

        // Reinicia la capacidad de saltar si el jugador está en el suelo
        if (getY() == 70 && speedy <= 0) { // Verifica que el jugador esté en el suelo y no esté subiendo
            onGround = true; // Marca que el jugador está en el suelo
        } else {
            onGround = false; // Marca que el jugador no está en el suelo
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(manager.get("bird.png", Texture.class), getX(), getY());
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setManager(AssetManager manager) {
        this.manager = manager;
    }
    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public boolean isOnGround() {
        return onGround;
    }
}
