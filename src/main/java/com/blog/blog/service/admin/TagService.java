package com.blog.blog.service.admin;

import com.blog.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag saveTag(Tag tag);
    Tag getTag(Long id);
    Tag getTagByName(String name);
    Page<Tag> listTag (Pageable pageable);
    List<Tag> listTag ();
    Tag updateTag (Long id,Tag tag);
    void deleteType(Long id);

    List<Tag> getTag(String ids);

    List<Tag> listTagTop(Integer size);
}
