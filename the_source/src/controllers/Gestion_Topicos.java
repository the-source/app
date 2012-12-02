package controllers;

import javax.servlet.http.HttpServletRequest;

import models.Topico;
import models.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import services.Serv_Topico;

@Controller
@RequestMapping("/topicos/*")
public class Gestion_Topicos {
	
	@Autowired private Serv_Topico serv_Topico;
	
	@RequestMapping("gestion_topicos")
	public String gestion_topicos(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("topicos", serv_Topico.get_All());
			return "topicos/gestion_topicos";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("adicionar_topico")
	public String adicionar_topico(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "topicos/adicionar_topico";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("guardar_topico")
	public String guardar_topico(HttpServletRequest request, Model model, @ModelAttribute("tp") Topico tp){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Topico.add(tp);
			return "redirect:../topicos/gestion_topicos";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("modificar_topico")
	public String modificar_topico(HttpServletRequest request, Model model, Integer id_top){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("tp", serv_Topico.get_by_Id(id_top));
			return "topicos/modificar_topico";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("actualizar_topico")
	public String actualizar_topico(HttpServletRequest request, Model model, @ModelAttribute("tp") Topico tp){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Topico.modify(tp);
			return "redirect:../topicos/gestion_topicos";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("eliminar_topico")
	public String eliminar_topico(HttpServletRequest request, Model model, Integer id_top){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Topico.delete(id_top);
			return "redirect:../topicos/gestion_topicos";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
}