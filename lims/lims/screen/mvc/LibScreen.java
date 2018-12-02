package lims.screen.mvc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import lims.screen.services.LibService;

@Controller
@RequestMapping("lims/screen/lib")
public class LibScreen {
	
	@Autowired
	private LibService service;
	
	@RequestMapping
	public String index(HttpServletRequest request){
		//查询所有的待接收样品信息
		request.setAttribute("toReceive", service.toReceive());
		request.setAttribute("testing", service.testing());
		List<Map<String,Object>> list = service.approval();
		request.setAttribute("approval",list);
		
		//消息通知
		request.setAttribute("xxtz",service.xxtg());
		
		return "lims/screen/lib";
	}
}
