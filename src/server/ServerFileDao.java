package server;

import java.io.*;
import java.util.LinkedList;

/**
 * @author yyzy
 */
public class ServerFileDao {
	File file;
	File verifyFile = new File("verify.dat");

	/**
	 * 将文件信息一行一行读入到链表中
	 */
	public LinkedList<Express> loadFile(String devicesId) throws IOException {
		LinkedList<Express> expresses = new LinkedList<>();
		String str;
		file = new File(devicesId+".dat");
		file.createNewFile();
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		try {
			while (true) {
				str = br.readLine();
				if (str == null) {
					break;
				}
				String expId = str.substring(str.indexOf("#") + 1, str.lastIndexOf("#"));
				String expCom = str.substring(str.indexOf("$") + 1, str.lastIndexOf("$"));
				int expCode = Integer.parseInt(str.substring(str.indexOf("%") + 1, str.lastIndexOf("%")));
				int expPos = Integer.parseInt(str.substring(str.indexOf("!") + 1, str.lastIndexOf("!")));
				expresses.add(new Express(expId, expCom, expCode, expPos));
			}
		} catch (IOException e) {
			PrintWriter pw = new PrintWriter("bug.log");
			e.printStackTrace(pw);
			pw.close();
		}
		return expresses;
	}

	/**
	 * 将传入的链表存入到文件中
	 */
	public void store(LinkedList<Express> expresses) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(file);
		for (Express express : expresses) {
			String str = express.fileString();
			pw.println(str);
		}
		if (expresses.size()!=0){
			pw.close();
		}
	}

	/**
	 * 读取认证文件
	 * */
	public boolean verifyFile(String devicesID,String IP) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(verifyFile));
		while (true){
			String str = br.readLine();
			if (str == null){
				break;
			}
			String id = str.substring(str.indexOf("#") + 1, str.lastIndexOf("#"));
			String ip = str.substring(str.indexOf("$") + 1, str.lastIndexOf("$"));
			if (devicesID.equals(id)&&IP.equals(ip)){
				return true;
			}
		}
		return false;
	}
}
