package service;

import dto.Board;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BoardServiceImpl implements BoardService{
  protected final Connection connection;

  protected BoardServiceImpl(Connection connection) {
    this.connection = connection;
  }

  @Override
  public abstract List<Board> list() throws SQLException;

  @Override
  public abstract Board read(int bno) throws SQLException;

  @Override
  public abstract void create(Board board) throws SQLException;

  @Override
  public abstract void update(Board board) throws SQLException;

  @Override
  public abstract void delete(int bno) throws SQLException;

  @Override
  public abstract void clear() throws SQLException;

  @Override
  public void close() {
  }
}