package com.example.view.board;

import com.example.biz.board.BoardService;
import com.example.biz.board.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
@SessionAttributes("board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @ModelAttribute("conditionMap")
    public Map<String, String> searchConditionMap() {
        Map<String, String> conditionMap = new HashMap<String, String>();
        conditionMap.put("제목", "TITLE");
        conditionMap.put("내용", "CONTENT");

        return conditionMap;
    }


    @RequestMapping(value = "/insertBoard.do")
    public String insertBoard(BoardVO vo) throws IOException {

        MultipartFile uploadFile = vo.getUploadFile();
        if (!uploadFile.isEmpty()) {
            String fileName = uploadFile.getOriginalFilename();
            uploadFile.transferTo(new File("D:/" + fileName));
        }

        boardService.insertBoard(vo);
        return "redirect:getBoardList.do";

    }

    @RequestMapping("/updateBoard.do")
    public String updateBoard(@ModelAttribute("board") BoardVO vo) {
        System.out.println("작성자 : " + vo.getWriter());
        boardService.updateBoard(vo);
        return "getBoardList.do";
    }

    @RequestMapping("/deleteBoard.do")
    public String deleteBoard(BoardVO vo) {
        boardService.deleteBoard(vo);
        return "getBoardList.do";
    }

    @RequestMapping("/getBoard.do")
    public String getBoard(BoardVO vo, Model model) {
        model.addAttribute("board", boardService.getBoard(vo));
        return "getBoard.jsp";
    }

    @RequestMapping("/getBoardList.do")
    public String getBoardList(BoardVO vo, Model model) {

        if (vo.getSearchCondition() == null) {
            vo.setSearchCondition("TITLE");

        }

        if (vo.getSearchKeyword() == null) {
            vo.setSearchKeyword("");
        }

        model.addAttribute("boardList", boardService.getBoardList(vo));
        return "getBoardList.jsp";
    }
}
