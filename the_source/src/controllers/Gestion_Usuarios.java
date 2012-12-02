package controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import models.Rol;
import models.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import services.Serv_Rol;
import services.Serv_Usuario;

@Controller
@RequestMapping("/usuarios/*")
public class Gestion_Usuarios {
	
	@Autowired private Serv_Usuario serv_Usuario;
	@Autowired private Serv_Rol serv_Rol;
	
	@RequestMapping("gestion_usuarios")
	public String gestion_usuarios(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			List<Usuario> usuarios = new ArrayList<>();
			for(Usuario us: serv_Usuario.get_All()){
				us.setRol(serv_Rol.get_by_Id(us.getId_rol()));
				usuarios.add(us);
			}
			model.addAttribute("usuarios", usuarios);
			return "usuarios/gestion_usuarios";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("adicionar_usuario")
	public String adicionar_usuario(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			List<Rol> roles = new ArrayList<>();
			for(Rol rol: serv_Rol.get_All()){
				if(rol.getId_rol()!=5) roles.add(rol);
			}
			model.addAttribute("roles", roles);
			return "usuarios/adicionar_usuario";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("guardar_usuario")
	@SuppressWarnings("deprecation")
	public String guardar_usuarios(HttpServletRequest request, Model model, @ModelAttribute("us") Usuario us, @RequestParam("file") MultipartFile file){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			try {
				if(!file.isEmpty()){
					FileOutputStream out = new FileOutputStream(request.getRealPath("images")+"/users_photo/"+file.getOriginalFilename());
					out.write(file.getBytes()); out.close();
					us.setFoto(file.getOriginalFilename().toString());
				}
				us.setVerificado(true);
				serv_Usuario.add(us);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:../usuarios/gestion_usuarios";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("modificar_usuario")
	public String modificar_usuario(HttpServletRequest request, Model model, Integer id_us){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("us", serv_Usuario.get_by_Id(id_us));
			model.addAttribute("roles", serv_Rol.get_All());
			return "usuarios/modificar_usuario";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("actualizar_usuario")
	@SuppressWarnings("deprecation")
	public String actualizar_usuario(HttpServletRequest request, Model model, @ModelAttribute("us") Usuario us, @RequestParam("file") MultipartFile file){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			try {
				if(!file.isEmpty()){
					new File(request.getRealPath("images")+"/users_photo/"+us.getFoto()).delete();
					FileOutputStream out = new FileOutputStream(request.getRealPath("images")+"/users_photo/"+file.getOriginalFilename());
					out.write(file.getBytes()); out.close();
					us.setFoto(file.getOriginalFilename().toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			serv_Usuario.modify(us);
			return "redirect:../usuarios/gestion_usuarios";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("eliminar_usuario")
	@SuppressWarnings("deprecation")
	public String eliminar_usuario(HttpServletRequest request, Model model, Integer id_us){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			new File(request.getRealPath("images")+"/users_photo/"+serv_Usuario.get_by_Id(id_us).getFoto()).delete();
			serv_Usuario.delete(id_us);
			return "redirect:../usuarios/gestion_usuarios";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
}