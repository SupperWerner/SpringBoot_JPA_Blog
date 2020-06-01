package com.blog.blog.web;

import com.blog.blog.po.Blog;
import com.blog.blog.po.Tag;
import com.blog.blog.service.admin.BlogService;
import com.blog.blog.service.admin.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;
    @GetMapping("/tags/{id}")
    public String types(@PageableDefault(size=8, sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable, @PathVariable Long id, Model model){
        List<Tag> tags = tagService.listTagTop(100000);
        if(id==-1){
            id = tags.get(0).getId();
        }
        Page<Blog> blogs = blogService.listBlog(pageable, id);
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogs);
        model.addAttribute("activeTagId",id);
        return "labelPage";
    }
}
