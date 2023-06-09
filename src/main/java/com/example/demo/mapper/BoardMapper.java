package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface BoardMapper {
	
	@Select("""
			SELECT
				id,
				title,
				writer,
				inserted
			FROM Board
			ORDER BY id DESC
			""")
	List<Board> selectAll();

	@Select("""
			SELECT 
				b.id,
				b.title,
				b.body,
				b.inserted,
				b.writer,
				b.hit,
				f.fileName,
				(select count(*) from BoardLike where boardId = b.id) as likeCount
			FROM Board b LEFT JOIN FileName f ON b.id = f.boardId
			WHERE b.id = #{id}
			""")
	@ResultMap("boardResultMap")
	Board selectById(Integer id);

	@Update("""
			UPDATE Board
			SET 
				title = #{title},
				body = #{body}
			WHERE
				id = #{id}
			""")
	int update(Board board);

	@Delete("""
			DELETE FROM Board
			WHERE id = #{id}
			""")
	int deleteById(Integer id);

	@Insert("""
			INSERT INTO Board (title, body, writer)
			VALUES (#{title}, #{body}, #{writer})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(Board board);

	@Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'" />
			SELECT
				b.id,
				b.title,
				b.writer,
				b.hit,
				b.inserted,
				count(f.id) fileCount,
				(select count(*) from BoardLike where boardId = b.id) as likeCount,
				(select count(*) from Comment where boardId = b.id) as commentCount 
			FROM Board b
			left join FileName f on b.id = f.boardId
			
			<where>
				<if test="(type eq 'all') or (type eq 'title')">
				   title  LIKE #{pattern}
				</if>
				<if test="(type eq 'all') or (type eq 'body')">
				OR body   LIKE #{pattern}
				</if>
				<if test="(type eq 'all') or (type eq 'writer')">
				OR writer LIKE #{pattern}
				</if>
			</where>
			
			group by b.id
			ORDER BY b.id DESC
			LIMIT #{startIndex}, #{rowPerPage}
			</script>
			""")
	List<Board> selectAllPaging(Integer startIndex, Integer rowPerPage, String search, String type);

	@Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'" />
			SELECT COUNT(*) 
			FROM Board
			
			<where>
				<if test="(type eq 'all') or (type eq 'title')">
				   title  LIKE #{pattern}
				</if>
				<if test="(type eq 'all') or (type eq 'body')">
				OR body   LIKE #{pattern}
				</if>
				<if test="(type eq 'all') or (type eq 'writer')">
				OR writer LIKE #{pattern}
				</if>
			</where>
			
			</script>
			""")
	Integer countAll(String search, String type);

	@Insert("""
			INSERT INTO FileName (boardId, fileName)
			VALUES (#{boardId}, #{fileName})
			""")
	Integer insertFileName(Integer boardId, String fileName);

	@Select("""
			select FileName from FileName where boardId = #{boardId}
			""")
	List<String> selectFileNamesByBoardId(Integer boardId);
	
	@Delete("""
			delete from FileName where boardId = #{boardId}
			""")
	void deleteFileNameByBoardId(Integer boardId);

	@Delete("""
			delete from FileName 
			where 
				boardId = #{boardId}
					and
				fileName = #{fileName}
			""")
	void deleteFileNameByBoardIdAndFileName(Integer boardId, String fileName);

	@Update("""
			update Board set hit = hit + 1 where id = #{boardId}
			""")
	void plusHit(Integer boardId);

	@Select("select id from Board where writer = #{writer}")
	List<Integer> selectBoardIdByWriter(String writer);

}
