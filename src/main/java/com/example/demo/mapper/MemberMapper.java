package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface MemberMapper {

	@Insert("""
			insert into Member (id, password, nickName, email)
			values
				(#{id}, #{password}, #{nickName}, #{email})
			""")
	int insert(Member member);
	
	@Select("""
			select id, password, nickName, email, inserted
			from Member order by inserted desc
			""")
	List<Member> selectAll();
	
	@Select("select id, password, nickName, email, inserted from Member where id = #{id}")
	Member findById(String id);
	
	@Update("""
			update Member
			set 
				password = #{update.password},
				nickName = #{update.nickName},
				email = #{update.email}
			where id = #{id};
			""")
	Integer update(String id, UpdateMemberForm update);
}
