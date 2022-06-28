package com.s24825.controller;

import com.s24825.Settings;
import com.s24825.model.Engine;
import com.s24825.view.ViewFactory;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class GameWindowController extends BaseController implements Initializable {


    @FXML
    private ImageView loseScreen;

    @FXML
    private Canvas canvas;

    @FXML
    private Text timeLabel;

    @FXML
    private Text scoreLabel;

    @FXML
    private ImageView timeImg;

    @FXML
    private ImageView scoreImg;

    @FXML
    void onKeyPressed(KeyEvent event) {

        KeyCode code = event.getCode();
        int currentDirection = engine.getCurrentDirection();

        if (!engine.isChangedDirection()) {
            if (code == KeyCode.RIGHT) {
                if (currentDirection != LEFT) {
                    engine.setCurrentDirection(RIGHT);
                    engine.setChangedDirection(true);
                }

            } else if (code == KeyCode.LEFT) {
                if (currentDirection != RIGHT) {
                    engine.setCurrentDirection(LEFT);
                    engine.setChangedDirection(true);
                }

            } else if (code == KeyCode.UP) {
                if (currentDirection != DOWN) {
                    engine.setCurrentDirection(UP);
                    engine.setChangedDirection(true);
                }

            } else if (code == KeyCode.DOWN) {
                if (currentDirection != UP) {
                    engine.setCurrentDirection(DOWN);
                    engine.setChangedDirection(true);
                }
            }
        }
    }

    private int width;
    private int height;
    private int squareSize;

    private GraphicsContext board;
    private Engine engine;

    private final int UP = 0;
    private final int DOWN = 1;
    private final int LEFT = 2;
    private final int RIGHT = 3;

    private Timeline clock;
    private Timeline score;





    public GameWindowController(ViewFactory viewFactory, String fxmlName, Settings settings) {
        super(viewFactory, fxmlName, settings);
    }

    public void timerTimeLine() {
        DateFormat timeFormat = new SimpleDateFormat( "mm:ss" );
        clock = new Timeline(
                new KeyFrame(
                        Duration.millis( 500 ),
                        event -> {
                            timeLabel.setText( timeFormat.format(engine.calculateTime()));
                            if (engine.isGameOver()) {
                                loseScreen.setVisible(true);
                                clock.stop();
                            }
                        }
                )
        );
        clock.setCycleCount( Animation.INDEFINITE );
        clock.play();
    }
    public void scoreTimeLine() {
        score = new Timeline(
                new KeyFrame(
                        Duration.millis( 500 ), event -> {
                            scoreLabel.setText(engine.getTotalScore());
                    if (engine.isGameOver()) {
                        score.stop();
                    }
                }
                )
        );
        score.setCycleCount( Animation.INDEFINITE );
        score.play();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setValues();
        this.engine = new Engine(board, settings.getRows(), settings.getColumns(), squareSize, width, height);
        Parent anchorPane = canvas.getParent();
        anchorPane.requestFocus();
        engine.start();


        timerTimeLine();
        scoreTimeLine();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(settings.getSpeed()), e -> engine.mainGame()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }


    private void setValues() {
        this.board = canvas.getGraphicsContext2D();
        this.width = (int) canvas.getWidth();
        this.height = (int) canvas.getHeight();
        this.squareSize = width / settings.getColumns();
    }


}
