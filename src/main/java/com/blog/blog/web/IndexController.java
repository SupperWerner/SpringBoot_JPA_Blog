package com.blog.blog.web;

import com.blog.blog.service.admin.BlogService;
import com.blog.blog.service.admin.TagService;
import com.blog.blog.service.admin.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 跳转主页方法
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(@PageableDefault(size=8,sort = {"updateTime"},direction= Sort.Direction.DESC)Pageable pageable,
                        Model model){
        model.addAttribute("page",blogService.listBlog(pageable)); // 获得博客list
        model.addAttribute("tags",tagService.listTagTop(6));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("recommendBlogs",blogService.listBlogTop(8));
        return "index";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }

    /**
     * 主页查询方法
     * @param pageable
     * @param query
     * @param model
     * @return
     */
    @PostMapping("/search")
    public String search(@PageableDefault(size=8,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable
            , @RequestParam String query,Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

/*    @PostMapping("/comments")
    @ResponseBody
    public void Comments(Comment comment){

    }*/

    @GetMapping("/footer/newblog")
    public String newBlogs(Model model){
        model.addAttribute("blogs",blogService.listBlogTop(3));
        return "_fragments :: newBlogList";
    }
}
