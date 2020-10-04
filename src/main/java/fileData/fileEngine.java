package fileData;

import containers.Move;
import db.DatabaseEngine;
import exceptions.fileException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class fileEngine implements DatabaseEngine {

    @SuppressWarnings("FieldCanBeLocal")
    private final String FILENAME = "savedGame";

    @Override
    public void createNewGame(int player1, int player2) {
        try {
            clearFile();
            insertPlayers(player1, player2);
        } catch (IOException e) {
            throw new fileException();
        }
    }

    @Override
    public void insertMove(Move move) {
        try {
            File file = getFile();
            FileWriter fileWriter = getFileWriter(file, true);
            int x = move.getLeftRight();
            int y = move.getUpDown();
            fileWriter.append("\n").append(String.valueOf(x)).append(" ").append(String.valueOf(y));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new fileException();
        }
    }

    @Override
    public void readMoves(MoveListBuilder builder) {
        try {
            File file = getFile();
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            List<String> moves = lines.subList(1, lines.size());
            for (String moveLine : moves) {
                String[] move = moveLine.split(" ");
                builder.buildMove(Integer.parseInt(move[0]), Integer.parseInt(move[1]));
            }
        } catch (IOException e) {
            throw new fileException();
        }
    }

    @Override
    public void readPlayers(PlayersBuilder builder) {
        try {
            File file = getFile();
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            String[] players = lines.get(0).split(" ");
            builder.buildPlayers(Integer.parseInt(players[0]));
            builder.buildPlayers(Integer.parseInt(players[1]));
        } catch (IOException e) {
            throw new fileException();
        }
    }

    @Override
    public boolean isGameSaved() {
        return getFile().length() != 0;
    }

    private File getFile() {
        return new File(FILENAME);
    }

    private FileWriter getFileWriter(File file, boolean append) throws IOException {
        return new FileWriter(file, append);
    }

    private void insertPlayers(int player1, int player2) throws IOException {
        File file = getFile();
        FileWriter fileWriter = getFileWriter(file, true);
        fileWriter.append(String.valueOf(player1)).append(" ").append(String.valueOf(player2));
        fileWriter.flush();
        fileWriter.close();
    }

    private void clearFile() throws IOException {
        File file = getFile();
        FileWriter fileWriter = getFileWriter(file, false);
        fileWriter.flush();
        fileWriter.close();
    }
}
