package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface CommentMapper {

	@Insert("""
			insert into Comment (boardId, memberId, content)
			values (#{boardId}, #{memberId}, #{content})
			""")
	Integer insert(Comment comment);

	@Select("""
			select * from Comment
			where boardId = #{boardId}
			order by id desc
			""")
	List<Comment> selectAllByBoardId(Integer boardId);

	@Select("select * from Comment where id = #{commentId}")
	Comment selectById(Integer commentId);

	@Update("""
			update Comment
			set
				content = #{content}
			where
				id = #{id}
			""")
	Integer update(Comment comment);

	@Delete("delete from Comment where id = #{commentId}")
	Integer deleteById(Integer commentId);

}
