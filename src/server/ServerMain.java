package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * @author yyzy
 */
public class ServerMain {
	public static String currentID;
	private static boolean firstThread = false;
	private static final ThreadPoolExecutor SERVICE = new ThreadPoolExecutor(
			10,
			100,
			5,
			TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(10),
			(ThreadFactory) Thread::new);

	public static void main(String[] args) {
		try {

			ServerSocket socket = new ServerSocket(6789);
			while (true) {
				if (!firstThread){
					SERVICE.execute(() -> {
						while (true) {
							if (Final.EXIT.equals(new Scanner(System.in).nextLine())) {
								SERVICE.shutdown();
								System.out.println("服务器已强制关闭");
								System.exit(0);
							}
						}
					});
				}
				firstThread = true;
				Socket s = socket.accept();
				SERVICE.execute(() -> {
					try {
						//认证并返回认证结果
						InputStream is1 = s.getInputStream();
						BufferedReader br = new BufferedReader(new InputStreamReader(is1));
						currentID = br.readLine();
						ServerDataDao sdd = new ServerDataDao(new LinkedList<>(), new ServerFileDao(), currentID);
						OutputStream os = s.getOutputStream();
						PrintStream ps = new PrintStream(os);
						String s1 = "true";
						if (!sdd.verify(s.getInetAddress().toString())) {
							s1 = "false";
							ps.println(s1);
							s.close();
							return;
						}
						ps.println(s1);
						//打开文件流
						sdd.loadFile();
						//储存运行结果
						boolean b;
						//判断要执行的功能
						switch (br.readLine()) {
							case Final.ADD:
								String id = br.readLine();
								String com = br.readLine();
								Express e = new Express(id, com);
								String str = sdd.addExpress(e);
								ps.println(str);
								if (Final.DONE.equals(str)) {
									ps.println(sdd.returnInfo());
								}
								break;
							case Final.OUT:
								b = sdd.userExpress(Integer.parseInt(br.readLine()));
								ps.println(b);
								break;
							case Final.DEL:
								b = sdd.deleteExpress(br.readLine());
								ps.println(b);
								break;
							case Final.FIND:
								LinkedList<Express> expresses = sdd.viewExpress();
								ps.println(expresses.size());
								for (Express express : expresses) {
									ps.println(express);
								}
								break;
							default:
								break;
						}
						//关闭文件流并保存
						sdd.storeFile();
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
