package org.rosychao.springframework.test.action;

import org.rosychao.springframework.beans.factory.annotation.Autowired;
import org.rosychao.springframework.stereotype.Service;
import org.rosychao.springframework.test.service.IModifyService;
import org.rosychao.springframework.test.service.IQueryService;

/**
 * 增删改业务
 * @author Tom
 *
 */
@Service
public class ModifyService implements IModifyService {

	@Autowired
	private IQueryService queryService;
	//@Autowired private IAddService addService;

	/**
	 * 增加
	 */
	public String add(String name,String addr) {
		return "modifyService add,name=" + name + ",addr=" + addr;
	}

	/**
	 * 修改
	 */
	public String edit(Integer id,String name) {
		return "modifyService edit,id=" + id + ",name=" + name;
	}

	/**
	 * 删除
	 */
	public String remove(Integer id) {
		return "modifyService id=" + id;
	}
	
}
