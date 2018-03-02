package com.bizideal.mn.service;

import com.bizideal.mn.entity.UserInfo;

/**
 * @author : liulq
 * @date: 创建时间：2017年8月28日 下午1:20:09
 * @version: 1.0
 * @Description:
 */
public interface UserInfoService extends BaseService<UserInfo, Integer> {

	Integer updateById(Integer userId, String userName, Integer age);

	UserInfo findByUserId(Integer userId);
}
