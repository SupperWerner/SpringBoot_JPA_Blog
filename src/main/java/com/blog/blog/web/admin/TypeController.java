package com.blog.blog.web.admin;

import com.blog.blog.po.Type;
import com.blog.blog.service.admin.TypeService;
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

import javax.validation.Valid;

@Controller
@RequestMapping("admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 跳转分类管理页面
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/type_management";  // 跳转到分类管理页面
    }

    /**
     * 跳转新增页面
     * @param model
     * @return
     */
    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type",new Type());
        return "admin/type_edit";
    }

    /**
     * 跳转编辑页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        Type type = typeService.getType(id);
        model.addAttribute("type",type);
        return "admin/type_edit";
    }

    /**
     * 新增分类
     * @param type
     * @param result
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/types")
    public String saveType(@Valid Type type, BindingResult result , RedirectAttributes redirectAttributes) {
        if (!StringUtils.isEmpty(type.getName())){
            Type t = typeService.getTypeByName(type.getName());
            if (t != null) {
                result.rejectValue("name","nameError","不可重复添加！");
            }
        }

        if (result.hasErrors()){
            return "admin/type_edit";
        }
        Type t = typeService.saveType(type);
        if (t == null){
            redirectAttributes.addFlashAttribute("message","新增失败!");
            redirectAttributes.addFlashAttribute("code","0");
        }else{
            //
            redirectAttributes.addFlashAttribute("message","新增成功!");
            redirectAttributes.addFlashAttribute("code","1");
        }
        return "redirect:/admin/types";
    }


    @PostMapping("/types/{id}")
    public String editType(@Valid Type type, BindingResult result ,@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (!StringUtils.isEmpty(type.getName())){
            Type t = typeService.getTypeByName(type.getName());
            if (t != null) {
                result.rejectValue("name","nameError","不可重复添加！");
            }
        }

        if (result.hasErrors()){
            return "admin/type_edit";
        }
        Type t = typeService.updateType(id,type);
        if (t == null){
            redirectAttributes.addFlashAttribute("message","更新失败!");
            redirectAttributes.addFlashAttribute("code","0");
        }else{
            //
            redirectAttributes.addFlashAttribute("message","更新成功!");
            redirectAttributes.addFlashAttribute("code","1");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete (@PathVariable Long id,RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功!");
        return "redirect:/admin/types";
    }


}
