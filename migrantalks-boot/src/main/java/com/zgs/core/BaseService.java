package com.zgs.core;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 * @author zgs
 */
public interface BaseService<T> {

    /**
     * 保存
     * @param model
     */
    void insert(T model);

    /**
     * 批量保存
     * @param models
     */
    void insertList(List<T> models);

    /**
     * 通过主鍵刪除
     * @param id
     */
    void deleteById(Long id);

    /**
     * 批量删除
     * ids -> "1,2,3,4"
     * @param ids
     */
    void deleteByIds(String ids);

    /**
     * 批量删除
     * @param list
     * @param property
     * @param clazz
     * @return
     */
    int batchDelete(List<String> list, String property, Class<T> clazz);

    /**
     * 伪删除
     * @param model
     */
    void pseudoDelById(T model);

    /**
     * 更新
     * @param model
     */
    void update(T model);//更新

    /**
     * 通过ID查找
     * @param id
     * @return
     */
    T selectById(Long id);//通过ID查找

    /**
     * 通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
     * @param fieldName
     * @param value
     * @return
     * @throws TooManyResultsException
     */
    T selectBy(String fieldName, Object value) throws TooManyResultsException;
    /**
     * 根据多个ID查找
     * ids -> "1,2,3,4"
     * @param ids
     * @return
     */
    List<T> selectByIds(String ids);
    /**
     * 根据条件查找
     * @param condition
     * @return
     */
    List<T> selectByCondition(Condition condition);
    /**
     * 获取所有
     * @return
     */
    List<T> selectAll();

    /**
     * 获取所有
     * @param t
     * @return
     */
    List<T> select(T t);
}
