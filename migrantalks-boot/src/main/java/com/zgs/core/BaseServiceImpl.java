package com.zgs.core;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 * @author zgs
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    protected BaseMapper<T> mapper;

    /**
     * 当前泛型真实类型的Class
     */
    private Class<T> modelClass;

    public BaseServiceImpl() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public void insert(T model) {
        mapper.insertSelective(model);
    }

    @Override
    public void insertList(List<T> models) {
        mapper.insertList(models);
    }

    @Override
    public void deleteById(Long id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(String ids) {
        mapper.deleteByIds(ids);
    }

    @Override
    public int batchDelete(List<String> list, String property, Class<T> clazz) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, list);
        return this.mapper.deleteByExample(example);
    }

    @Override
    public void pseudoDelById(T model) {
        mapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public void update(T model) {
        mapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public T selectById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T selectBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public List<T> selectByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    @Override
    public List<T> selectByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    @Override

    public List<T> selectAll() {
        return mapper.selectAll();
    }
    @Override
    public List<T> select(T t) {return mapper.select(t);}
}
