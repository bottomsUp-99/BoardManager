package service;

import dto.Board;

import java.sql.SQLException;
import java.util.List;

public interface BoardService {
  List<Board> list() throws SQLException;
  Board read(int bno) throws SQLException;
  void create(Board board) throws SQLException;
  void update(Board board) throws SQLException;
  void delete(int bno) throws SQLException;
  void clear() throws SQLException;
  void close();
}
