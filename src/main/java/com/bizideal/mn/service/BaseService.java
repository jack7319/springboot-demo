package com.bizideal.mn.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author : liulq
 * @date: 创建时间：2017年8月28日 下午1:20:59
 * @version: 1.0
 * @Description:
 */
public interface BaseService<T, ID extends Serializable> {

	/**
	 * 
	 * @Title: save
	 * @author : liulq
	 * @date: 创建时间：2017年8月28日 下午3:17:26
	 * @Description: 单个实体保存
	 * @param t
	 * @return
	 *
	 */
	T save(T t);

	/**
	 * 
	 * @Title: findOne
	 * @author : liulq
	 * @date: 创建时间：2017年8月28日 下午3:17:07
	 * @Description: 根据id查询
	 * @param id 主键id
	 * @return
	 *
	 */
	T findOne(ID id);

	/**
	 * 
	 * @Title: findAll
	 * @author : liulq
	 * @date: 创建时间：2017年8月28日 下午3:08:35
	 * @Description: 分页，下标从0开始
	 * @param pageNum 当前页码
	 * @param pageSize 每页显示条数
	 * @param direction 排序方式，Direction.ASC或DESC
	 * @param properties 参与排序的属性，可以有多个
	 * @return
	 *
	 */
	Page<T> findAll(int pageNum, int pageSize, Direction direction, String... properties);

	/**
	 * 
	 * @Title: findAll
	 * @author : liulq
	 * @date: 创建时间：2017年8月28日 下午3:16:55
	 * @Description: 查询所有
	 * @return
	 *
	 */
	List<T> findAll();

	/**
	 * 
	 * @Title: delete
	 * @author : liulq
	 * @date: 创建时间：2017年8月28日 下午3:16:28
	 * @Description: 根据id删除
	 * @param id 主键id
	 *
	 */
	void delete(ID id);

	/**
	 * 
	 * @Title: update
	 * @author : liulq
	 * @date: 创建时间：2017年8月28日 下午6:41:33
	 * @Description: 更新，其实调用的是save
	 * @param t
	 * @return
	 *
	 */
	T update(T t);

	/**
	 * 
	 * @Title: updateSelective
	 * @author : liulq
	 * @date: 创建时间：2017年8月28日 下午6:41:50
	 * @Description: 选择性更新，为空不更新，其实是先findOne，再save，效果一般
	 * @param t
	 * @return
	 * @throws Exception
	 *
	 */
	T updateSelective(T t) throws Exception;
}
