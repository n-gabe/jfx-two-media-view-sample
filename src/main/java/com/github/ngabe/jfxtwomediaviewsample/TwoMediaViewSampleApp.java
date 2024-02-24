package com.github.ngabe.jfxtwomediaviewsample;

import java.net.URL;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TwoMediaViewSampleApp extends Application {

	private final ObjectProperty<MediaPlayer> mediaPlayerProperty = new SimpleObjectProperty<>();

	private BorderPane borderPane;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		borderPane = new BorderPane();
		borderPane.setCenter(createMediaViews());
		borderPane.setBottom(createToolBar());

		final var scene = new Scene(borderPane, 660, 330);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private Node createToolBar() {
		final var playButton = new Button("Play");
		playButton.setOnAction(evt -> openMedia(getClass().getResource("/waves.mp4")));

		final var stopButton = new Button("Stop");
		stopButton.setOnAction(evt -> mediaPlayerProperty.set(null));

		final var resetButton = new Button("Reset");
		resetButton.setOnAction(evt -> {
			try {
				mediaPlayerProperty.set(null);
			} finally {
				borderPane.setCenter(createMediaViews());
			}
		});

		final var toolBar = new HBox(20, playButton, stopButton, resetButton);
		toolBar.setAlignment(Pos.CENTER);
		return toolBar;
	}

	private Node createMediaViews() {
		final var pane = new HBox(10);
		pane.setFillHeight(false);
		pane.getChildren().add(createMediaView());
		pane.getChildren().add(createMediaView());
		pane.setAlignment(Pos.TOP_CENTER);
		return pane;
	}

	private Node createMediaView() {
		final var mediaView = new MediaView();
		mediaView.setPreserveRatio(true);
		mediaView.setSmooth(true);
		mediaView.setFitWidth(300);
		mediaView.setFitHeight(200);
		mediaPlayerProperty.subscribe((previous, current) -> {
			if (previous != null && previous.getStatus() == Status.PLAYING) {
				previous.stop();
			}

			mediaView.setMediaPlayer(current);
		});

		final var pane = new Pane(mediaView);
		pane.setBackground(Background.fill(Color.BLACK));
		pane.setPrefSize(300, 200);
		return pane;
	}

	private void openMedia(final URL url) {
		final var mediaPlayer = new MediaPlayer(new Media(url.toString()));
		mediaPlayer.setAutoPlay(true);
		mediaPlayer.setOnError(() -> mediaPlayer.getError().printStackTrace());
		mediaPlayerProperty.set(mediaPlayer);
	}

}
