

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
public class DoFile {
	
	public int getrows(File file) throws IOException{
	    int rows = 0;
	    BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(file)));
	    for (rows = 0; br.readLine() != null; rows++);
		br.close();
		return rows;
	}
	
	public void splitfile(String from, String to, int subfilenum) throws IOException{
		File fromfile = new File(from);
		
		if (!fromfile.exists()) {
			throw new IOException(fromfile + "不存在！！！");
		}
		if (!fromfile.isFile()) {
			throw new IOException(fromfile + "不是文件！！");
		}
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(fromfile)));
		
		String str = null;
		int filerows = getrows(fromfile);
		int subrows = (int) Math.ceil(1.0 * filerows / subfilenum);

		for (int i = 0; i < subfilenum; i++) {

			PrintWriter pw = new PrintWriter(to + "-" + i + ".dat");
			int row = 0;
			System.out.println("生成第：" + (i+1) + "个文件");
			while(row < subrows) {
				
				if ((str = br.readLine()) != null) {
					pw.println(str);
					row++;
				}else {
					pw.flush();
					pw.close();
					break;
				}
				
			}
			pw.flush();
			pw.close();
		}

		br.close();
		System.out.println("文件拆分完成！");
	}
	//按行   合并文件    给定已知文件夹
     public void mergefile(String dirname, String finalfile) throws IOException{
		File dir = new File(dirname);
		if (!dir.exists()) {
			throw new IOException(dir + "不存在！！");
		}
		if (!dir.isDirectory()) {
			throw new IOException(dir + "不是文件夹");
		}
		FileOutputStream out = new FileOutputStream(finalfile, true);
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(out));
		int i = 1;
		String[] files = dir.list();
		
		for(String string: files) {
			System.out.println("合并到第：" + i++ + "个文件");
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(dir + "/"+ string)));
			String str = null;
			while((str = br.readLine()) != null) {
				bw.write(str);
				bw.newLine();
			}
			br.close();
		}
		bw.flush();
		bw.close();
		System.out.println("文件合并完成！");
			
	}   
	
}
