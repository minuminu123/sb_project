package com.KoreaIT.smw.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.KoreaIT.smw.demo.vo.Board;

@Mapper
public interface BoardRepository {

	@Select("""
			SELECT *
			FROM board
			WHERE id= #{boardId}
			AND delStatus = 0;
			""")
	Board getBoardById(int boardId);

	@Select("""
			SELECT A.*
			FROM board AS B
			INNER JOIN article AS A
			ON A.boardId = B.id
			ORDER BY A.id DESC;
			""")
	List<Board> getForPrintArticles(int boardId);

}
