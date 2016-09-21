package kr.ac.sungkyul.mysite.controller;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.ac.sungkyul.mysite.service.BBSService;
import kr.ac.sungkyul.mysite.vo.AttachFileVo;
import kr.ac.sungkyul.mysite.vo.BoardVo;


@Controller
@RequestMapping("/bbs")
public class BBSController {
	
	@Autowired
	private BBSService bbsService;
	
	//쓰기폼
	@RequestMapping(value="write",method=RequestMethod.GET)
	public String write(){
		
		return "board/write";
	}
	
	//글등록
	@RequestMapping(value="register",method=RequestMethod.POST)
	public String reqisterBoard(BoardVo boardVo,MultipartFile file) throws Exception{		
		bbsService.insertBoard(boardVo,file);
		System.out.println(file.getOriginalFilename().toString());
		return "redirect:/bbs/list";
	}
	
	//리스트
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String listBoard(Model model){
		bbsService.listBoard();
		List<BoardVo> listBoard =bbsService.listBoard();
		//System.out.println(listBoard);
		model.addAttribute("listBoard",listBoard);
		return "board/list";
	}
	
	//상세
	@RequestMapping(value="view",method=RequestMethod.GET)
	public String readBoard(BoardVo boardVo,Model model){
		 boardVo = bbsService.selectBoard(boardVo);
		 int no = boardVo.getNo();
		 AttachFileVo attachfileVo =bbsService.selectAttachFileByNo(no);
	
		 model.addAttribute("BoardVo",boardVo);
		 model.addAttribute("attachfileVo",attachfileVo);
		return "board/view";
	}
	
	
	//수정 폼 불러오기
	@RequestMapping(value="modifyform",method=RequestMethod.GET)
	public String modifyform(BoardVo boardVo,Model model){
		 boardVo = bbsService.selectBoard(boardVo);
		 System.out.println(boardVo);
		 model.addAttribute("BoardVo",boardVo);
		return "/board/modify";
	}
	
	//수정하기
	@RequestMapping(value="update", method=RequestMethod.POST)
	public String update(BoardVo boardVo){
		bbsService.updateBoard(boardVo);
		return"redirect:/bbs/list";
	}
	
	//첨부
	@RequestMapping(value = "download", method = RequestMethod.GET)
	public void downloadFile(int fNO, HttpServletResponse res) throws Exception {
		 AttachFileVo attachFileVO = bbsService.selectAttachFileByFNO(fNO);
		 
		System.out.println(attachFileVO);
		
		String saveName = attachFileVO.getSaveName();
		String orgName = attachFileVO.getOrgName();
		    
		res.setContentType("application/download");
		res.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(orgName,"UTF-8") +"\"");
		OutputStream resOut = res.getOutputStream();
		
		FileInputStream fin = new FileInputStream("C:\\upload\\"+saveName);
		FileCopyUtils.copy(fin, resOut);
			
		fin.close();
		    
	}
	
	@RequestMapping(value = "view2", method = RequestMethod.GET)
	public String view2() {

		return "board/view2";
	}

	@ResponseBody
	@RequestMapping(value = "readAjax", method = RequestMethod.POST)
	public BoardVo readBoardAjax(int no) {
		BoardVo boardVo = bbsService.selectBoardNo(no);
		return boardVo;
	}
	
	
	

}
