package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import models.Evento;
import models.Topico;
import models.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import services.Serv_Evento;
import services.Serv_Topico;
import services.Serv_Usuario;

@Controller
@RequestMapping("/eventos/*")
public class Gestion_Eventos {
	
	@Autowired private Serv_Evento serv_Evento;
	@Autowired private Serv_Topico serv_Topico;
	@Autowired private Serv_Usuario serv_Usuario;
	
	@RequestMapping("gestion_eventos")
	public String gestion_eventos(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			List<Evento> eventos = new ArrayList<>();
			for(Evento ev: serv_Evento.get_All()){
				ev.setUsuario(serv_Usuario.get_by_Id(ev.getId_us()));
				eventos.add(ev);
			}
			model.addAttribute("eventos", eventos);
			return "eventos/gestion_eventos";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping(value="topicos", method=RequestMethod.GET)
	public @ResponseBody List<Topico> topicos(Integer tipo){
		if(tipo==0) return serv_Topico.get_All_tipo(false);
		else return serv_Topico.get_All_tipo(true);
	}
	
	@RequestMapping(value="eventos", method=RequestMethod.GET)
	public @ResponseBody List<Evento> eventos(){
		return serv_Evento.get_All();
	}
	
	@RequestMapping(value="eventosbyt", method=RequestMethod.GET)
	public @ResponseBody List<Evento> eventos(Integer id_top){
		System.out.println(id_top+"topico");
		return serv_Evento.get_All_by_Topico(id_top);
	}
	
	@RequestMapping("adicionar_evento")
	public String adicionar_evento(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("topicos", serv_Topico.get_All());
			return "eventos/adicionar_evento";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("guardar_evento")
	public String guardar_evento(HttpServletRequest request, Model model, @ModelAttribute("ev") Evento ev){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			ev.setTopico(serv_Topico.get_by_Id(ev.getId_top()));
			ev.setId_us(user.getId_us());
			serv_Evento.add(ev);
			return "redirect:../eventos/gestion_eventos";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("modificar_evento")
	public String modificar_evento(HttpServletRequest request, Model model, Integer id_eve){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("ev", serv_Evento.get_by_Id(id_eve));
			return "eventos/modificar_evento";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("actualizar_evento")
	public String actualizar_evento(HttpServletRequest request, Model model, @ModelAttribute("ev") Evento ev){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Evento.modify(ev);
			return "redirect:../eventos/gestion_eventos";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
	
	@RequestMapping("eliminar_evento")
	public String eliminar_url(HttpServletRequest request, Model model, Integer id_eve){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Evento.delete(id_eve);
			return "redirect:../eventos/gestion_eventos";
		}
		else{
			model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			return "main/messages";
		}
	}
}