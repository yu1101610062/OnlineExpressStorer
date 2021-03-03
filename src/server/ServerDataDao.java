package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author yyzy
 */
public class ServerDataDao {
	private LinkedList<Express> expresses;
	private final ServerFileDao serverFileDao;
	private final String devicesId;

	/**
	 * 构造方法
	 * 每个线程（不同快递柜---用设备id区分）必须传入独立的文件、数据类和设备id
	 * */
	public ServerDataDao(LinkedList<Express> expresses, ServerFileDao serverFileDao, String devicesId) {
		this.expresses = expresses;
		this.serverFileDao = serverFileDao;
		this.devicesId = devicesId;
	}

	/**
	 * 从文件中读取数据
	 */
	public void loadFile() {
		try {
			expresses = serverFileDao.loadFile(devicesId);
		} catch (IOException ignored) {
		}
	}

	/**
	 * 将数据存入文件
	 */
	public void storeFile() {

		try {
			serverFileDao.store(expresses);
		} catch (FileNotFoundException ignored) {
		}
	}

	/**
	 * 客户端认证
	 * */
	public boolean verify(String ip) throws IOException {
		return serverFileDao.verifyFile(devicesId,ip);
	}

	/**
	 * 添加快递的实现方法
	 * 先调用私有成员方法idEquals判断id是否相同
	 * 之后存入快递并调用setCode与setPos生成取件码和位置
	 * setPos返回位置满时，返回错误信息；
	 */
	public String addExpress(Express express) {
		if (idEquals(express)) {
			return Final.EQUALS;
		} else if (!setPos(express)) {
			return Final.FULL;
		} else {
			setCode(express);
			expresses.add(express);
			return Final.DONE;
		}
	}

	/**
	 * 用户取件,得益于removeIf方法，我们可以用一行代码直接搞定用户取件
	 */
	public boolean userExpress(int code) {
		return expresses.removeIf(exp -> exp.getExpressCode() == code);
	}

	/**
	 * 删除方法，得益于removeIf方法，我们可以用一行代码直接搞定删除功能
	 */
	public boolean deleteExpress(String str) {
		return expresses.removeIf(exp -> exp.getExpressId().equals(str));
	}

	/**
	 * 返回刚添加的快递的信息
	 * */
	public String returnInfo(){
		return expresses.getLast().toString();
	}

	/**
	 * 查询方法
	 */
	public LinkedList<Express> viewExpress() {
		return expresses;
	}

	/**
	 * 私有化查重方法
	 */
	private boolean idEquals(Express express) {
		for (Express e : expresses) {
			if (express.getExpressId().equals(e.getExpressId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 私有化取件码生成方法
	 * 这里会使用全局静态常量类中规定的最大快递柜容量判断快递柜是否已满
	 * 只有未满时才会调用setCode设置取件码
	 * <p>
	 * 由于采用链表结构，所以此方法有待优化，已知的问题时储存的快递越满，消耗处理器资源越大
	 */
	private boolean setPos(Express express) {
		int count = 0, pos;
		pos:
		while (true) {
			pos = new Random().nextInt(Final.MAX_SIZE);
			for (Express e : expresses) {
				if (e.getExpressPos() == pos) {
					++count;
					continue pos;
				}
			}
			if (count == Final.MAX_SIZE) {
				return false;
			} else {
				express.setExpressPos(pos);
				return true;
			}
		}
	}

	/**
	 * 私有化快递位置生成方法，此方法一旦调用一定执行成功
	 * 由于六位随机数相同的概率极小，所以此方法大多数情况只需要遍历一遍快递柜
	 */
	private void setCode(Express express) {
		int code;
		code:
		while (true) {
			code = new Random().nextInt(900000) + 100000;
			for (Express e : expresses) {
				if (e.getExpressCode() == code) {
					continue code;
				}
			}
			express.setExpressCode(code);
			return;
		}
	}


}
