package com.bizideal.mn.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @author : liulq
 * @date: 创建时间：2017年8月28日 上午11:00:38
 * @version: 1.0
 * @Description:
 */
@Entity
@DynamicInsert
@DynamicUpdate(true)
@Table(name = "user_info")
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 5140254228585827006L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	@Column // 不可为空
	private String userName;
	@Column
	private Integer age;

	public UserInfo() {
		super();
	}

	public UserInfo(String userName, Integer age) {
		super();
		this.userName = userName;
		this.age = age;
	}

	public UserInfo(Integer userId, String userName, Integer age) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.age = age;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "{\"userId\":\"" + userId + "\", \"userName\":\"" + userName + "\", \"age\":\"" + age + "\"} ";
	}

}
