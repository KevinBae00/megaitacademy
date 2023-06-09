package model1;

import common.JDBCConnect;

import javax.servlet.ServletContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardDAO extends JDBCConnect {
    // 데이터베이스 연결 및 쿼리문 실행을 위한 메소드 작성

    public BoardDAO(ServletContext application) {
        super(application);
        // null exception이 뜰 경우 web.xml 문제일 수 있다. db관련 내용을 넣었는지 확인해라
    }

    //    겁색 조건에 맞는 게시물의 개수를 반환합니다.
    public int selectCount(Map<String, Object> map) {
        int totalCount = 0;
        String query = "select count(*) from board";
        if (map.get("searchWord") != null) {
            query += " where " + map.get("searchField") + " Like '%" + map.get("searchWord") + "%'";
        }
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            rs.next();
            totalCount = rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalCount;
    }
//    검색 조건에 맞는 게시물 목록을 반환합니다.

    public List<BoardDTO> selectList(Map<String, Object> map) {

        List<BoardDTO> bbs = new ArrayList<BoardDTO>();

        String query = "select * from board";
        if (map.get("searchWord") != null) {
            query += " WHERE " + map.get("searchField") + " "
                    + " LIKE '%" + map.get("searchWord") + "%' ";
        }
        query += " order by num desc";

        try {

            System.out.println(query);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {

                BoardDTO dto = new BoardDTO();

                dto.setNum(rs.getString("num"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setPostdate(rs.getDate("postdate"));
                dto.setId(rs.getString("id"));
                dto.setVisitcount(rs.getString("visitcount"));

                bbs.add(dto);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bbs;

    }

    //    검색 조건에 맞는 게시물 목록을 반화합니다.(페이징 기능 지원).
    public List<BoardDTO> selectListPage(Map<String, Object> map) {
        List<BoardDTO> bbs = new ArrayList<BoardDTO>();
        String query = "select *\n" +
                "from (select ROWNUM rnum, tb.*\n" +
                "      from (select *\n" +
                "            from BOARD\n";

        if (map.get("searchWord") != null) {
            query += " WHERE " + map.get("searchField") + " "
                    + " LIKE '%" + map.get("searchWord") + "%' ";
        }

        query += "            order by NUM desc) tb)\n" +
                "where rnum>=? and rnum<=?";

        try {
            psmt = con.prepareStatement(query);
            psmt.setString(1, map.get("start").toString());
            psmt.setString(2, map.get("end").toString());

            rs = psmt.executeQuery();

            while (rs.next()) {
                BoardDTO dto = new BoardDTO();

                dto.setNum(rs.getString("num"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setPostdate(rs.getDate("postdate"));
                dto.setId(rs.getString("id"));
                dto.setVisitcount(rs.getString("visitcount"));

                bbs.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bbs;
    }

    //    게시글 데이터를 받아 DB에 추가합니다.
    public int insertWrite(BoardDTO dto) {

        int result = 0;

        String query = "insert into board (num,title,content,id,visitcount)"
                + " values (seq_board_num.NEXTVAL,?,?,?,0)";

        try {
            psmt = con.prepareStatement(query);
            psmt.setString(1, dto.getTitle());
            psmt.setString(2, dto.getContent());
            psmt.setString(3, dto.getId());

            result = psmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    //    지정한 게시물을 찾아 내용을 반환합니다.
    public BoardDTO selectView(String num) {
        BoardDTO dto = new BoardDTO();

        String query = "select B.*,m.NAME" +
                " from MEMBER m inner join BOARD B" +
                " on m.ID = B.ID" +
                " where NUM = ?";

        try {
            psmt = con.prepareStatement(query);
            psmt.setString(1, num);
            rs = psmt.executeQuery();

            if (rs.next()) {
                dto.setNum(rs.getString(1)); // 실제 컴럼 이름이 없을 경우(count(*)같이) 이런 경우 쓸 수 있다. 이번 경우는 이름으로 써도 무방
                dto.setTitle(rs.getString(2));
                dto.setContent(rs.getString("content"));
                dto.setPostdate(rs.getDate("postdate"));
                dto.setId(rs.getString("id"));
                dto.setVisitcount(rs.getString(6));
                dto.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dto;
    }

    //    지정한 게시물의 조회수를 1 증가시킵니다.
    public void updateVisitCount(String num) {
        String query = "update BOARD" +
                " set VISITCOUNT = VISITCOUNT + 1" +
                " where NUM = ?";

        try {
            psmt = con.prepareStatement(query);
            psmt.setString(1, num);
            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //    지정한 게시물을 수정합니다.
    public int updateEdit(BoardDTO dto) {
        int result = 0;

        String query = "update BOARD\n" +
                "set TITLE = ?, content = ?\n" +
                "where NUM=?";

        try {
            psmt = con.prepareStatement(query);
            psmt.setString(1, dto.getTitle());
            psmt.setString(2, dto.getContent());
            psmt.setString(3, dto.getNum());

            result = psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    //    지정한 게시물을 삭제합니다.
    public int deletePost(String num) {
        int result = 0;
        String query = "delete from BOARD\n" +
                "where NUM=?";

        try {
            psmt = con.prepareStatement(query);
            psmt.setString(1, num);

            result = psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
