package kr.ac.sungkyul.mysite.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.ac.sungkyul.mysite.dao.BBSDao;
import kr.ac.sungkyul.mysite.vo.AttachFileVo;
import kr.ac.sungkyul.mysite.vo.BoardVo;

@Service
public class BBSService {
	@Autowired
	private BBSDao bbsDAO;
	
	
	public void insertBoard(BoardVo boardVo,MultipartFile file)throws Exception{
		//2.no 게시글저장
		int no = bbsDAO.insertBoard(boardVo);
		
		//1.fno 저장할때 
		
		//3.orgName
		String orgName = file.getOriginalFilename();
		//4.filesize
		long fileSize = file.getSize();
		//5.saveName
		String saveName = UUID.randomUUID().toString()+"_"+orgName;
		//6.path
		String path="c:\\upload";
		
		AttachFileVo attachFileVo = new AttachFileVo();
		attachFileVo.setNo(no);
		attachFileVo.setPath(path);
		attachFileVo.setOrgName(orgName);
		attachFileVo.setSaveName(saveName);
		attachFileVo.setFileSize(fileSize);
		
		System.out.println(attachFileVo.toString());
		
		bbsDAO.insertAttachFile(attachFileVo);
		
		File target = new File(path,saveName);
		FileCopyUtils.copy(file.getBytes(),target);
	}
	
	public AttachFileVo selectAttachFileByNo(int no){
		return bbsDAO.selectAttachFileByNo(no);
	}
	
	public List<BoardVo> listBoard(){
		return bbsDAO.listBoard();
	}
	
	public BoardVo selectBoard(BoardVo boardVo){
		return bbsDAO.selectBoard(boardVo);
	}
	
	public void updateBoard(BoardVo boardVo){
		bbsDAO.updateBoard(boardVo);
	}
	
	public AttachFileVo selectAttachFileByFNO(int fNO){
		return bbsDAO.selectAttachFileByFNO(fNO);
	}
	
	public BoardVo selectBoardNo(int no){
		return bbsDAO.selectBoardNo(no);
	}

}
