

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
			throw new IOException(fromfile + "�����ڣ�����");
		}
		if (!fromfile.isFile()) {
			throw new IOException(fromfile + "�����ļ�����");
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
			System.out.println("���ɵڣ�" + (i+1) + "���ļ�");
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
		System.out.println("�ļ������ɣ�");
	}
	//����   �ϲ��ļ�    ������֪�ļ���
     public void mergefile(String dirname, String finalfile) throws IOException{
		File dir = new File(dirname);
		if (!dir.exists()) {
			throw new IOException(dir + "�����ڣ���");
		}
		if (!dir.isDirectory()) {
			throw new IOException(dir + "�����ļ���");
		}
		FileOutputStream out = new FileOutputStream(finalfile, true);
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(out));
		int i = 1;
		String[] files = dir.list();
		
		for(String string: files) {
			System.out.println("�ϲ����ڣ�" + i++ + "���ļ�");
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
		System.out.println("�ļ��ϲ���ɣ�");
			
	}   
	
}
