    package com.mygdx.bird;

    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Screen;
    import com.badlogic.gdx.audio.Sound;
    import com.badlogic.gdx.graphics.OrthographicCamera;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
    import com.badlogic.gdx.math.MathUtils;
    import com.badlogic.gdx.scenes.scene2d.Stage;
    import com.badlogic.gdx.utils.Array;
    import com.badlogic.gdx.utils.ScreenUtils;
    import com.badlogic.gdx.utils.TimeUtils;
    import com.badlogic.gdx.utils.Timer;

    import java.util.Iterator;
    import java.util.Random;

    public class GameScreen implements Screen {
        OrthographicCamera camera;
        final Bird game;
        Stage stage;
        Player player;
        boolean dead;
        float score;

        private boolean jumping = false;
        private float jumpTime = 0f;
        private float originalY;
        // Define la velocidad inicial y el factor de incremento de velocidad
        private static final float INITIAL_SPIKE_SPEED = 200; // Puedes ajustar este valor según tus necesidades
        private static final float SPIKE_SPEED_INCREMENT = 10; // Puedes ajustar este valor según tus necesidades

        private float spikeSpeed = INITIAL_SPIKE_SPEED;


        public GameScreen(final Bird gam) {
            this.game = gam;
            // create the camera and the SpriteBatch
            camera = new OrthographicCamera();
            camera.setToOrtho(false, 800, 480);


            player = new Player();
            player.setManager(game.manager);
            player.setPosition(14, 80);

            stage = new Stage();
            stage.getViewport().setCamera(camera);
            stage.addActor(player);
            dead = false;
            score = 0;
            // create the obstacles array and spawn the first obstacle
            obstacles = new Array<Spike>();
            obstacles2 = new Array<Nube>(); // Inicializa la lista obstacles2
            obstacles3 = new Array<Capsula>();
            //spawnObstacle();
            originalY = player.getY();



        }

        @Override
        public void render(float delta) {

            //LOGICA


            stage.act();
            // tell the camera to update its matrices.
            camera.update();
            // process user input
            if (Gdx.input.justTouched()) {
                player.impulso();
                game.manager.get("flap.wav", Sound.class).play();
            }


           if (Gdx.input.justTouched()) {
                player.impulso();
                game.manager.get("flap.wav", Sound.class).play();
            }
            // Comprova que el jugador no es surt de la pantalla.
            // Si surt per la part inferior, game over
            if (player.getBounds().y > 480 - player.getHeight())
                player.setY(480 - player.getHeight());
           if (player.getBounds().y < 0 - player.getHeight()) {
                dead = true;
            }
            if (player.getBounds().y < 70) {
                player.setY(70);
            }

            // Genera un tiempo de espera aleatorio entre 1 y 3 segundos (en nanosegundos)
           // long randomDelay = Random.random(1, 4);
            // Comprueba si es necesario generar un nuevo obstáculo
           // System.out.println(getRandomNumber(900000000,1000000000));

            if (TimeUtils.nanoTime() - lastObstacleTime > getRandomNumber(900000000+900000000,1000000000+999999999))
                spawnObstacle();

            // Lógica de colisión y eliminación de obstáculos
            Iterator<Capsula> iter2 = obstacles3.iterator();
            while (iter2.hasNext()) {
                Capsula capsula = iter2.next();
                capsula.act(delta); // Actualiza la posición de la cápsula
                if (capsula.getX() < -64) {
                    obstacles3.removeValue(capsula, true);
                    capsula.remove(); // Elimina la cápsula del escenario
                } else if (capsula.getBounds().overlaps(player.getBounds())) {
                    // Cuando hay colisión, aumenta temporalmente la gravedad
                    player.setGravity(90); // Aumenta la gravedad a 90
                }
            }
            // Lógica de colisión y eliminación de obstáculos
            Iterator<Spike> iter = obstacles.iterator();
            Iterator<Nube> iter3 = obstacles2.iterator();
            while (iter.hasNext()) {
                Spike spike = iter.next();
                spike.act(delta); // Actualiza la posición del obstáculo
                if (spike.getX() < -64) {
                    obstacles.removeValue(spike, true);
                    spike.remove(); // Elimina el obstáculo del escenario
                } else if (spike.getBounds().overlaps(player.getBounds())) {
                    dead = true;
                }
            }
    // Treure de l'array les tuberies que estan fora de pantalla
            iter = obstacles.iterator();
            while (iter.hasNext()) {
                Spike spike = iter.next();
                if (spike.getX() < -64) {
                    obstacles.removeValue(spike, true);
                }
            }
            // Treure de l'array les tuberies que estan fora de pantalla
            iter3 = obstacles2.iterator();
            while (iter3.hasNext()) {
                Nube nube = iter3.next();
                if (nube.getX() < -64) {
                    obstacles2.removeValue(nube, true);
                }
            }

           /* if (TimeUtils.nanoTime() - lastObstacleTime > 1500000000)
                spawnObstacle();*/
    // Comprova si les tuberies colisionen amb el jugador
           /* Iterator<Spike> iter = obstacles.iterator();
            /*while (iter.hasNext()) {
                Spike spike = iter.next();
               /* if (pipe.getBounds().overlaps(player.getBounds())) {
                    dead = true;
                }

            }*/
    // Treure de l'array les tuberies que estan fora de pantalla
            iter = obstacles.iterator();
            while (iter.hasNext()) {
                Spike spike = iter.next();
                if (spike.getX() < -64) {
                    obstacles.removeValue(spike, true);
                }
            }
            if (dead) {
                game.manager.get("fail.wav", Sound.class).play();
                game.lastScore = (int)score;
                if(game.lastScore > game.topScore)
                    game.topScore = game.lastScore;
                game.setScreen(new GameOverScreen(game));
                dispose();
            }
            //La puntuació augmenta amb el temps de joc
            score += Gdx.graphics.getDeltaTime();

            //COLORES
            // clear the screen with a color
            ScreenUtils.clear(0.3f, 0.8f, 0.8f, 1);

            // tell the SpriteBatch to render in the
    // coordinate system specified by the camera.
            game.batch.setProjectionMatrix(camera.combined);
    // begin a new batch
            game.batch.begin();
            game.batch.draw(game.manager.get("fondo.png", Texture.class),
                    0, 0);
            game.batch.end();
            // Stage batch: Actors
            stage.getBatch().setProjectionMatrix(camera.combined);
            stage.draw();
            game.batch.begin();
            game.smallFont.draw(game.batch, "Score: " + (int)score, 10, 470);
            game.batch.end();


        }

        @Override
        public void resize(int width, int height) {
        }

        @Override
        public void show() {


        }

        @Override
        public void hide() {
        }

        @Override
        public void pause() {
        }

        @Override
        public void resume() {
        }

        @Override
        public void dispose() {
        }

        Array<Spike> obstacles;
        long lastObstacleTime;

        Array<Nube> obstacles2;
        Array<Capsula> obstacles3;


        private void spawnObstacle() {

            // Calcula la altura del obstáculo aleatoriamente
            float holeY = MathUtils.random(50, 230);


            // Crea un nuevo Spike y establece su posición y textura
            Spike spike = new Spike();
            // Ajusta la velocidad del Spike
            spike.setSpeed(spikeSpeed);

            // Incrementa la velocidad para el próximo Spike
            spikeSpeed += SPIKE_SPEED_INCREMENT;
            spike.setX(800); // Cambiar esto a un valor dentro de los límites de la pantalla
            spike.setY(70);
            spike.setPointingUp(true);
            spike.setManager(game.manager);

            // Agrega el Spike al escenario y al arreglo de obstáculos
            obstacles.add(spike);
            stage.addActor(spike);

            // Genera un tiempo de espera aleatorio entre 1 y 3 segundos (en nanosegundos)
            Nube nube= new Nube();
            nube.setX(800); // Cambiar esto a un valor dentro de los límites de la pantalla
            nube.setY(300);
            nube.setPointingUp(true);
            nube.setManager(game.manager);
            obstacles2.add(nube); // Agrega la nube a la lista obstacles2
            stage.addActor(nube); // Agrega la nube al stage2

            //Genera una cápsula
            Capsula capsula = new Capsula();
            capsula.setX(800); // Cambiar esto a un valor dentro de los límites de la pantalla
            capsula.setY(100);
            capsula.setDirection(true);
            capsula.setManager(game.manager);
            obstacles3.add(capsula); // Agrega la cápsula a la lista obstacles3
            stage.addActor(capsula); // Agrega la cápsula al stage2

            lastObstacleTime = TimeUtils.nanoTime();
        }
        public static int getRandomNumber(int min, int max) {
            return (int) ((Math.random() * (max - min)) + min);
        }



    }