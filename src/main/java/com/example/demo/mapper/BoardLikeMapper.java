package com.example.demo.mapper;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface BoardLikeMapper {

	@Insert("""
			insert into BoardLike
			values (#{boardId}, #{memberId})
			""")
	Integer insert(Like like);

	@Delete("""
			delete from BoardLike
			where boardId = #{boardId}
			and
			memberId = #{memberId}
			""")
	Integer delete(Like like);

}
