package com.gamewerks.blocky.engine;

import java.util.Random;

import com.gamewerks.blocky.util.Constants;
import com.gamewerks.blocky.util.Position;

public class BlockyGame {
    private static final int LOCK_DELAY_LIMIT = 30;
    
    private Board board;
    private Piece activePiece;
    private Direction movement;
    
    private int lockCounter;
    //Array to hold all the pieces in an array
    private PieceKind[] pieces = {PieceKind.I, PieceKind.J, PieceKind.L, PieceKind.O, PieceKind.S, PieceKind.T,PieceKind.Z};
    //int to keep track of which piece to return next
    private int piecesIterator = 7;

    //Return the next piece in line, if all 7 have been returned shuffle and return the first piece in list.
    private PieceKind pickPiece(){
        PieceKind ret;
        if(piecesIterator == pieces.length){
            shuffle(pieces);
            piecesIterator = 0;
            ret = pieces[piecesIterator];
            piecesIterator++;
        } else{
            ret = pieces[piecesIterator];
            piecesIterator++;
        }

        return ret;
    }

    //Completes the Yates Shuffle to shuffle the 7 pieces
    private void shuffle(PieceKind pieces[]){
        //Initialize random number?
        Random random = new Random();
        for(int i = pieces.length - 1; i>0; i--){
            //Get a random number between 0 and i and swap that element with the ith element
            int randomPiece = random.nextInt(i + 1);
            PieceKind temp = pieces[randomPiece];
            pieces[randomPiece] = pieces[i];
            pieces[i] = temp;
        }
    }

    public BlockyGame() {
        board = new Board();
        movement = Direction.NONE;
        lockCounter = 0;
        trySpawnBlock();
    }
    
    private void trySpawnBlock() {
        if (activePiece == null) {
            activePiece = new Piece(pickPiece(), new Position(3, Constants.BOARD_WIDTH / 2 - 2));
            if (board.collides(activePiece)) {
                System.exit(0);
            }
        }
    }
    
    private void processMovement() {
        Position nextPos;
        switch(movement) {
        case NONE:
            nextPos = activePiece.getPosition();
            break;
        case LEFT:
            nextPos = activePiece.getPosition().add(0, -1);
            break;
        case RIGHT:
            nextPos = activePiece.getPosition().add(0, 1);
            break;
        default:
            throw new IllegalStateException("Unrecognized direction: " + movement.name());
        }
        if (!board.collides(activePiece.getLayout(), nextPos)) {
            activePiece.moveTo(nextPos);
        }
    }
    
    private void processGravity() {
        Position nextPos = activePiece.getPosition().add(1, 0);
        if (!board.collides(activePiece.getLayout(), nextPos)) {
            lockCounter = 0;
            activePiece.moveTo(nextPos);
        } else {
            if (lockCounter < LOCK_DELAY_LIMIT) {
                lockCounter += 1;
            } else {
                board.addToWell(activePiece);
                lockCounter = 0;
                activePiece = null;
            }
        }
    }
    
    private void processClearedLines() {
        board.deleteRows(board.getCompletedRows());
    }
    
    public void step() {
        trySpawnBlock();
        processMovement();
        processGravity();

        processClearedLines();
    }
    
    public boolean[][] getWell() {
        return board.getWell();
    }
    
    public Piece getActivePiece() { return activePiece; }
    public void setDirection(Direction movement) { this.movement = movement; }
    public void rotatePiece(boolean dir) { activePiece.rotate(dir); }
}
