package lk.PremasiriBrothers.inventorymanagement.asset.process.finance.controller;

import lk.PremasiriBrothers.inventorymanagement.asset.process.finance.entity.DiscountRatio;
import lk.PremasiriBrothers.inventorymanagement.asset.process.finance.service.DiscountRatioService;
import lk.PremasiriBrothers.inventorymanagement.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/discount")
public class DiscountController {
    private final DiscountRatioService discountRatioService;
    private final UserService userService;

    @Autowired
    public DiscountController(DiscountRatioService discountRatioService, UserService userService) {
        this.discountRatioService = discountRatioService;
        this.userService = userService;
    }

    @RequestMapping
    public String allDiscounts(Model model){
        List<DiscountRatio> discountRatios = discountRatioService.findAll();
        model.addAttribute("discounts", discountRatios);
        return "discount/discount";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editDiscount(@PathVariable("id") Integer id, Model model){
        model.addAttribute("discount", discountRatioService.findById(id));
        model.addAttribute("addStatus", false);
        return "discount/addDiscountRatio";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String DiscountForm(Model model){
        model.addAttribute("addStatus", true);
        model.addAttribute("discount", new DiscountRatio());
        return "discount/addDiscountRatio";
    }
    @RequestMapping(value = {"/add","/update"}, method = RequestMethod.POST)
    public String addDiscount(@Valid @ModelAttribute DiscountRatio discountRatio, BindingResult result, Model model, RedirectAttributes redirectAttributes){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userService.findByUserIdByUserName(auth.getName());
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                System.out.println(error.getField() + ": " + error.getDefaultMessage());
            }
            model.addAttribute("addStatus", true);
            model.addAttribute("discount", discountRatio);
            return "discount/addDiscountRatio";
        }
        discountRatioService.persist(discountRatio);
        return "redirect:/discount";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String deleteDiscount(@PathVariable("id") Integer id){
        discountRatioService.delete(id);
        return "redirect:/discount";
    }
}
