package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.MoveTransition;
import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.chess.gui.Table.BoardDirection.STANDARD;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private Board chessBoard;
    private Tile sourceTile;
    private Tile destTile;
    private Piece userMovedPiece;
    private BoardDirection boardDirection;
    private boolean highlightLegalMoves;

    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension (600, 600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension PANEL_DIMENSION = new Dimension(10,10);

    public Table() {
        this.gameFrame = new JFrame("KotlinToJavaChess");
        final JMenuBar menuBar = createMenuBar();
        this.gameFrame.setJMenuBar(menuBar);
        this.highlightLegalMoves = false;
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setVisible(true);
        this.gameFrame.setLayout(new BorderLayout());
        this.boardDirection = STANDARD;
        this.chessBoard = Board.createStandartBoard();
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
    }

    private JMenuBar createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createPreferencesMenu());
        return menuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("opened PGN");
            }
        });
        fileMenu.add(openPGN);

        final JMenuItem exitMenu = new JMenuItem("Exit");
        exitMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenu);
        return fileMenu;
    }

    private JMenu createPreferencesMenu() {
        final JMenu preferencesMenu = new JMenu("Preferences");

        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
        flipBoardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardDirection = boardDirection.flip();
                boardPanel.drawBoard(chessBoard);
            }
        });
        preferencesMenu.add(flipBoardMenuItem);

        final JCheckBoxMenuItem legalMoveHighlighterCheckBox =
                new JCheckBoxMenuItem("Highlight Possible Moves", false);
        legalMoveHighlighterCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                highlightLegalMoves = legalMoveHighlighterCheckBox.isSelected();
            }
        });
        preferencesMenu.add(legalMoveHighlighterCheckBox);
        return preferencesMenu;
    }

    public enum BoardDirection {
        STANDARD {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return boardTiles;
            }

            @Override
            BoardDirection flip() {
                return FLIPPED;
            }
        },

        FLIPPED {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            @Override
            BoardDirection flip() {
                return STANDARD;
            }
        };

        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
        abstract BoardDirection flip();
    }

    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard(final Board board) {
            removeAll();
            for (final TilePanel tilePanel: boardDirection.traverse(boardTiles)) {
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    private class TilePanel extends JPanel {
        private final int tileID;

        TilePanel(final BoardPanel boardPanel, final int tileID) {
            super(new GridBagLayout());
            this.tileID = tileID;
            setPreferredSize(PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {

                    if(isRightMouseButton(e)) {

                        sourceTile = null;
                        destTile = null;
                        userMovedPiece = null;

                    } else if (isLeftMouseButton(e)) {
                        if (sourceTile == null) {
                            sourceTile = chessBoard.getTile(tileID);
                            userMovedPiece = sourceTile.getPiece();
                            if (!sourceTile.isOccupied()) sourceTile = null;
                        } else {
                            destTile = chessBoard.getTile(tileID);
                            final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoord(), destTile.getTileCoord());
                            final MoveTransition trans = chessBoard.currentPlayer().makeMove(move);
                            if (trans.getStatus().isDone())
                                chessBoard = trans.getTransitionBoard(); //add move to MoveLog todo
                            sourceTile = null;
                            destTile = null;
                            userMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                boardPanel.drawBoard(chessBoard);
                            }
                        });
                    }

                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });
            validate();
        }

        private void assignTilePieceIcon (final Board board) {
            this.removeAll();
            if (board.getTile(this.tileID).isOccupied()) {
                try {
                    final BufferedImage image = ImageIO.read(new File("chessPieces/art/" +
                                    board.getTile(this.tileID).getPiece().getTeam().toString().substring(0, 1) +
                                    board.getTile(this.tileID).getPiece().toString() + ".png"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void assignTileColor() {
            if (BoardUtils.EIGHTH_RANK[this.tileID] ||
                BoardUtils.SIXTH_RANK[this.tileID] ||
                    BoardUtils.FOURTH_RANK[this.tileID] ||
                    BoardUtils.SECOND_RANK[this.tileID]) {
                setBackground(this.tileID % 2 == 0 ? Color.lightGray : Color.darkGray);
            } else if (BoardUtils.SEVENTH_RANK[this.tileID] ||
                    BoardUtils.FIFTH_RANK[this.tileID] ||
                    BoardUtils.THIRD_RANK[this.tileID] ||
                    BoardUtils.FIRST_RANK[this.tileID]) {
                setBackground(this.tileID % 2 == 0 ? Color.darkGray : Color.lightGray);
            }
        }

        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            validate();
            repaint();
        }

        private void highlightLegalMoves(final Board board) {
            if (true) {
                for (final Move move: pieceLegalMoves(board)) {
                    if (move.getDest() == this.tileID) {
                        try {
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("chessPieces/art/gold.png")))));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }



    private Collection<Move> pieceLegalMoves(final Board board) {
        if (userMovedPiece != null && userMovedPiece.getTeam() == board.currentPlayer().getTeam()) {
            return userMovedPiece.calcMoves(board);
        }
        return Collections.emptyList();
    }
}
