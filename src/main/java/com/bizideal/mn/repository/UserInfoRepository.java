package com.bizideal.mn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bizideal.mn.entity.UserInfo;

/**
 * @author : liulq
 * @date: 创建时间：2017年8月28日 上午11:05:00
 * @version: 1.0
 * @Description: 相当于mybatis中的mapper
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>, JpaSpecificationExecutor<UserInfo> {

	UserInfo findByUserName(String userName); // 通过解析方法名创建查询

	UserInfo findByUserNameAndAge(String userName, Integer age); // 通过解析方法名创建查询

	UserInfo findByUserId(Integer userId);// 通过解析方法名创建查询

	@Modifying
	@Query("update UserInfo set userName=:userName,age=:age where userId =:userId")
	Integer updateById(@Param("userId") Integer userId, @Param("userName") String userName, @Param("age") Integer age);

	@Query("from UserInfo where userName =:name")
	UserInfo findByName(@Param("name") String name);

}
