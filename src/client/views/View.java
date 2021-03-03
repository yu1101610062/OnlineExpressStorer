package client.views;

import client.bean.Express;

import java.util.Scanner;

/**
 * 视图类
 *
 * @author yuzha
 */
public class View {
	public final Scanner scanner = new Scanner(System.in);

	public void verifyError(){
		System.out.println("认证失败！");
	}

	public void connectError(){
		System.out.println("连接失败！");
	}

	public void welcome() {
		System.out.println("欢迎使用xxx快递柜");
	}

	public void bye() {
		System.out.println("感谢您选择XXX快递柜，再见");
	}

	public void failure() {
		System.out.println("操作有误，请重新输入");
	}

	public void success() {
		System.out.println("操作成功");
	}

	public void equals() {
		System.out.println("快递单号重复！");
	}

	public void full() {
		System.out.println("空间不足！");
	}

	public void printAllExpress() {
		System.out.println("以下显示所有快递，如不显示则代表空");
	}

	public void printExpress(String str) {
		System.out.println(str);
	}

	public void clearBuff() {
		scanner.nextLine();
	}

	public int mainMenu() {
		System.out.println("请输入身份（1-用户 2-快递员 0-退出）");
		return scanner.nextInt();
	}

	public int userMenu() {
		System.out.println("请输入取件码：");
		return scanner.nextInt();
	}

	public int adminMenu() {
		System.out.println("请选择操作（1-录入快递 2-删除快递 3-修改快递 4-查看所有快递 0-退出）");
		return scanner.nextInt();
	}

	public Express add() {
		Express express = new Express();
		System.out.println("请输入新的快递单号：");
		express.setExpressId(scanner.next());
		System.out.println("请输入新的快递公司：");
		express.setExpressCom(scanner.next());
		return express;
	}

	public String doSth() {
		System.out.println("请输入要操作的快递单号：");
		String id = scanner.next();
		System.out.println("确认继续吗？此操作不可恢复！！！（true-确定 false-取消）");
		if (!scanner.nextBoolean()) {
			return null;
		}
		return id;
	}
}
