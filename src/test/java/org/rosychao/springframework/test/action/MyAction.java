package org.rosychao.springframework.test.action;

import org.rosychao.springframework.beans.factory.annotation.Autowired;
import org.rosychao.springframework.stereotype.Controller;
import org.rosychao.springframework.test.service.IModifyService;
import org.rosychao.springframework.test.service.IQueryService;
import org.rosychao.springframework.web.bind.annotation.RequestMapping;

/**
 * 公布接口url
 * @author Tom
 *
 */
@Controller
@RequestMapping("/web")
public class MyAction {

	@Autowired
	IQueryService queryService;
	@Autowired
	IModifyService modifyService;

	/*@RequestMapping("/query.json")
	public void query(HttpServletRequest request, HttpServletResponse response,
					  @RequestParam("name") String name){
		String result = queryService.query(name);
		out(response,result);
	}
	
	@RequestMapping("/add*.json")
	public void add(HttpServletRequest request,HttpServletResponse response,
			   @GPRequestParam("name") String name,@GPRequestParam("addr") String addr){
		String result = modifyService.add(name,addr);
		out(response,result);
	}
	
	@GPRequestMapping("/remove.json")
	public void remove(HttpServletRequest request,HttpServletResponse response,
		   @GPRequestParam("id") Integer id){
		String result = modifyService.remove(id);
		out(response,result);
	}
	
	@GPRequestMapping("/edit.json")
	public void edit(HttpServletRequest request,HttpServletResponse response,
			@GPRequestParam("id") Integer id,
			@GPRequestParam("name") String name){
		String result = modifyService.edit(id,name);
		out(response,result);
	}
	
	
	
	private void out(HttpServletResponse resp,String str){
		try {
			resp.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
}
