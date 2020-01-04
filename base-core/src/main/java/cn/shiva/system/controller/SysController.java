package cn.shiva.system.controller;

import cn.shiva.core.base.BaseController;
import cn.shiva.core.constant.BaseConstant;
import cn.shiva.core.utils.SysUtils;
import cn.shiva.core.utils.properties.PropUtil;
import cn.shiva.system.entity.Menu;
import cn.shiva.system.entity.Role;
import cn.shiva.system.entity.User;
import cn.shiva.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * @author shiva
 */
@Controller
@RequestMapping(value = "${adminPath}")
public class SysController extends BaseController {

	@Autowired
	private MenuService menuService;
	
	/**
	 * 基础架构页面
	* @return String  
	* @author shiva
	 */
	@RequestMapping(value= {"/index",""})
	public String index(Model model) {
		User user = SysUtils.getUser();
		List<Role> roleList = user.getRoleList();
		//没有角色不允许登陆
		if (roleList == null || roleList.size() == 0){
			return "redirect:/logout";
		}
		if (!BaseConstant.SUPER_ADMIN_ID.equals(user.getId())){
			model.addAttribute("menus",packetMenu(roleList));
		}else {
			//超级管理员账号
			model.addAttribute("menus",adminMenu());
		}
		model.addAttribute("user",user);
		model.addAttribute("console", PropUtil.get("maybe.consolePath"));
		return "system/sysIndex";
	}


	@RequestMapping(value = "/iconSelect")
	public String iconSelect() {
		return "system/iconSelect";
	}


	/**
	 * 根据角色列表，获取所有权限
	 */
	private List<Menu> packetMenu(List<Role> roleList){
		List<Menu> menus = new ArrayList<Menu>();

		Map<Long, Menu> maps = new HashMap<Long, Menu>();
		//先添加到map<id,menu>中
		for (Role role : roleList) {
			if (role.getMenuList().size() != 0){
				for (Menu menu : role.getMenuList()) {
					maps.put(menu.getId(), menu);
				}
			}
		}

		for (Long key : maps.keySet()) {
			menus.add(maps.get(key));
		}

		return comparatorMenu(menus);
	}

	/**
	 * 查询全部菜单
	 */
	private List<Menu> adminMenu(){
		return comparatorMenu(SysUtils.getAllMenuList());
	}

	/**
	 * 对菜单列表进行排序
	 */
	private List<Menu> comparatorMenu(List<Menu> menus){
		Collections.sort(menus, new Comparator<Menu>() {
			@Override
			public int compare(Menu o1, Menu o2) {
				try {
					if (o1.getPids().split(BaseConstant.SPLIT_SYMBOL).length <= o2.getPids().split(BaseConstant.SPLIT_SYMBOL).length){
						return 1;
					}else if (o1.getSort() <= o2.getSort()){
						return 1;
					}else {
						return -1;
					}

				} catch (Exception e) {
					e.printStackTrace();
					return 0;
				}
			}
		});

		return menus;
	}

}
