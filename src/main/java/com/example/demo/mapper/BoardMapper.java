package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

//@Mapper
public interface BoardMapper {

//	@Select("select id, title, writer, inserted from Board order by id desc")
	List<Board> selectAll();

//	@Select("select * from Board where id = #{id}")
//	@ResultMap("boardResultMap")
	Board selectById(Integer id);

//	@Update("update Board "
//			+ "set title = #{title}, "
//			+ "	   body = #{body}, "
//			+ "    writer = #{writer} "
//			+ "where id = #{id}")
	int update(Board board);

//	@Delete("delete from Board where id = #{id}")
	int deleteById(Integer id);

//	@Insert("insert into Board (title, body, writer) values (#{title}, #{body}, #{writer})")
//	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(Board board);

//	@Select("<script> "
//			+ "<bind name=\"pattern\" value=\"\'%\' + search + \'%\'\" /> "
//			+ "    select id, title, writer, inserted from Board "
//			+ "    where title like #{pattern} "
//			+ "       or body like #{pattern} "
//			+ "       or writer like #{pattern} "
//			+ "order by id desc limit #{startIndex}, #{rowPerPage} "
//			+ "</script>")
//	List<Board> selectAllPaging(Integer startIndex, Integer rowPerPage, String search);

//	@Select("<script> "
//			+ "    select id, title, writer, inserted from Board "
//			+ "<where> "
//			+ "		<if test=\"(type eq \'all\') or (type eq \'title\')\"> "
//			+ "			or title like concat(\'%\', #{search}, \'%\') "
//			+ "		</if> "
//			+ "		<if test=\"(type eq \'all\') or (type eq \'body\')\"> "
//			+ "			or body like concat(\'%\', #{search} ,\'%\') "
//			+ "		</if> "
//			+ "		<if test=\"(type eq \'all\') or (type eq \'writer\')\"> "
//			+ "			or writer like concat(\'%\', #{search} ,\'%\') "
//			+ "		</if> "
//			+ "</where>"
//			+ "order by id desc limit #{startIndex}, #{rowPerPage} "
//			+ "</script>")
	List<Board> selectAllPaging(Integer startIndex, Integer rowPerPage, String search, String type);

//	@Select("<script> "
//			+ "    select id, title, writer, inserted from Board "
//			+ "where title like concat(\'%\', #{search}, \'%\') "
//			+ "<if test=\"type eq \'all\'\"> "
//			+ "	 or	 body like concat(\'%\', #{search}, \'%\') "
//			+ "  or  writer like concat(\'%\', #{search}, \'%\') "
//			+ "</if>"
//			+ "order by id desc limit #{startIndex}, #{rowPerPage} "
//			+ "</script>")
//	List<Board> selectAllPaging(Integer startIndex, Integer rowPerPage, String search, String type);

//	@Select("select id, title, writer, inserted from Board "
//			+ "where title like #{search} "
//			+ "	 or	 body like #{search} "
//			+ "  or  writer like #{search}"
//			+ "order by id desc limit #{startIndex}, #{rowPerPage}")
//	List<Board> selectAllPaging(Integer startIndex, Integer rowPerPage, String search);

//	@Select("<script> "
//			+ "<bind name=\"pattern\" value=\"\'%\' + search + \'%\'\" /> "
//			+ "		select count(*) from Board "
//			+ "<where> "
//			+ "		<if test=\"(type eq \'all\') or (type eq \'title\')\"> "
//			+ "			or title like #{pattern} "
//			+ "		</if> "
//			+ "		<if test=\"(type eq \'all\') or (type eq \'body\')\"> "
//			+ "			or body like #{pattern} "
//			+ "		</if> "
//			+ "		<if test=\"(type eq \'all\') or (type eq \'writer\')\"> "
//			+ "			or writer like #{pattern} "
//			+ "		</if> "
//			+ "</where>"
//			+ "</script>")
	Integer countAll(String search, String type);

	Integer insertFileName(Integer boardId, String fileName);

}
