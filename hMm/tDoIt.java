
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class tDoIt {
	List<String> wordlist;
	List<String> labellist;
	
	public tDoIt() {
		wordlist = new ArrayList<String>();
		labellist = new ArrayList<String>();
	}
	
	//进行测试
	public void test(String model, String test, String result) throws IOException{
		System.out.println("----- 开始测试 -----");
		////////////////////
		String[] wordd = readdocument("wordlist.dat");
		for (String stri: wordd) {
			wordlist.add(stri);
		}
		//System.out.println(Arrays.toString(word));
		String[] label = readdocument("labellist.dat");
		for (String strii: label) {
			labellist.add(strii);
		}
		//System.out.println(labellist);
		//////////////////////
		File filemodel = new File(model);
		File filetest = new File(test);
		if(!filemodel.exists()) {
			throw new IOException(filemodel + "不存在！！！");
		}
		if(!filemodel.isFile()) {
			throw new IOException(filemodel + "不是文件！！！");
		}
		if(!filetest.exists()) {
			throw new IOException(filetest + "不存在！！！");
		}
		if(!filetest.isFile()) {
			throw new IOException(filetest + "不是文件！！！");
		}
		BufferedReader brmodel = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(filemodel)));
		BufferedReader brtest = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(filetest)));
		String[] rowpi = null;
		String[] rowA = null;
		String[] rowB = null;
		String strmodel = null;
		int rownumpi = tempreadfile(filemodel)[0];
		int rownumA = tempreadfile(filemodel)[1];
		int rownumB  = tempreadfile(filemodel)[2];
		//System.out.println(rownumA);
		//System.out.println(rownumB);
		double[] pi = new double[rownumB - rownumA];
	    double[][] A = new double[rownumB - rownumA][];
	    double[][] B = new double[rownumB - rownumA][];
	    int j = 0, k = 0;
		for (int i = 0; (strmodel = brmodel.readLine()) != null; i++) {
			if(i >= rownumpi && i < rownumA) {
				rowpi = strmodel.split(" ");
				pi = strtodouble(rowpi);
			}else if (i >= rownumA && i < rownumB) {
				rowA = strmodel.split(" ");
				A[j++] = strtodouble(rowA);
			}else if(i >= rownumB){
				rowB = strmodel.split(" ");
				B[k++] = strtodouble(rowB);
			}
				
		}
		
		StringBuilder strbd;
		PrintWriter pw = new PrintWriter(result);
		String teststr = null;
		int row = 1;
		while((teststr = brtest.readLine()) != null) {
			//System.out.println(teststr);
			pw.println(teststr);
			//实验
			System.out.println("--test读取到文件的行号： " + row++);
			
			//System.out.println(teststr);
			String[] strarray = teststr.split(" ");
			strbd = new StringBuilder();
			for (String substr: strarray) {
				String[] tempstr = substr.split("/");
				if (tempstr.length == 2) {
					String word = tempstr[0];
					strbd.append(word + " ");
				}
			}
			
			//实验
			//System.out.println(strbd.toString());/////////////////////
			int[] labelindex = viterbi(strbd.toString(), pi, A, B);
			//System.out.println(Arrays.toString(labelindex));
			//System.out.println(labellist);
			String[] strwords = strbd.toString().split(" ");
			
			for (int i = 0; i < labelindex.length; i++) {
				pw.print(strwords[i] + "/" + labellist.get(labelindex[i]) + " ");
			}
			
			
//			for (int i: labelindex) {
//				pw.print(labellist.get(i) + " ");
//			}
			pw.println();
			
		}
		pw.flush();
		brmodel.close();
		brtest.close();
		pw.close();
	}
	
	// viterbi
	public int[] viterbi(String string, double[] pi, double[][] A, double[][] B) throws IOException{
		
		//
		//System.out.println(wordlist.indexOf("前"));
		//System.out.println(B[0][367]);
		
		String[] words = string.split(" ");
		double[][] delta = new double[words.length][pi.length];
		int[][] way = new int[words.length][pi.length];
		int[] labelindex = new int[words.length];
        //System.out.println(words[0]);
		for (int i = 0; i < pi.length; i++) {
			delta[0][i] = pi[i] * B[i][wordlist.indexOf(words[0])];  //////////////////////////////////////////////
		}
		for (int t = 1; t < words.length; t++) {
			//System.out.println(words[t]);
			for (int i = 0; i < pi.length; i++) {
				for (int j = 0; j < pi.length; j++) {
					////////
					//System.out.println("t:" +t + "i:" + i + "j:" + j + "wordlist.indexOf(words[t]):"
						//	+ wordlist.indexOf(words[t]));
					if(delta[t][i] < delta[t-1][j] * A[j][i] * B[i][wordlist.indexOf(words[t])]) {
						delta[t][i] = delta[t-1][j] * A[j][i] * B[i][wordlist.indexOf(words[t])];
						way[t][i] = j;
					}
				}
			}
		}
		double max = delta[words.length - 1][0];
		labelindex[words.length - 1] = 0;
		for (int i = 0; i < pi.length; i++) {
			if (delta[words.length - 1][i] > max) {
				max = delta[words.length - 1][i];
				labelindex[words.length - 1] = i;
			}
		}
		for (int t = words.length - 2; t >= 0; t--) {
			labelindex[t] = way[t + 1][labelindex[t + 1]];
		}
		//System.out.println(Arrays.toString(labelindex));
		return labelindex;
		
		
		
	}
	
	// 读文件到数组
	public String[] readdocument(String filename) throws IOException{
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(filename)));
		String[] strarray = br.readLine().split(" ");
		br.close();
		return strarray;
				
	}
	//读取文件前的三个参数
	public int[] tempreadfile(File file) throws IOException {
		int[] threenum = new int[3];
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(file)));
		int i = 0;
		String str;
		while((str = br.readLine()) != null) {
			if(i > 2) {
				break;
			}
			threenum[i++] = Integer.parseInt(str);
		}
		br.close();
		return threenum;
	}
	
	//转String 为 double类型
	public double[] strtodouble(String[] strarray) {
		double[] dbs = new double[strarray.length];
		for (int i = 0; i < strarray.length; i++) {
			dbs[i] = Double.valueOf(strarray[i]);
		}
		return dbs;
	}
	
	
	//测试的入口
	public void pleasetest(String filename, String resultname) throws IOException{
		double start = System.currentTimeMillis();
		
		
		String test = filename;
		String model = "model.dat";
		String result = resultname;
		test(model, test, result);
		
		
		double end = System.currentTimeMillis();
		System.out.println("测试用时(min)： " + (end - start) / 1000 / 60);
	}
	
		
}
