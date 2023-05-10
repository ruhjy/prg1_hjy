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
	
//	@Select("""
//			select id, password, nickName, email, inserted
//			from Member m
//				join MemberAuthority ma on m.id = ma.memberId
//			where id = #{id}
//			""")
////	@ResultMap("memberMap")
//	Member selectById(String id);
	
	@Select("""
			select id, password, nickName, email, inserted
			from Member
			where id = #{id}
			""")
	Member selectById(String id);
	
	@Delete("delete from Member where id = #{id}")
	Integer deleteById(String id);

	// 시큐리티 적용 - 수정
	@Update("""
			<script>
				update Member
				set 
					<if test="password neq null and password neq ''">
						password = #{update.password},
					</if>
					
					nickName = #{update.nickName},
					email = #{update.email}
				where id = #{memberId}
			</script>
			""")
	Integer update(String memberId, Member update);
	
	@Update("""
			update Member
			set password = #{update.password},
				nickName = #{update.nickName},
				email = #{update.email}
			where id = #{memberId}
			""")
	Integer update2(String memberId, Member update);
	
	
	
	
}
