package controllers;

import javax.servlet.http.HttpServletRequest;

import models.Rol;
import models.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import services.Serv_Menu;
import services.Serv_Rol;

@Controller
@RequestMapping("/roles/*")
public class Gestion_Roles {
	
	@Autowired private Serv_Rol serv_Rol;
	@Autowired private Serv_Menu serv_Menu;
	
	@RequestMapping("gestion_roles")
	public String gestion_roles(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("roles", serv_Rol.get_All());
			return "roles/gestion_roles";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("adicionar_rol")
	public String adicionar_rol(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "roles/adicionar_rol";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("guardar_rol")
	public String guardar_rol(HttpServletRequest request, Model model, @ModelAttribute("rol") Rol rol){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Rol.add(rol);
			return "redirect:../roles/gestion_roles";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("modificar_rol")
	public String modificar_rol(HttpServletRequest request, Model model, Integer id_rol){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("rol", serv_Rol.get_by_Id(id_rol));
			return "roles/modificar_rol";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("actualizar_rol")
	public String actualizar_rol(HttpServletRequest request, Model model, @ModelAttribute("rol") Rol rol){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Rol.modify(rol);
			return "redirect:../roles/gestion_roles";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("eliminar_rol")
	public String eliminar_rol(HttpServletRequest request, Model model, Integer id_rol){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Rol.delete(id_rol);
			return "redirect:../roles/gestion_roles";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("asignar_menus")
	public String asignar_menus(HttpServletRequest request, Model model, Integer id_rol){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("rol", serv_Rol.get_by_Id(id_rol));
			model.addAttribute("menus", serv_Menu.get_Menus_without_assign_to_Menu(id_rol));
			return "roles/asignar_menus";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("guardar_menus")
	public String guardar_menus(HttpServletRequest request, Model model, Integer id_rol, Integer[] id_men){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Rol.delete_from_rol_men(id_rol);
			if(id_men!=null){
				for(int i: id_men) serv_Rol.assign_menu_to_rol(id_rol, i);
			}
			return "redirect:../roles/gestion_roles";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
}