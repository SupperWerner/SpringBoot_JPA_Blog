package com.blog.blog.web.admin;

import com.blog.blog.errorExceptionClass.ParamentSubmitException;
import com.blog.blog.po.Blog;
import com.blog.blog.po.Tag;
import com.blog.blog.po.User;
import com.blog.blog.service.admin.BlogService;
import com.blog.blog.service.admin.TagService;
import com.blog.blog.service.admin.TypeService;
import com.blog.blog.util.Regex;
import com.blog.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 博客
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT="admin/blog_edit";   // 编辑页
    private static final String LIST="admin/blog_management";   // 管理页
    private static final String REDIRECT_LIST="redirect:/admin/blogs";  // 重定向到管理页

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /**
     *  跳转博客列表页
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        model.addAttribute("types",typeService.listType());
        return LIST;
    }

    /**
     * 局部刷新博客列表
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blog_management :: blogList";
    }

    /**
     * 跳转编辑页
     * @param model
     * @return
     */
    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("blog",new Blog());
        setTypeAndTag(model);
        return INPUT;
    }

    /**
     * 内部方法， 设置 model 返回值
     * @param model
     */
    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }

    /**
     * 跳转修改页面
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/blogs/{id}/input")
    public String blogEdit(Model model, @PathVariable Long id){
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }

    /**
     * 博客保存/修改功能
     * @param blog
     * @param attributes
     * @param session
     * @return
     */
    @PostMapping("/blogs/save")
    public String saveBlog(Blog blog, RedirectAttributes attributes,HttpSession session){

        boolean flag = true; // 判断是否成功添加标签

        /*
            遍历ids中全部的id 并判断是否为数字，不为数字进行  Tag 添加操作
         */
        String[] ids = blog.getTagIds().split(",");
        StringBuffer strId = new StringBuffer();
        for (String id : ids) {
            if (!Regex.StringisNumberType(id)){
                Tag tag = new Tag();
                tag.setName(id);
                Tag tag1 = tagService.saveTag(tag);
                if (tag1 != null){
                    strId.append(tag1.getId());
                }else{
                    throw new ParamentSubmitException("参数提交错误");
                }
            }else{
                strId.append(id).append(",");
            }
        }
        blog.setTagIds(strId.toString().substring(0,strId.toString().length()-1));
        
        User user = (User)session.getAttribute("user");
        blog.setUser(user);
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.getTag(blog.getTagIds()));
        Blog b = null;

        if (blog.getId() == null){
            b = blogService.saveBlog(blog);
        }else{
            b = blogService.updateBlog(blog.getId(),blog);
        }

        if (b==null){
            attributes.addFlashAttribute("message","操作失败！");
        }else{
            if (flag)
                attributes.addFlashAttribute("message","操作成功！");
            else
                attributes.addFlashAttribute("message","操作成功，标签添加失败！");

        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功！");
        return REDIRECT_LIST;
    }
}
