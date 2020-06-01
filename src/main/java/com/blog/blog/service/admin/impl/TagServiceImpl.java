package com.blog.blog.service.admin.impl;

import com.blog.blog.dao.TagRepository;
import com.blog.blog.errorExceptionClass.NotFoundException;
import com.blog.blog.po.Tag;
import com.blog.blog.service.admin.TagService;
import com.blog.blog.util.MyStringUtils;
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
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size, Sort.by(order));
        return tagRepository.findTop(pageable);
    }

    @Override
    public List<Tag> getTag(String ids) {
        return tagRepository.findAllById(MyStringUtils.StringToLongList(ids));
    }

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.findById(id).orElse(null);
        if (t == null) {
            throw new NotFoundException("不存在该标签对象！");
        }
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        tagRepository.deleteById(id);
    }

}
