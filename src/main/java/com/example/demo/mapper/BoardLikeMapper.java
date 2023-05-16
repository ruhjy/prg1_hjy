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

	@Select("""
			select count(*) from BoardLike
			where boardId = #{boardId}
			""")
	Integer countByBoardId(Integer boardId);

	@Select("""
			select * from BoardLike
			where boardId = #{boardId}
			 and memberId = #{memberId}
			""")
	Like select(Integer boardId, String memberId);

	@Delete("""
			delete from BoardLike
			where boardId = #{boardId}
			""")
	void deleteByBoardId(Integer boardId);

	@Delete("""
			delete from BoardLike
			where memberId = #{memberId}
			""")
	void deleteByMemberId(String id);

}
