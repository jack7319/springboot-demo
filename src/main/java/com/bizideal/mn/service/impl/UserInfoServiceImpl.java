package com.bizideal.mn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizideal.mn.entity.UserInfo;
import com.bizideal.mn.repository.UserInfoRepository;
import com.bizideal.mn.service.UserInfoService;

/**
 * @author : liulq
 * @date: 创建时间：2017年8月28日 下午1:20:35
 * @version: 1.0
 * @Description:
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo, Integer> implements UserInfoService {

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Override
	@Transactional
	public Integer updateById(Integer userId, String userName, Integer age) {
		return userInfoRepository.updateById(userId, userName, age);
	}

	@Override
	@Cacheable(key = "'userId'+#userId", value = "UserInfo")
	public UserInfo findByUserId(Integer userId) {
		return userInfoRepository.findByUserId(userId);
	}

}
