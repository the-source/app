package controllers;

import javax.servlet.http.HttpServletRequest;

import models.Categoria;
import models.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import services.Serv_Categoria;

@Controller
@RequestMapping("/categorias/*")
public class Gestion_Categorias {
	
	@Autowired private Serv_Categoria serv_Categoria;
	
	@RequestMapping("gestion_categorias")
	public String gestion_categorias(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("categorias", serv_Categoria.get_All());
			return "categorias/gestion_categorias";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("adicionar_categoria")
	public String adicionar_categoria(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "categorias/adicionar_categoria";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("guardar_categoria")
	public String guardar_categoria(HttpServletRequest request, Model model, @ModelAttribute("ca") Categoria ca){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Categoria.add(ca);
			return "redirect:../categorias/gestion_categorias";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("modificar_categoria")
	public String modificar_categoria(HttpServletRequest request, Model model, Integer id_cat){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("ca", serv_Categoria.get_by_Id(id_cat));
			return "categorias/modificar_categoria";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("actualizar_categoria")
	public String actualizar_categoria(HttpServletRequest request, Model model, @ModelAttribute("ca") Categoria ca){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Categoria.modify(ca);
			return "redirect:../categorias/gestion_categorias";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("eliminar_categoria")
	public String eliminar_url(HttpServletRequest request, Model model, Integer id_cat){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Categoria.delete(id_cat);
			return "redirect:../categorias/gestion_categorias";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
}