package boarding.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import boarding.dto.BoardingDto;
import boarding.dto.BoardingFileDto;
import boarding.service.BoardingService;

@Controller
public class RestBoardingController {
	
	@Autowired
	private BoardingService boardService;
	
	@RequestMapping(value="/board", method=RequestMethod.GET)//주소와 요청방법을 꼭 지정해야한다.
	public ModelAndView openBoardList() throws Exception{
		ModelAndView mv = new ModelAndView("/board/restBoardList");
		List<BoardingDto> list = boardService.selectBoardList();
		mv.addObject("list", list);
		return mv;		
	}
	
	@RequestMapping(value="/board/write", method=RequestMethod.GET)
	public String openBoardWrite() throws Exception{
		return "/board/restBoardWrite";
	}
	
	@RequestMapping(value="/board/write", method=RequestMethod.POST)
	public String insertBoard(BoardingDto board , MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		boardService.insertBoard(board, multipartHttpServletRequest);
		return "redirect:/board";
	}
	
	@RequestMapping(value ="/board/{boardIdx}", method=RequestMethod.GET)
	public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception{
		ModelAndView mv = new ModelAndView("/board/restBoardDetail");
		
		BoardingDto board = boardService.selectBoardDetail(boardIdx);
		mv.addObject("board",board);
		
		return mv;
	}
	
	@RequestMapping(value="/board/{boardIdx}", method=RequestMethod.PUT)
	public String updateBoard(BoardingDto board) throws Exception{
		boardService.updateBoard(board);
		return "redirect:/board";
	}
	
	@RequestMapping(value = "/board/{boardIdx}", method=RequestMethod.DELETE)
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
		boardService.deleteBoard(boardIdx);
		return "redirect:/board";
	}
	
	@RequestMapping(value="/board/file", method=RequestMethod.GET)
	public void downloadFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception{
		BoardingFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
		
		if(ObjectUtils.isEmpty(boardFile) ==  false) {
			String fileName = boardFile.getOriginalFileName();
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" +  URLEncoder.encode(fileName,"UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		}
	}
}
