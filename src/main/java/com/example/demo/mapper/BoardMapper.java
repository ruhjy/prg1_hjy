package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface BoardMapper {

	@Select("select id, title, writer, inserted from Board order by id desc")
	List<Board> selectAll();

	@Select("select * from Board where id = #{id}")
	Board selectById(Integer id);

	@Update("update Board "
			+ "set title = #{title}, "
			+ "	   body = #{body}, "
			+ "    writer = #{writer} "
			+ "where id = #{id}")
	int update(Board board);

	@Delete("delete from Board where id = #{id}")
	int deleteById(Integer id);
}
