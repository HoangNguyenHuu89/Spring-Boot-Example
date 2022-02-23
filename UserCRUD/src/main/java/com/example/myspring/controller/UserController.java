package com.example.myspring.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.myspring.model.User;
import com.example.myspring.service.UserService;

@Controller
public class UserController implements WebMvcConfigurer {
	@Autowired
	UserService userService;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/users").setViewName("users");
	}

	@GetMapping("/")
	public String home() {
		return "redirect:/users";
	}

	@GetMapping("/users")
	public String index(Model model, HttpServletRequest request, RedirectAttributes redirect) {
		request.getSession().setAttribute("userlist", null);

		if (model.asMap().get("success") != null)
			redirect.addFlashAttribute("success", model.asMap().get("success").toString());
		return "redirect:/users/page/1";
	}

	@GetMapping("/users/create")
	public String add(Model model) {
		model.addAttribute("user", new User());
		return "form";
	}

	@PutMapping(value = "/users/{id}")
	public String update(@PathVariable long id, Model model) {
		model.addAttribute("user", userService.findOne(id));
		return "form";
	}

	@PostMapping("/users")
	public String save(@Valid User user, BindingResult result, RedirectAttributes redirect, Model model) {
		if (result.hasErrors()) {
			return "form";
		}
		userService.save(user);
		redirect.addFlashAttribute("success", "Saved user successfully!");
		return "redirect:/users";
	}

	@DeleteMapping(value = "/users/{id}")
	public String delete(@PathVariable() long id, RedirectAttributes redirect) {
		userService.delete(id);
		redirect.addFlashAttribute("success", "Deleted user successfully!");
		return "redirect:/users";
	}

	@GetMapping(value = "/users/page/{pageNumber}")
	public String search(@RequestParam(value = "search", required = false, defaultValue = "") String search,
			Model model, HttpServletRequest request, @PathVariable int pageNumber) {
		List<User> list = null;
		int pagesize = 0;
		if (search.equals("")) {
			PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("userlist");
			pagesize = 5;
			list = (List<User>) userService.findAll();
			System.out.println(list.size());
			if (pages == null) {
				pages = new PagedListHolder<>(list);
				pages.setPageSize(pagesize);
			} else {
				final int goToPage = pageNumber - 1;
				if (goToPage <= pages.getPageCount() && goToPage >= 0) {
					pages.setPage(goToPage);
				}
			}
			request.getSession().setAttribute("userlist", pages);
			int current = pages.getPage() + 1;
			int begin = Math.max(1, current - list.size());
			int end = Math.min(begin + 5, pages.getPageCount());
			int totalPageCount = pages.getPageCount();
			String baseUrl = "/users/page/";

			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("totalPageCount", totalPageCount);
			model.addAttribute("baseUrl", baseUrl);
			model.addAttribute("users", pages);

			return "list";
		}
		list = userService.search(search);
		if (list == null) {
			return "redirect:/users";
		}
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("userlist");
		pagesize = 3;
		pages = new PagedListHolder<>(list);
		pages.setPageSize(pagesize);

		final int goToPage = pageNumber - 1;
		if (goToPage <= pages.getPageCount() && goToPage >= 0) {
			pages.setPage(goToPage);
		}
		request.getSession().setAttribute("userlist", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/users/page/";
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("users", pages);
		return "list";
	}
}
