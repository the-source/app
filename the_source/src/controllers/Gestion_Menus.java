package controllers;

import javax.servlet.http.HttpServletRequest;

import models.Menu;
import models.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import services.Serv_Menu;
import services.Serv_Url;

@Controller
@RequestMapping("/menus/*")
public class Gestion_Menus {
	
	@Autowired private Serv_Url serv_Url;
	@Autowired private Serv_Menu serv_Menu;
	
	@RequestMapping("gestion_menus")
	public String gestion_menus(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("menus", serv_Menu.get_All());
			return "menus/gestion_menus";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("adicionar_menu")
	public String adicionar_menu(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "menus/adicionar_menu";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("guardar_menu")
	public String guardar_menu(HttpServletRequest request, Model model, @ModelAttribute("me") Menu me){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Menu.add(me);
			return "redirect:../menus/gestion_menus";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("modificar_menu")
	public String modificar_menu(HttpServletRequest request, Model model, Integer id_men){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("me", serv_Menu.get_by_Id(id_men));
			return "menus/modificar_menu";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("actualizar_menu")
	public String actualizar_menu(HttpServletRequest request, Model model, @ModelAttribute("me") Menu me){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Menu.modify(me);
			return "redirect:../menus/gestion_menus";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("eliminar_menu")
	public String eliminar_menu(HttpServletRequest request, Model model, Integer id_men){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Menu.delete(id_men);
			return "redirect:../menus/gestion_menus";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("asignar_urls")
	public String asignar_urls(HttpServletRequest request, Model model, Integer id_men){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("me", serv_Menu.get_by_Id(id_men));
			model.addAttribute("urls", serv_Url.get_Urls_without_Assign(id_men));
			return "menus/asignar_urls";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("guardar_urls")
	public String guardar_urls(HttpServletRequest request, Model model, Integer id_men, Integer[] id_url){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Menu.delete_from_men_url(id_men);
			if(id_url!=null){
				for(int i: id_url) serv_Menu.assing_url_to_menu(id_men, i);
			}
			return "redirect:../menus/gestion_menus";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
}