package kr.ac.sungkyul.mysite.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.sungkyul.mysite.vo.AttachFileVo;
import kr.ac.sungkyul.mysite.vo.BoardVo;

@Repository
public class BBSDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insertBoard(BoardVo boardVo){
		sqlSession.insert("bbs.insertBoard",boardVo);
		return boardVo.getNo();
	
	}
	
	public List<BoardVo> listBoard(){
		List<BoardVo> listBoard = sqlSession.selectList("bbs.listBoard");
		return listBoard;
	}
	
	public BoardVo selectBoard(BoardVo boardVo){
		return sqlSession.selectOne("bbs.selectBoard",boardVo);
	}
	
	public void updateBoard(BoardVo boardVo){
		 sqlSession.update("bbs.updateBoard",boardVo);
	}
	public void insertAttachFile(AttachFileVo attachFileVo){
		sqlSession.insert("bbs.insertAttachFile",attachFileVo);
	}
	
	public AttachFileVo selectAttachFileByNo(int no){
		return sqlSession.selectOne("bbs.selectAttachFileByNo",no);
	}
	
	public AttachFileVo selectAttachFileByFNO(int fNO){
		return sqlSession.selectOne("bbs.selectAttachFileByFNO",fNO);
	}

}
