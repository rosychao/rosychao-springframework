package org.rosychao.springframework.test.action;

import org.rosychao.springframework.beans.factory.annotation.Autowired;
import org.rosychao.springframework.stereotype.Service;
import org.rosychao.springframework.test.service.IQueryService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 查询业务
 * @author Tom
 *
 */
@Service
public class QueryService implements IQueryService {

	//@Autowired
	//private IModifyService modifyService;

	@Autowired
	private MyAction myAction;

	/**
	 * 查询
	 */
	public String query(String name) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		String json = "{name:\"" + name + "\",time:\"" + time + "\"}";
		System.out.println("这是在业务方法中打印的：" + json);
		return json;
	}

}
