package com.blog.blog.web.admin;

import com.blog.blog.po.Tag;
import com.blog.blog.service.admin.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("admin")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 跳转标签列表页
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable , Model model ){
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tag_management";
    }

    /**
     * 跳转标签新增
     * @param model
     * @return
     */
    @GetMapping("/tags/input")
    public String input (Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tag_edit";
    }

    /**
     * 新增标签
     * @param tag
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/tags")
    public String saveTag(Tag tag, BindingResult result , RedirectAttributes attributes){
        if (!StringUtils.isEmpty(tag.getName().trim())){
            Tag t = tagService.getTagByName(tag.getName().trim());
            if (t != null)
            result.rejectValue("name","nameError","标签不可重复添加！");
        }
        if (result.hasErrors())
            return "admin/tag_edit";

        Tag t = tagService.saveTag(tag);
        if (t==null) {
            attributes.addFlashAttribute("message","新增失败！");
        } else {
            attributes.addFlashAttribute("message","新增成功！");
        }
            return "redirect:/admin/tags";

    }

    /**
     * 跳转编辑页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}/input")
    public String editTag(@PathVariable Long id,Model model){
        Tag tag = tagService.getTag(id);
        model.addAttribute("tag",tag);
        return "admin/tag_edit";
    }

    @PostMapping("/tags/{id}")
    public String updateTag(@PathVariable Long id , Tag tag ,BindingResult result ,RedirectAttributes attributes) {
        if (!StringUtils.isEmpty(tag.getName())){
            Tag t = tagService.getTagByName(tag.getName());
            if (t != null){
                result.rejectValue("name","errorName","标签已存在，修改失败！");
            }
        }
        if (result.hasErrors()) {
            return "admin/tag_edit";
        }
        Tag t = tagService.updateTag(id, tag);
        if (t == null) {
            attributes.addFlashAttribute("message","修改失败！");
        } else {
            attributes.addFlashAttribute("message","修改成功！");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功!");
        return "redirect:/admin/tags";
    }
}
