package service;

import lib.ConnectionManager;
import dto.Board;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO extends BoardServiceImpl {

  public BoardDAO() {
    super(ConnectionManager.getInstance().getConnection());
  }

  @Override
  public List<Board> list() throws SQLException {
    List<Board> boards = new ArrayList<>();
    String sql = "SELECT bno, btitle, bcontent, bwriter, bdate FROM boardlist ORDER BY bno DESC";
    try (PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {
      while (rs.next()) {
        Board board = new Board();
        board.setBno(rs.getInt("bno"));
        board.setBtitle(rs.getString("btitle"));
        board.setBcontent(rs.getString("bcontent"));
        board.setBwriter(rs.getString("bwriter"));
        board.setBdate(rs.getDate("bdate"));
        boards.add(board);
      }
    }
    return boards;
  }

  @Override
  public Board read(int bno) throws SQLException {
    Board board = null;
    String sql = "SELECT bno, btitle, bcontent, bwriter, bdate FROM boardlist WHERE bno=?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setInt(1, bno);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          board = new Board();
          board.setBno(rs.getInt("bno"));
          board.setBtitle(rs.getString("btitle"));
          board.setBcontent(rs.getString("bcontent"));
          board.setBwriter(rs.getString("bwriter"));
          board.setBdate(rs.getDate("bdate"));
        }
      }
    }
    return board;
  }

  @Override
  public void create(Board board) throws SQLException {
    String sql = "INSERT INTO boardlist (btitle, bcontent, bwriter, bdate) VALUES (?, ?, ?, now())";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setString(1, board.getBtitle());
      pstmt.setString(2, board.getBcontent());
      pstmt.setString(3, board.getBwriter());
      pstmt.executeUpdate();
    }
  }

  @Override
  public void update(Board board) throws SQLException {
    String sql = "UPDATE boardlist SET btitle=?, bcontent=?, bwriter=? WHERE bno=?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setString(1, board.getBtitle());
      pstmt.setString(2, board.getBcontent());
      pstmt.setString(3, board.getBwriter());
      pstmt.setInt(4, board.getBno());
      pstmt.executeUpdate();
    }
  }

  @Override
  public void delete(int bno) throws SQLException {
    String sql = "DELETE FROM boardlist WHERE bno=?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setInt(1, bno);
      pstmt.executeUpdate();
    }
  }

  @Override
  public void clear() throws SQLException {
    String sql = "TRUNCATE TABLE boardlist";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.executeUpdate();
    }
  }

  @Override
  public void close() {
    ConnectionManager.getInstance().closeConnection();
  }
}
