package com.blog.blog.service.admin.impl;

import com.blog.blog.dao.TypeRepository;
import com.blog.blog.errorExceptionClass.NotFoundException;
import com.blog.blog.po.Type;
import com.blog.blog.service.admin.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService{
    @Autowired
    private TypeRepository typeRepository;

    // 新增一个类别
    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    // 获取一个类别
    @Transactional
    @Override
    public Type getType(Long id) {
        return  typeRepository.findById(id).orElse(null);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    // 分页查询分类
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    // 更新
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.findById(id).orElse(null);
        if (t == null) {
            throw new NotFoundException("不存在该对象");
        }

        // 对象复制
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC,"blogs.size");
        Pageable pageable1 =PageRequest.of(0,size,Sort.by(order));
        return typeRepository.findTop(pageable1);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }
}
