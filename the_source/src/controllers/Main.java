package controllers;

import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import models.Punto;
import models.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import services.Serv_Punto;
import services.Serv_Rol;
import services.Serv_Usuario;

@Controller
@RequestMapping("/main/*")
public class Main {
	
	@Autowired private Serv_Usuario serv_Usuario;
	@Autowired private Serv_Rol serv_Rol;
	@Autowired private Serv_Punto serv_Punto;
	@RequestMapping("index")
	public String index(Model model){
		return "main/index";
	}
	
	@RequestMapping("validate")
	public String validate(HttpServletRequest request, String nombre_us, String clave_us){
		try {
			Usuario user = serv_Usuario.start_session(nombre_us, clave_us);
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:../main/post_validate";
	}
	
	@RequestMapping("post_validate")
	public String post_validate(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("user", user);
			model.addAttribute("rol", serv_Rol.get_by_Id(user.getId_rol()));
			return "main/post_validate";
		}
		else{
			return "redirect:../main/index";
		}
		
	}
	
	@RequestMapping("logout")
	public String logout(HttpServletRequest request, Model model){
		request.getSession(false).invalidate();
		return "redirect:../main/index";
	}
	
	@RequestMapping("create_account")
	@SuppressWarnings("deprecation")
	public String create_account(HttpServletRequest request, Model model, @ModelAttribute("us") Usuario us, @RequestParam("file") MultipartFile file){
		//Usuario user = (Usuario)request.getSession().getAttribute("user");
		//if(user!=null){
			try {
				if(!file.isEmpty()){
					FileOutputStream out = new FileOutputStream(request.getRealPath("images")+"/users_photo/"+file.getOriginalFilename());
					out.write(file.getBytes()); out.close();
					us.setFoto(file.getOriginalFilename().toString());
				}
				serv_Usuario.add(us);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:../main/index";
		//}
		//else{
			//model.addAttribute("msg", "No tiene privilegios o inicie sesión..!");
			//return "main/messages";
		//}
	}
	
	@RequestMapping("default_map")
	public String default_map(HttpServletRequest request, Model model, Integer id_eve, Integer ver){
		model.addAttribute("id_eve", id_eve);
		if(ver!=null){ 
			model.addAttribute("coord", serv_Punto.get_By_evento(id_eve));
		}
		return "main/default_map";
	}
	
	@RequestMapping(value="add_point", method=RequestMethod.POST)
	public String add_point(String x, String y, int id_eve){
		String X[]=x.replace("null", "").split(",");
		String Y[]=y.replace("null", "").split(",");
		for(int i=0;i<X.length;i++){
			System.out.println(X[i] +" "+Y[i]);
		}
		Punto punto=new Punto();
		
		punto.setId_eve(id_eve);
		serv_Punto.add(punto, X, Y);
		
		return "redirect:../main/post_validate";
	}
}