
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DoIt {
	List<String> wordlist;
	List<String> labellist;
	
	public DoIt() {
		wordlist = new ArrayList<String>();
		labellist = new ArrayList<String>();
	}
	
	public int[] creatlist(String train) throws IOException{
		System.out.println("----- 创建List -----");
		System.out.println(".......... . .  .");
		File file = new File(train);
		int[] twonum = new int[2];
		if(!file.exists()) {
			throw new IOException(file + "不存在！！！");
		}
		if(!file.isFile()) {
			throw new IOException(file + "不是文件！！！");
		}
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(file)));
		String str = null;
		while((str = br.readLine()) != null) {
			String[] strarray = str.split(" ");
			for (String substr: strarray) {
				String[] tempstr = substr.split("/");
				if (tempstr.length == 2) {
					String word = tempstr[0];
					String label = tempstr[1];
					if(!wordlist.contains(word)) {
						wordlist.add(word);
					}
					if(!labellist.contains(label)) {
						labellist.add(label);
					}
				}
			}
		}
		br.close();
		twonum[0] = wordlist.size();
		twonum[1] = labellist.size();
		listtodocument(labellist, "labellist.dat");     //写文件
		listtodocument(wordlist, "wordlist.dat"); 
		System.out.println("----- 创建List完成 -----");
		
		return twonum;
	}
	
	//list 写到 文件
	public void listtodocument(List<String> list, String filename) throws IOException{
		PrintWriter pw = new PrintWriter(filename);
		for (String string: list) {
			pw.print(string + " ");
		}
		pw.flush();
		pw.close();
	}
	
	public void learn(String train, String model, int[] twonum) throws IOException{
		
		System.out.println("----- 开始训练 -----");
		//System.out.println(twonum[0] +"---------" + twonum[1]);
		int wordnum = twonum[0];
		int labelnum = twonum[1];
		double[] pi = new double[labelnum];
		double[][] A = new double[labelnum][labelnum];
		double[][] B = new double[labelnum][wordnum];
		for (int i = 0; i < labelnum; i++) {
			pi[i] = 1;
			for (int j = 0; j < labelnum; j++) {
				A[i][j] = 1;
			}
			for (int k = 0; k < wordnum; k++) {
				B[i][k] = 1;
			}
		}
		File file = new File(train);
		if(!file.exists()) {
			throw new IOException(file + "不存在！！！");
		}
		if(!file.isFile()) {
			throw new IOException(file + "不是文件！！！");
		}
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(file)));
		PrintWriter pw = new PrintWriter(model);
		String str = null;
		int frontindex = -1;
		int rowpi = 0;
		while((str = br.readLine()) != null) {
			rowpi ++;
			System.out.println("--learn读取到文件的行号： " + rowpi);
			String[] strarray = str.split(" ");
			for (String substr: strarray) {
				String[] tempstr = substr.split("/");
				if (tempstr.length == 2) {
					String word = tempstr[0];
					String label = tempstr[1];
					int wordindex = wordlist.indexOf(word);
					int labelindex = labellist.indexOf(label);
					B[labelindex][wordindex] += 1;
					if (frontindex != -1) {
						A[frontindex][labelindex] += 1;
					}
					frontindex = labelindex;
				}
			}
			String firstlabel = strarray[0].split("/")[1];
		    int firstlabelindex = labellist.indexOf(firstlabel);
		   // System.out.println(firstlabel);
		    pi[firstlabelindex] += 1;
		    
		}
		System.out.println("----- 写参数到model -----");
		//计算概率   写入model文件
		int factor = 1000;
		pw.println(3);
		pw.println(4);
		pw.println(labelnum + 4);
		for (int i = 0; i < labelnum; i++) {
			pw.print(factor * pi[i] / rowpi + " ");
		}
		pw.println();
		double rowsumA = 0;
		//pw.println("A");
		for (int i = 0; i < labelnum; i++) {
			
			for (int j = 0; j < labelnum; j++) {
				rowsumA += A[i][j];
			}
            for (int j = 0; j < labelnum; j++) {
            	pw.print(factor * A[i][j] / rowsumA + " ");
			}
            rowsumA = 0;
            pw.println();
		}
		double rowsumB = 0;
		//pw.println("B");
		for (int i = 0; i < labelnum; i++) {
            for (int k = 0; k < wordnum; k++) {
				rowsumB += B[i][k];
			}
            for (int k = 0; k < wordnum; k++) {
            	pw.print(factor * B[i][k] / rowsumB + " ");
			}
            rowsumB = 0;
            pw.println();
		}
		pw.flush();
		br.close();
		pw.close();
		System.out.println("--- 文件写入完毕 训练完成 ---");
	}
	
	//训练  写 参数 到model文件中
	public void tomodel(String allfile, String train, String model) throws IOException{
		//int[] twonum = creatlist(train);
		
		int[] twonum = creatlist(allfile);
		learn(train, model, twonum);
		
	}
	
	//训练的入口
	public void pleaselearn(String filename) throws IOException{
		double start = System.currentTimeMillis();
		
		String train = filename;
		String model = "model.dat";
		String allfile = "dataa.dat";
		tomodel(allfile, train, model);
		
		
		double end = System.currentTimeMillis();
		System.out.println("训练用时(s)： " + (end - start) / 1000);
	}
		
}
