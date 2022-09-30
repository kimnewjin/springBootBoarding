package boarding.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


import boarding.dto.BoardingDto;
import boarding.dto.BoardingFileDto;
import boarding.service.BoardingService;


@Controller // 스프링 MVC 컨트롤러를 의미한다. @Controller 어노테이션을 붙혀줌으로 해당 클래스를 컨트롤러로 동작함
public class BoardingController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BoardingService boardService; // BoardService  클래스 생성 후 추가한다.

//비지니스 로직을 처리하는 서비스 빈을 연결한다. 
@RequestMapping("/")
public String goMain() {
	return "redirect:/board/openBoardList.do";
}

@RequestMapping("/board/openBoardWrite.do")
public String openBoardWrite() throws Exception{
	return "/board/boardWrite";
}

@RequestMapping("/board/insertBoard.do")
public String insertBoard(BoardingDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{

boardService.insertBoard(board, multipartHttpServletRequest);

return "redirect:/board/openBoardList.do";
}

@RequestMapping("/board/openBoardDetail.do")
public ModelAndView openBoardDetail(@RequestParam int boardIdx) throws Exception{
ModelAndView mv = new ModelAndView("/board/boardDetail");
BoardingDto board = boardService.selectBoardDetail(boardIdx);
mv.addObject("board",board);
return mv;
}

@RequestMapping("/board/updateBoard.do")
public String updateBoard(BoardingDto board) throws Exception{
	boardService.updateBoard(board);
	return "redirect:/board/openBoardList.do";
}
@RequestMapping("/board/deleteBoard.do")
public String deleteBoard(int boardIdx) throws Exception{
	boardService.deleteBoard(boardIdx);
	return "redirect:/board/openBoardList.do"; 

}

@RequestMapping("/board/downloadBoardFile.do")

public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception{

//HttpServletResponse 객체를 파라미터로 사용한다. 사용자에게 전달할 데이터를 담고 있다.

BoardingFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);// 데이터 베이스에서 선택된 파일 정보를 조회한다.

if(ObjectUtils.isEmpty(boardFile)== false) {

String fileName = boardFile.getOriginalFileName();
byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));

//storedFilePath 값을 이용해 실제 저장되어 있는 파일을 읽어온 후 byte[] 형태로 변환한다

//여기서 사용되는 FileUtils 클래스는 org.apache.commons.io 패키지의 FileUtils 클래스 이다.
response.setContentType("application/octet-stream");

response.setContentLength(files.length);

response.setHeader("Content-Disposition", "attachment; fileName=\""+ URLEncoder.encode(fileName,"UTF-8")+"\";");

response.setHeader("Content-Transfer-Encoding","binary");

//띄어스기와 대소문자에 주의하자.

response.getOutputStream().write(files);// 배열데이터 저장

//버퍼를 정리하고 닫아줌

response.getOutputStream().flush();

response.getOutputStream().close();

}

}



@RequestMapping("/board/openBoardList.do") // 주소를 지정한다.
//클라이언트에서 호출한 주소와 그것을 수행할 메서드를 연결한다.
public ModelAndView openBoardList() throws Exception{
	log.debug("openBoardList");
	ModelAndView mv = new ModelAndView("/board/boardList");

// 호출된 요청의 결과를 보여줄 뷰를 지정한다.Thymeleaf와 같은 템플릿을 사용할 경우

// 스프링 부트의 자동 설정 기능으로 .html과 같은 접미사를 생략할 수 있다.

// templates 폴더 아래에 있는 board/boardlist.html을 의미한다.

List<BoardingDto> list = boardService.selectBoardList(); // BoardService  클래스 생성 후 추가한다.

// 게시글 목록을 조회한다. 비지니스 로직을 수행학 ㅣ위해서 BoardService 클래스의 select BoardList를 호출한다.

//게시글 목록을 저장하기 위해 List 인터페이스를 사용하였다.

mv.addObject("list",list);

//실행된 비지니스 로직의 결과 값을 뷰에 list라는 이름으로 저장한다. 뷰에서 사용할 경우 list라는 이름으로

//조회결과를 사용할 수 있다.

return mv;

}

}