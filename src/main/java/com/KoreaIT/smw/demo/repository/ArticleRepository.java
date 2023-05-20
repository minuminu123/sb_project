package com.KoreaIT.smw.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.KoreaIT.smw.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	@Insert("""
			INSERT INTO article
			SET regDate = NOW(),
			updateDate = NOW(),
			memberId = #{memberId},
			boardId = #{boardId},
			title =#{title},
			`body`= #{body}
				""")
	public void writeArticle(int memberId, int boardId, String title, String body);

	@Select("""
			SELECT A.*, M.nickname AS extra__writer
			FROM article AS A
			INNER JOIN `member` AS M
			ON A.memberId = M.id
			WHERE A.memberId = #{actorId}
			ORDER BY A.id DESC
				""")
	public List<Article> getArticles(int actorId);

	@Select("""
			<script>
			SELECT A.*,
			M.nickname AS extra__writer
			FROM article AS A
			INNER JOIN `member` AS M
			ON A.memberId = M.id
			WHERE 1
			<if test="boardId != 0">
				AND A.boardId = #{boardId}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'title'" >
						AND A.title LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchKeywordTypeCode == 'body'" >
						AND A.body LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<otherwise>
						AND A.title LIKE CONCAT('%',#{searchKeyword},'%')
						OR A.body LIKE CONCAT('%',#{searchKeyword},'%')
					</otherwise>
				</choose>
			</if>
			<choose>
					<when test="filter == 'recent'" >
						ORDER BY A.id DESC
					</when>
					<when test="filter == 'hitCount'" >
						ORDER BY A.hitCount DESC
					</when>
					<when test="filter == 'comments'">
						ORDER BY A.repliesCount DESC
					</when>
					<when test="filter == 'old'">
						ORDER BY A.id ASC
					</when>
					<otherwise>
						ORDER BY A.goodReactionPoint DESC
					</otherwise>
			</choose>
			<if test="limitFrom >= 0">
				LIMIT #{limitFrom}, #{limitTake}
			</if>
			</script>
				""")
	public List<Article> getForPrintArticles(int boardId, String searchKeywordTypeCode, String searchKeyword,
			int limitFrom, int limitTake, String filter);

	@Select("""
			SELECT *
			FROM article
			WHERE id = #{id}
			""")
	public Article getArticle(int id);

	@Select("""
			<script>
			SELECT A.*, M.nickname AS extra__writer
			FROM article AS A
			INNER JOIN `member` AS M
			ON A.memberId = M.id
			WHERE A.id = #{id}
			</script>
			""")
	public Article getForPrintArticle(int id);

	public void deleteArticle(int id);

	@Update("""
			<script>
			UPDATE article
			<set>
				<if test="title != null and title != ''">title = #{title},</if>
				<if test="body != null and body != ''">`body` = #{body},</if>
				updateDate= NOW()
			</set>
			WHERE id = #{id}
			</script>
			""")
	public void modifyArticle(int id, String title, String body);

	public int getLastInsertId();

	@Select("""
			<script>
			SELECT COUNT(*) AS cnt
			FROM article AS A
			WHERE 1
			<if test="boardId != 0">
				AND A.boardId = #{boardId}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'title'" >
						AND A.title LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchKeywordTypeCode == 'body'" >
						AND A.body LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<otherwise>
						AND A.title LIKE CONCAT('%',#{searchKeyword},'%')
						OR A.body LIKE CONCAT('%',#{searchKeyword},'%')
					</otherwise>
				</choose>
			</if>
			</script>
				""")
	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword);

	@Update("""
			<script>
				UPDATE article
				SET hitCount = hitCount + 1
				WHERE id = #{id}
			</script>
			""")

	public int increaseHitCount(int id);

	@Select("""
			<script>
				SELECT hitCount
				FROM article
				WHERE id = #{id}
			</script>
			""")
	public int getArticleHitCount(int id);

	@Update("""
			<script>
				UPDATE article
				SET goodReactionPoint = goodReactionPoint + 1
				WHERE id = #{relId}
			</script>
			""")
	public int increaseGoodReationPoint(int relId);

	@Update("""
			<script>
				UPDATE article
				SET badReactionPoint = badReactionPoint + 1
				WHERE id = #{relId}
			</script>
			""")
	public int increaseBadReationPoint(int relId);

	@Update("""
			<script>
				UPDATE article
				SET goodReactionPoint = goodReactionPoint - 1
				WHERE id = #{relId}
			</script>
			""")
	public int decreaseGoodReationPoint(int relId);

	@Update("""
			<script>
				UPDATE article
				SET badReactionPoint = badReactionPoint - 1
				WHERE id = #{relId}
			</script>
			""")
	public int decreaseBadReationPoint(int relId);

}
