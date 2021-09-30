package com.kodilla.tictactoe;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;

public class TicTacToe extends Application {

    private final Image imageback = new Image("file:src/main/resources/bckggreen.jpg");
    private final Image circle = new Image("file:src/main/resources/circle_green.png");
    private final Image cross = new Image("file:src/main/resources/cross_green.png");
    private final ImageView board = new ImageView("file:src/main/resources/kratka_green.png");

    private final List<Button> buttons = new LinkedList<>();
    private final List<Button> controlButtons = new LinkedList<>();
    private final List<RadioButton> difficultyButtons = new LinkedList<>();
    private final List<Integer> emptyButtons = new LinkedList<>();
    private final List<Line> lines = new LinkedList<>();

    private boolean isTurnX = true;
    private int difficulty = 1;
    private int computerScore = 0;
    private int playerScore = 0;
    private final Random random = new Random();
    private String username;

    private final Label label = new Label();
    private final Label hello = new Label();
    private final Label resultList = new Label();
    private final Label scoreBoard = new Label();
    private final Label chooseDifficulty = new Label("Choose\ndifficulty:");
    private final Button newGame = new Button("NEW GAME");
    private final Button saveGame = new Button("SAVE GAME");
    private final Button resetScore = new Button("RESTART GAME");
    private final RadioButton easy = new RadioButton("Easy");
    private final RadioButton hard = new RadioButton("Hard");
    private final RadioButton impossible = new RadioButton("Impossible");
    private final ToggleGroup toggleDifficulty = new ToggleGroup();
    private final VBox sideButtonBar = new VBox();
    private final HBox topButtonBar = new HBox();
    private final GridPane grid = new GridPane();
    private final Pane pane = new Pane();

    private static final String O = "o";
    private static final String X = "x";
    private static final String WIN = "You won!";
    private static final String LOSE = "You lost!";

    private int lastMove;
    private int lastComputerMove;
    private int lastPlayerMove;
    private int computerMove;
    private final int[] winningLine = new int[3];

    private final TextInputDialog dialog = new TextInputDialog();
    private final File ranking = new File("ranking.list");
    private final Map<String, String> resultMap = new HashMap<>();

    private final Line line048 = new Line(5.0f, 5.0f, 385.0f, 385.0f);
    private final Line line246 = new Line(5.0f, 385.0f, 385.0f, 5.0f);
    private final Line line012 = new Line(5.0f, 65.0f, 385.0f, 65.0f);
    private final Line line345 = new Line(5.0f, 195.0f, 385.0f, 195.0f);
    private final Line line678 = new Line(5.0f, 325.0f, 385.0f, 325.0f);
    private final Line line036 = new Line(65.0f, 5.0f, 65.0f, 385.0f);
    private final Line line147 = new Line(195.0f, 5.0f, 195.0f, 385.0f);
    private final Line line258 = new Line(325.0f, 5.0f, 325.0f, 385.0f);

    public boolean checkForThree(int index1, int index2, int index3) {
        if (buttons.get(index1).isDisabled() && buttons.get(index2).isDisabled() && buttons.get(index3).isDisabled() && (buttons.get(index1).getId()).equals(buttons.get(index2).getId()) && buttons.get(index1).getId().equals(buttons.get(index3).getId())) {
            winningLine[0] = index1;
            winningLine[1] = index2;
            winningLine[2] = index3;
            return true;
        } else {
            return false;
        }
    }

    public boolean checkForTwo(int index1, int index2, int index3) {
        if ((buttons.get(index1).getId()).equals(buttons.get(index2).getId()) && !buttons.get(index3).isDisabled()) {
            computerMove = index3;
            return true;
        } else if (buttons.get(index1).getId().equals(buttons.get(index3).getId()) && !buttons.get(index2).isDisabled()) {
            computerMove = index2;
            return true;
        } else {
             return false;
        }
    }

    public boolean isThreeInARow() {
        if (emptyButtons.size()<5) {
            switch(lastMove) {
                case 0:
                    return (checkForThree(0,1,2) || checkForThree(0,3,6) || checkForThree(0,4,8));
                case 1:
                    return (checkForThree(0,1,2) || checkForThree(1,4,7));
                case 2:
                    return (checkForThree(0,1,2) || checkForThree(2,4,6) || checkForThree(2,5,8));
                case 3:
                    return (checkForThree(3,4,5) || checkForThree(0,3,6));
                case 4:
                    return (checkForThree(3,4,5) || checkForThree(2,4,6) || checkForThree(1,4,7) || checkForThree(0,4,8));
                case 5:
                    return (checkForThree(3,4,5) || checkForThree(2,5,8));
                case 6:
                    return (checkForThree(6,7,8) || checkForThree(2,4,6) || checkForThree(0,3,6));
                case 7:
                    return (checkForThree(6,7,8) || checkForThree(1,4,7));
                case 8:
                    return (checkForThree(6,7,8) || checkForThree(2,5,8) || checkForThree(0,4,8));
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    public boolean isTwoInARow(int move) {
        if (emptyButtons.size()<7) {
            switch (move) {
                case 0:
                    return checkForTwo(0,1,2) || checkForTwo(0,3,6) || checkForTwo(0,4,8);
                case 1:
                    return checkForTwo(1,0,2) || checkForTwo(1,4,7);
                case 2:
                     return checkForTwo(2,0,1) || checkForTwo(2,4,6) || checkForTwo(2,5,8);
                case 3:
                    return checkForTwo(3,4,5) || checkForTwo(3,0,6);
                case 4:
                    return checkForTwo(4,3,5) || checkForTwo(4,2,6) || checkForTwo(4,1,7) || checkForTwo(4,0,8);
                case 5:
                    return checkForTwo(5,3,4) || checkForTwo(5,2,8);
                case 6:
                    return checkForTwo(6,7,8) || checkForTwo(6,2,4) || checkForTwo(6,0,3);
                case 7:
                    return checkForTwo(7,6,8) || checkForTwo(7,1,4);
                case 8:
                    return checkForTwo(8,6,7) || checkForTwo(8,2,5) || checkForTwo(8,0,4);
                default:
                    return false;
            }
        } return false;
    }

    public void computerTurnEasy() {
        if (!isTwoInARow(lastComputerMove)) {
            int bound = emptyButtons.size();
            int n = random.nextInt(bound);
            computerMove = emptyButtons.get(n);
        }
        buttons.get(computerMove).fire();
    }

    public void computerTurnImpossible() {
        if (isTwoInARow(lastComputerMove)) { //win if possible
            buttons.get(computerMove).fire();
        } else if (isTwoInARow(lastPlayerMove)) { //block opponent
            buttons.get(computerMove).fire();
        } else if (!buttons.get(4).isDisabled()) { //middle
            buttons.get(4).fire();
        } else if (X.equals(buttons.get(0).getId()) && X.equals(buttons.get(8).getId())) { //block opponent's fork
            buttons.get(1).fire();
        } else if (X.equals(buttons.get(2).getId()) && X.equals(buttons.get(6).getId())) {
            buttons.get(1).fire();
        } else if (X.equals(buttons.get(0).getId()) && !buttons.get(8).isDisabled()) { //opposite corner
            buttons.get(8).fire();
        } else if (X.equals(buttons.get(2).getId()) && !buttons.get(6).isDisabled()) {
                buttons.get(6).fire();
        } else if (X.equals(buttons.get(6).getId()) && !buttons.get(2).isDisabled()) {
            buttons.get(2).fire();
        } else if (X.equals(buttons.get(8).getId()) && !buttons.get(0).isDisabled()) {
            buttons.get(0).fire();
        } else if (!buttons.get(0).isDisabled()) { //any corner
            buttons.get(0).fire();
        } else if (!buttons.get(2).isDisabled()) {
            buttons.get(2).fire();
        } else if (!buttons.get(6).isDisabled()) {
            buttons.get(6).fire();
        } else if (!buttons.get(8).isDisabled()) {
            buttons.get(8).fire();
        } else if (!buttons.get(1).isDisabled()) { //any side middle
            buttons.get(1).fire();
        } else if (!buttons.get(3).isDisabled()) {
            buttons.get(3).fire();
        } else if (!buttons.get(5).isDisabled()) {
            buttons.get(5).fire();
        } else if (!buttons.get(7).isDisabled()) {
            buttons.get(7).fire();
        } else {
            int bound = emptyButtons.size();
            int n = random.nextInt(bound);
            computerMove = emptyButtons.get(n);
            buttons.get(computerMove).fire();
        }
    }

    public void computerTurnHard() {
        if (isTwoInARow(lastComputerMove)) {
            buttons.get(computerMove).fire();
        } else if (isTwoInARow(lastPlayerMove)) {
            buttons.get(computerMove).fire();
        } else if (!buttons.get(4).isDisabled()) {
            buttons.get(4).fire();
        } else if (X.equals(buttons.get(0).getId()) && !buttons.get(8).isDisabled()) {
            buttons.get(8).fire();
        } else if (X.equals(buttons.get(2).getId()) && !buttons.get(6).isDisabled()) {
            buttons.get(6).fire();
        } else if (X.equals(buttons.get(6).getId()) && !buttons.get(2).isDisabled()) {
            buttons.get(2).fire();
        } else if (X.equals(buttons.get(8).getId()) && !buttons.get(0).isDisabled()) {
            buttons.get(0).fire();
        } else if (!buttons.get(0).isDisabled()) {
            buttons.get(0).fire();
        } else if (!buttons.get(2).isDisabled()) {
            buttons.get(2).fire();
        } else if (!buttons.get(6).isDisabled()) {
            buttons.get(6).fire();
        } else if (!buttons.get(8).isDisabled()) {
            buttons.get(8).fire();
        } else if (!buttons.get(1).isDisabled()) {
            buttons.get(1).fire();
        } else if (!buttons.get(3).isDisabled()) {
            buttons.get(3).fire();
        } else if (!buttons.get(5).isDisabled()) {
            buttons.get(5).fire();
        } else if (!buttons.get(7).isDisabled()) {
            buttons.get(7).fire();
        } else {
            int bound = emptyButtons.size();
            int n = random.nextInt(bound);
            computerMove = emptyButtons.get(n);
            buttons.get(computerMove).fire();}
    }

    public boolean isBoardFull() {
        return emptyButtons.isEmpty();
    }

    public void fadeLine (Line line) {
        FadeTransition fadeTransitionLine = new FadeTransition(Duration.millis(500), line);
        fadeTransitionLine.setFromValue(0.0);
        fadeTransitionLine.setToValue(0.9);
        fadeTransitionLine.play();
    }

    public void fadeButton (Button button) {
        FadeTransition fadeTransitionButton = new FadeTransition(Duration.millis(500), button);
        fadeTransitionButton.setFromValue(0.0);
        fadeTransitionButton.setToValue(0.7);
        fadeTransitionButton.play();
    }

    public void drawLine(int @NotNull ... table) {
        if (table[0] == 0) {
            if (table[1] == 1) {
                pane.getChildren().add(line012);
                fadeLine(line012);
            } else if (table[1] == 3) {
                pane.getChildren().add(line036);
                fadeLine(line036);
            } else if (table[1] == 4) {
                pane.getChildren().add(line048);
                fadeLine(line048);
            }
        } else if (table[0] == 1) {
            pane.getChildren().add(line147);
            fadeLine(line147);
        } else if (table[0] == 2) {
            if (table[1] == 5) {
                pane.getChildren().add(line258);
                fadeLine(line258);
            } else if (table[1] == 4) {
                pane.getChildren().add(line246);
                fadeLine(line246);
            }
        } else if (table[0] == 3) {
            pane.getChildren().add(line345);
            fadeLine(line345);
        } else {
            pane.getChildren().add(line678);
            fadeLine(line678);
        }
    }

    public void setEmptyButtons() {
        for(int i=0; i<9; i++) {
            Integer temp = i;
            emptyButtons.add(temp);
        }
    }

    public void disableBoard() {
        for(Button button:buttons) {
            button.setDisable(true);
        }
    }

    public void welcomeDialog() {
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> username = name);

        if (username != null) {
            hello.setText("Hello " + username + "!");
        } else {
            hello.setText("Hello!");
        }
    }

    public void saveResult() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ranking));
            oos.writeObject(resultMap);
            oos.close();
        } catch (Exception e) {
            System.out.println("wystąpił błąd: " + e);
        }
    }

    public void loadResult() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ranking));
            Map<String,String> readMap = (HashMap<String,String>) ois.readObject();
                resultMap.putAll(readMap);
            ois.close();
        } catch (Exception e) {
            System.out.println("wystąpił błąd: " + e);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        Collections.addAll(lines,line048, line246, line012, line345, line678, line036, line147, line258);
        Collections.addAll(controlButtons,newGame, saveGame, resetScore);
        Collections.addAll(difficultyButtons, easy, hard, impossible);

        label.setTextFill(Color.rgb(190, 200, 77, 1.0));
        label.setFont(new Font("Arial Rounded MT Bold", 22));
        label.setPadding(new Insets(0.0, 0.0, 70.0, 20.0));

        hello.setTextFill(Color.rgb(190, 200, 77, 1.0));
        hello.setFont(new Font("Arial Rounded MT Bold", 22));
        hello.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));

        scoreBoard.setTextFill(Color.WHITE);
        scoreBoard.setFont(new Font("Arial Rounded MT Bold", 18));
        scoreBoard.setText("SCORE:\n\nComputer: " + computerScore + "\nPlayer: " + playerScore);
        scoreBoard.setPadding(new Insets(0.0, 15.0, 15.0, 20.0));

        chooseDifficulty.setTextFill(Color.WHITE);
        chooseDifficulty.setFont(new Font("Arial Rounded MT Bold", 18));
        chooseDifficulty.setPadding(new Insets(0.0, 5.0, 50.0, 20.0));

        for (Button controlButton:controlButtons) {
            controlButton.setTextFill(Color.WHITE);
            controlButton.setBackground(null);
            controlButton.setOnMouseEntered(event -> {
                controlButton.setTextFill(Color.rgb(39, 142, 59, 1.0));
                controlButton.setBorder(new Border(new BorderStroke(Color.rgb(39, 142, 59, 1.0), BorderStrokeStyle.SOLID, new CornerRadii(10.0), new BorderWidths(2.0,2.0,2.0,2.0))));
            });
            controlButton.setOnMouseExited(event -> {
                controlButton.setTextFill(Color.WHITE);
                controlButton.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10.0), new BorderWidths(2.0,2.0,2.0,2.0))));
            });
            controlButton.setFont(new Font("Arial Rounded MT Bold", 20));
            controlButton.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10.0), new BorderWidths(2.0,2.0,2.0,2.0))));
            HBox.setMargin(controlButton, new Insets(5.0, 5.0, 5.0, 10.0));
        }

        newGame.setOnAction(event -> {
            for (Button button:buttons) {
                button.setGraphic(null);
                button.setDisable(false);
                button.setId("");
            }
            pane.getChildren().removeAll(lines);
            emptyButtons.clear();
            setEmptyButtons();
            isTurnX = true;
            label.setText("");
        });

        resetScore.setOnAction(event -> {
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                username = result.get();
                hello.setText("Hello " + username + "!");
                computerScore = 0;
                playerScore = 0;
                scoreBoard.setText("SCORE:\n\nComputer: " + computerScore + "\nPlayer: " + playerScore);
                resultMap.put(username + " vs. Computer", playerScore + ":" + computerScore);
                saveResult();
                newGame.fire();
            }
        });

        board.setOpacity(0.5);

        for (RadioButton radioButton:difficultyButtons) {
            radioButton.setToggleGroup(toggleDifficulty);
            radioButton.setBackground(null);
            radioButton.setTextFill(Color.WHITE);
            radioButton.setFont(new Font("Arial Rounded MT Bold", 16));
            radioButton.getStyleClass().add("radio-button");
            VBox.setMargin(radioButton, new Insets(10.0, 5.0, 5.0, 5.0));
        }

        easy.setOnMouseClicked(event -> difficulty = 1);
        hard.setOnMouseClicked(event -> difficulty = 2);
        impossible.setOnMouseClicked(event -> difficulty = 3);

        easy.setSelected(true);

        sideButtonBar.getChildren().addAll(difficultyButtons);
        sideButtonBar.setPadding(new Insets(0.0, 0.0, 0.0, 20.0));

        topButtonBar.getChildren().addAll(controlButtons);
        topButtonBar.setPadding(new Insets(20.0, 5.0, 5.0, 5.0));

        loadResult();

        grid.setAlignment(Pos.TOP_CENTER);
        grid.setGridLinesVisible(false);
        grid.setBackground(background);
        //grid.setPadding(new Insets(60.0, 0.0, 0.0, 0.0));
        grid.setAlignment(Pos.CENTER);
        grid.add(pane, 0, 1, 3, 3);
        grid.add(board, 0,1,3,3);
        grid.add(label, 3, 1, 1, 1);
        grid.add(hello, 0, 0, 4, 1);
        grid.add(chooseDifficulty, 3, 1,1,2);
        grid.add(scoreBoard,3,3, 1, 1);
        grid.add(sideButtonBar, 3,2, 1, 2);
        grid.add(topButtonBar,0,4, 4, 1);
        grid.add(resultList, 0, 5, 4, 1);
        grid.getStylesheets().add("radio-button.css");

        setEmptyButtons();

        for (Line line:lines) {
            line.setStroke(Color.WHITESMOKE);
            line.setStrokeWidth(5.0);
            line.setOpacity(0.8);
            line.setStrokeLineCap(StrokeLineCap.ROUND);
        }

        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();
                button.setBackground(null);
                button.setOpacity(0.7);
                button.setPrefSize(130.0, 130.0);

                FadeTransition fadeButton = new FadeTransition(Duration.millis(500), button);
                fadeButton.setFromValue(0.0);
                fadeButton.setToValue(0.7);

                button.setOnAction(event -> {
                    if (isTurnX) {
                        button.setGraphic(new ImageView(cross));
                        button.setId(X);
                        lastMove = lastPlayerMove = buttons.indexOf(button);
                        emptyButtons.remove((Integer) lastMove);
                        button.setDisable(true);
                        if (isThreeInARow()) {
                            label.setText(WIN);
                            playerScore++;
                            scoreBoard.setText("SCORE:\n\nComputer: " + computerScore + "\nPlayer: " + playerScore);
                            drawLine(winningLine);
                            for (Button button1:buttons) {
                                button1.setDisable(true);
                            }
                        } else {
                            if (isBoardFull()) {
                                label.setText("It's a draw!");
                                disableBoard();
                            } else {
                                isTurnX = !isTurnX;
                                if (difficulty == 1) {
                                    computerTurnEasy();
                                } else if (difficulty == 2) {
                                    computerTurnHard();
                                } else {
                                    computerTurnImpossible();
                                }
                            }
                        }
                    } else {
                        fadeButton(button);
                        button.setGraphic(new ImageView(circle));
                        button.setId(O);
                        lastMove = lastComputerMove = buttons.indexOf(button);
                        emptyButtons.remove((Integer) lastMove);
                        button.setDisable(true);
                        if (isThreeInARow()) {
                            label.setText(LOSE);
                            computerScore++;
                            scoreBoard.setText("SCORE:\n\nComputer: " + computerScore + "\nPlayer: " + playerScore);
                            drawLine(winningLine);
                            disableBoard();
                        } else {
                            isTurnX = !isTurnX;
                        }
                    }
                });

                buttons.add(button);
                grid.add(button, j, i);
            }
        }

        Scene scene = new Scene(grid, 640, 720, Color.GREEN);

        dialog.setTitle("Tic-Tac-Toe");
        dialog.setGraphic(new ImageView("file:src/main/resources/logo_mini_mini.png"));
        dialog.setContentText("Please enter your name:");
        dialog.setHeaderText("");

        welcomeDialog();

        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
