package client.controler;

import client.bean.Express;
import client.bean.Final;
import client.dao.ExpressDao;
import client.views.View;

import java.util.LinkedList;

/**
 * @author yuzha
 */
public class ExpressMain {

	/**
	 * 初始化视图类对象
	 * 初始化数据操作类对象
	 */
	static final View VIEW = new View();
	static final ExpressDao EXPRESS_DAO = new ExpressDao();

	/**
	 * 程序入口调用mainSelect()方法显示主菜单
	 */
	public static void main(String[] args) {
		VIEW.welcome();
		while (true) {
			try {
				mainSelect();
				break;
			} catch (Exception e) {
				VIEW.failure();
				VIEW.clearBuff();
			}
		}
		VIEW.bye();
	}

	/**
	 * 主菜单
	 */
	private static void mainSelect() {
		while (true) {
			switch (VIEW.mainMenu()) {
				case 1:
					userSelect();
					break;
				case 2:
					adminSelect();
					break;
				case 0:
					return;
				default:
					VIEW.failure();
					break;
			}
		}
	}

	/**
	 * 快递员菜单
	 */
	private static void adminSelect() {
		while (true) {
			try {
				switch (VIEW.adminMenu()) {
					case 1:
						addSelect();
						break;
					case 2:
						delSelect();
						break;
					case 3:
						editSelect();
						break;
					case 4:
						viewSelect();
						break;
					case 0:
						return;
					default:
						VIEW.failure();
						break;
				}
			} catch (Exception e) {
				VIEW.failure();
				VIEW.clearBuff();
			}
		}
	}

	/**
	 * 快递员浏览所有快递
	 */
	private static void viewSelect() {
		LinkedList<String> ls = EXPRESS_DAO.viewExpress();
		if (ls == null){
			VIEW.connectError();
			return;
		}
		VIEW.printAllExpress();
		for (String str : ls) {
			VIEW.printExpress(str);
		}
	}

	/**
	 * 快递员修改快递
	 * 通过删除再重新添加的逻辑实现
	 */
	private static void editSelect() {
		if(!delSelect()){
			return;
		}
		addSelect();
	}

	/**
	 * 快递员删除快递
	 */
	private static boolean delSelect() {
		String str = VIEW.doSth();
		if (str == null) {
			return true;
		}
		String s = EXPRESS_DAO.deleteExpress(str);
		if (s == null){
			VIEW.connectError();
			return false;
		}
		if (Final.FALSE.equals(s)) {
			VIEW.failure();
			return true;
		}
		VIEW.success();
		return true;
	}

	/**
	 * 快递员添加快递
	 */
	private static void addSelect() {
		Express express = VIEW.add();
		String pos = EXPRESS_DAO.addExpress(express);
		switch (pos) {
			case Final.EQUALS:
				VIEW.equals();
				break;
			case Final.FULL:
				VIEW.full();
				break;
			case Final.CONNECTED_ERROR:
				VIEW.connectError();
				break;
			case Final.VERIFY_ERROR:
				VIEW.verifyError();
				break;
			default:
				VIEW.success();
				VIEW.printExpress(pos);
		}
	}

	/**
	 * 用户菜单
	 */
	private static void userSelect() {
		String str = EXPRESS_DAO.userExpress(VIEW.userMenu());
		if (str == null){
			VIEW.connectError();
			return;
		}
		if (Final.FALSE.equals(str)) {
			VIEW.failure();
			return;
		}
		VIEW.success();
	}
}
