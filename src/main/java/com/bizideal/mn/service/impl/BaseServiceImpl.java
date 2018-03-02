package com.bizideal.mn.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bizideal.mn.exception.CommonException;
import com.bizideal.mn.service.BaseService;

/**
 * @author : liulq
 * @date: 创建时间：2017年8月28日 下午1:45:20
 * @version: 1.0
 * @Description:
 */
public abstract class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

	@Autowired
	private JpaRepository<T, ID> repository;

	@Override
	public T save(T t) {
		return repository.save(t);
	}

	@Override
	@Cacheable(key = "#p0")
	public T findOne(ID id) {
		return repository.findOne(id);
	}

	@Override
	public Page<T> findAll(int pageNum, int pageSize, Direction direction, String... properties) {
		PageRequest pageRequest = new PageRequest(pageNum, pageSize, direction, properties);
		return repository.findAll(pageRequest);
	}

	@Override
	public List<T> findAll() {
		return repository.findAll();
	}

	@Override
	public void delete(ID id) {
		repository.delete(id);
	}

	@Override
	public T update(T t) {
		return repository.save(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T updateSelective(T t) throws Exception {
		T newInstance = null;
		Field[] declaredFields = t.getClass().getDeclaredFields();
		boolean hasAnnotaion = false;
		for (Field field : declaredFields) {
			if (field.isAnnotationPresent(javax.persistence.Id.class)) {
				hasAnnotaion = true;
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				Object id = field.get(t);
				field.setAccessible(accessible);
				newInstance = this.findOne((ID) id);
				break;
			}
		}
		if (!hasAnnotaion)
			throw new CommonException("该类没有设置主键");
		if (null == newInstance)
			return t;
		for (Field field : declaredFields) {
			Class<?> type = field.getType();
			String fieldName = field.getName();
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			String setter = "set" + firstLetter + fieldName.substring(1);
			Method getMethod = null, setMethod = null;
			try {
				getMethod = t.getClass().getMethod(getter);
				setMethod = t.getClass().getMethod(setter, new Class[] { type });
			} catch (Exception e) {
				continue;
			}
			Object invoke = getMethod.invoke(t, new Object[] {});
			if (null == invoke) {
				continue; // 值为null
			}
			String value = invoke.toString();
			if (StringUtils.isNotBlank(value) || (type.equals(Long.class) && !"0".equals(value))) {
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				Object convert = ConvertUtils.convert(value, type);
				setMethod.invoke(newInstance, convert);
				field.setAccessible(accessible);
			}
		}
		return repository.save(newInstance);
	}

}
