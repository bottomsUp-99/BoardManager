import dto.Board;
import service.BoardDAO;
import service.BoardService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Menu {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private static final BoardService boardService = new BoardDAO();

  public static void main(String[] args) throws IOException, SQLException {
    mainMenu();
  }

  private static void mainMenu() throws IOException, SQLException {
    while (true) {
      System.out.println();
      System.out.println("-------------------------------------------------------------------");
      System.out.println("메인 메뉴: 1.Create | 2.Read | 3.List | 4.Update | 5.Delete | 6.Clear | 7.Exit");
      System.out.print("메뉴 선택: ");
      String menuNo = br.readLine();
      System.out.println();
      switch (menuNo) {
        case "1" -> create();
        case "2" -> read();
        case "3" -> list();
        case "4" -> {
          System.out.print("수정할 게시물 번호: ");
          int bno = Integer.parseInt(br.readLine());
          update(boardService.read(bno));
        }
        case "5" -> {
          System.out.print("삭제할 게시물 번호: ");
          int bno = Integer.parseInt(br.readLine());
          delete(bno);
        }
        case "6" -> clear();
        case "7" -> {
          exit();
          return;
        }
      }
    }
  }

  private static void list() throws SQLException {
    System.out.println("[게시물 목록]");
    System.out.println("-------------------------------------------------------------------");
    System.out.printf("%-6s%-12s%-16s%-40s\n", "no", "writer", "date", "title");
    System.out.println("-------------------------------------------------------------------");

    for (Board board : boardService.list()) {
      System.out.printf("%-6s%-12s%-16s%-40s \n", board.getBno(), board.getBwriter(),
          board.getBdate(), board.getBtitle());
    }
  }

  private static void read() throws IOException, SQLException {
    System.out.println("[게시물 읽기]");
    System.out.print("bno: ");
    int bno = Integer.parseInt(br.readLine());
    Board board = boardService.read(bno);

    if (board != null) {
      System.out.println("#############");
      System.out.println("번호: " + board.getBno());
      System.out.println("제목: " + board.getBtitle());
      System.out.println("내용: " + board.getBcontent());
      System.out.println("작성자: " + board.getBwriter());
      System.out.println("날짜: " + board.getBdate());
      System.out.println("--------------------------------------------------------------");
      System.out.println("보조 메뉴: 1.Update | 2.Delete | 3.List");
      System.out.print("메뉴 선택: ");
      String menuNo = br.readLine();
      if (menuNo.equals("1")) {
        update(board);
      } else if (menuNo.equals("2")) {
        delete(board.getBno());
      }
    } else {
      System.out.println("해당 번호의 게시물이 존재하지 않습니다.");
    }
  }

  private static void create() throws IOException, SQLException {
    Board board = new Board();
    System.out.println("[새 게시물 입력]");
    System.out.print("제목: ");
    board.setBtitle(br.readLine());
    System.out.print("내용: ");
    board.setBcontent(br.readLine());
    System.out.print("작성자: ");
    board.setBwriter(br.readLine());
    System.out.println("-------------------------------------------------------------------");
    System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
    System.out.print("메뉴 선택: ");
    String menuNo = br.readLine();
    if (menuNo.equals("1")) {
      boardService.create(board);
      System.out.println("게시물이 저장되었습니다.");
    }
  }

  private static void update(Board board) throws IOException, SQLException {
    if (board == null) {
      System.out.println("해당 번호의 게시물이 존재하지 않습니다.");
      return;
    }

    System.out.println("[수정 내용 입력]");
    System.out.print("제목: ");
    board.setBtitle(br.readLine());
    System.out.print("내용: ");
    board.setBcontent(br.readLine());
    System.out.print("작성자: ");
    board.setBwriter(br.readLine());
    System.out.println("-------------------------------------------------------------------");
    System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
    System.out.print("메뉴 선택: ");
    String menuNo = br.readLine();
    if (menuNo.equals("1")) {
      boardService.update(board);
      System.out.println("게시물이 수정되었습니다.");
    }
  }

  private static void delete(int bno) throws SQLException {
    boardService.delete(bno);
    System.out.println("게시물이 삭제되었습니다.");
  }

  private static void clear() throws SQLException, IOException {
    System.out.println("[게시물 전체 삭제]");
    System.out.println("-------------------------------------------------------------------");
    System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
    System.out.print("메뉴 선택: ");
    String menuNo = br.readLine();
    if (menuNo.equals("1")) {
      boardService.clear();
      System.out.println("모든 게시물이 삭제되었습니다.");
    }
  }

  private static void exit() {
    boardService.close();
    System.out.println("** 게시판 종료 **");
  }
}
