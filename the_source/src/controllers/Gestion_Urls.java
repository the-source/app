package controllers;

import javax.servlet.http.HttpServletRequest;

import models.Url;
import models.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import services.Serv_Url;

@Controller
@RequestMapping("/urls/*")
public class Gestion_Urls {
	
	@Autowired private Serv_Url serv_Url;
	
	@RequestMapping("gestion_urls")
	public String gestion_urls(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("urls", serv_Url.get_All());
			return "urls/gestion_urls";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("adicionar_url")
	public String adicionar_url(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "urls/adicionar_url";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("guardar_url")
	public String guardar_url(HttpServletRequest request, Model model, @ModelAttribute("url") Url url){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Url.add(url);
			return "redirect:../urls/gestion_urls";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("modificar_url")
	public String modificar_url(HttpServletRequest request, Model model, Integer id_url){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("url", serv_Url.get_by_Id(id_url));
			return "urls/modificar_url";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("actualizar_url")
	public String actualizar_url(HttpServletRequest request, Model model, @ModelAttribute("url") Url url){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Url.modify(url);
			return "redirect:../urls/gestion_urls";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("eliminar_url")
	public String eliminar_url(HttpServletRequest request, Model model, Integer id_url){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Url.delete(id_url);
			return "redirect:../urls/gestion_urls";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
}