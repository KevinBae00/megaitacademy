//package com.algoda.Word.model;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class Wordcdao {
//
//    private Connection conn = null;
//    private PreparedStatement stmt = null;
//    private ResultSet rs = null;
//
//    private final String WORDC_INSERT = "insert into  wordc(" +
//            "word, MEANINGA, MEANINGB, MEANINGC, SIMILIAR_WORDA, SIMILIAR_WORDB, SIMILIAR_WORDC, day)\n" +
//            "values (?,?,?,?,?,?,?,?)";
//    private final String WORDC_UPDATE = "update wordc " +
//            "set  MEANINGA=?, MEANINGB=?, MEANINGC=?, " +
//            "SIMILIAR_WORDA=?, SIMILIAR_WORDB=?, SIMILIAR_WORDC=?, day=? " +
//            "where word = ?";
//    private final String WORDC_DELETE = "delete wordc where word=?";
//    private final String WORDC_GET = "select * from wordc where word=?";
//    private final String WORDC_LIST = "select * from wordc where day=?";
//
//    //    글 등록
//    public void insertwordc(Worddto dto) {
//        System.out.println("===> JDBC로 insertwordc() 기능 처리");
//
//        try {
//            conn = JDBCUtil.getConnection();
//            stmt = conn.prepareStatement(WORDC_INSERT);
//            stmt.setString(1, dto.getWord());
//            stmt.setString(2, dto.getMEANINGA());
//            stmt.setString(3, dto.getMEANINGB());
//            stmt.setString(4, dto.getMEANINGC());
//            stmt.setString(5, dto.getSIMILIAR_WORDA());
//            stmt.setString(6, dto.getSIMILIAR_WORDB());
//            stmt.setString(7, dto.getSIMILIAR_WORDC());
//            stmt.setInt(8, dto.getDay());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            JDBCUtil.close(stmt, conn);
//        }
//    }
//
//    //    글 수정
//    public void updatewordc(Worddto dto) {
//        System.out.println("===> JDBC로 updatewordc() 기능 처리");
//
//        try {
//            conn = JDBCUtil.getConnection();
//            stmt = conn.prepareStatement(WORDC_UPDATE);
//            stmt.setString(1, dto.getMEANINGA());
//            stmt.setString(2, dto.getMEANINGB());
//            stmt.setString(3, dto.getMEANINGC());
//            stmt.setString(4, dto.getSIMILIAR_WORDA());
//            stmt.setString(5, dto.getSIMILIAR_WORDB());
//            stmt.setString(6, dto.getSIMILIAR_WORDC());
//            stmt.setInt(7, dto.getDay());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            JDBCUtil.close(stmt, conn);
//        }
//    }
//
//    //    글 삭제
//    public void deletewordc(Worddto dto) {
//        System.out.println("===> JDBC로 deletewordc() 기능 처리");
//
//        try {
//            conn = JDBCUtil.getConnection();
//            stmt = conn.prepareStatement(WORDC_DELETE);
//            stmt.setString(1, dto.getWord());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            JDBCUtil.close(stmt, conn);
//        }
//    }
//
//    //    글 상세 조회
//    public Worddto getwordc(Worddto dto) {
//        System.out.println("===> JDBC로 getwordc() 기능 처리");
//
//        Worddto worddto = null;
//        try {
//            conn = JDBCUtil.getConnection();
//            stmt = conn.prepareStatement(WORDC_GET);
//            stmt.setString(1, dto.getWord());
//            rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                worddto = new Worddto();
//                worddto.setWord(rs.getString("word"));
//                worddto.setMEANINGA(rs.getString("MEANINGA"));
//                worddto.setMEANINGB(rs.getString("MEANINGB"));
//                worddto.setMEANINGC(rs.getString("MEANINGC"));
//                worddto.setSIMILIAR_WORDA(rs.getString("SIMILIAR_WORDA"));
//                worddto.setSIMILIAR_WORDB(rs.getString("SIMILIAR_WORDB"));
//                worddto.setSIMILIAR_WORDC(rs.getString("SIMILIAR_WORDC"));
//                worddto.setDay(rs.getInt("day"));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            JDBCUtil.close(rs, stmt, conn);
//        }
//        return worddto;
//    }
//
//    //    글 목록 조회
//    public List<Worddto> getwordcList(Worddto dto) {
//        System.out.println("===> JDBC로 getwordcList() 기능 처리");
//
//        List<Worddto> wordcList = new ArrayList<Worddto>();
//        try {
//            conn = JDBCUtil.getConnection();
//            stmt = conn.prepareStatement(WORDC_LIST);
//            stmt.setInt(1, dto.getDay());
//            rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                Worddto worddto = new Worddto();
//                worddto.setWord(rs.getString("word"));
//                worddto.setMEANINGA(rs.getString("MEANINGA"));
//                worddto.setMEANINGB(rs.getString("MEANINGB"));
//                worddto.setMEANINGC(rs.getString("MEANINGC"));
//                worddto.setSIMILIAR_WORDA(rs.getString("SIMILIAR_WORDA"));
//                worddto.setSIMILIAR_WORDB(rs.getString("SIMILIAR_WORDB"));
//                worddto.setSIMILIAR_WORDC(rs.getString("SIMILIAR_WORDC"));
//                worddto.setDay(rs.getInt("day"));
//                wordcList.add(worddto);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            JDBCUtil.close(rs, stmt, conn);
//        }
//        return wordcList;
//    }
//}