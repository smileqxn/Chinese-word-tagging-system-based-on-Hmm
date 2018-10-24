
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class dDoIt {
	
	List<String> wordlist;
	List<String> labellist;
	
	public dDoIt() {
		wordlist = new ArrayList<String>();
		labellist = new ArrayList<String>();
	}
	
	//����decode
	public void decode(String model) throws Exception{
		Scanner console;
		System.out.println("[MADE BY XINGLICHAO]");
		System.out.println("���Ա�עϵͳ������...");
		System.out.println("------------------------------------");

		String[] wordd = readdocument("wordlist.dat");
		for (String stri: wordd) {
			wordlist.add(stri);
		}

		String[] label = readdocument("labellist.dat");
		for (String strii: label) {
			labellist.add(strii);
		}

		File filemodel = new File(model);

		if(!filemodel.exists()) {
			throw new IOException(filemodel + "�����ڣ�����");
		}
		if(!filemodel.isFile()) {
			throw new IOException(filemodel + "�����ļ�������");
		}

		BufferedReader brmodel = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(filemodel)));

		String[] rowpi = null;
		String[] rowA = null;
		String[] rowB = null;
		String strmodel = null;
		int rownumpi = tempreadfile(filemodel)[0];
		int rownumA = tempreadfile(filemodel)[1];
		int rownumB  = tempreadfile(filemodel)[2];
		
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
		
		while(true) {
			System.out.println("����������ӵĸ����ִʲ��Կո���룺");
			System.out.println("[����ʹ�� �밴  0 ]");
			System.out.println("------------------------------------");
			console = new Scanner(System.in);
			try {
				String str = console.nextLine();
				if (str.equals("0")) {
					brmodel.close();
					console.close();
					System.out.println();
					System.out.println("Ӧ�ý���...");
					System.exit(0);
				}
				long start = System.currentTimeMillis();
				int[] labelindex = viterbi(str, pi, A, B);
				String[] strwords = str.split(" ");
				System.out.println();
				System.out.println("------------------------------------");
				System.out.println("��ע�����");
				for (int i = 0; i < labelindex.length; i++) {
					System.out.print(strwords[i] + "/" + labellist.get(labelindex[i]) + " ");
					
				}
				System.out.println();
				long end = System.currentTimeMillis();
				System.out.println("\n[���α�ע��ʱ " + (end-start) + " ms]");
				System.out.println("------------------------------------");
				
			}catch(Exception e) {
				System.out.println("\n��ķִʳ������ҵ�������Χ!!");
				System.out.println("------------------------------------");
			}
		}
	}
	
	// viterbi
	public int[] viterbi(String string, double[] pi, double[][] A, double[][] B) throws IOException{
		
		//
		//System.out.println(wordlist.indexOf("ǰ"));
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
	
	// ���ļ�������
	public String[] readdocument(String filename) throws IOException{
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(filename)));
		String[] strarray = br.readLine().split(" ");
		br.close();
		return strarray;
				
	}
	//��ȡ�ļ�ǰ����������
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
	
	//תString Ϊ double����
	public double[] strtodouble(String[] strarray) {
		double[] dbs = new double[strarray.length];
		for (int i = 0; i < strarray.length; i++) {
			dbs[i] = Double.valueOf(strarray[i]);
		}
		return dbs;
	}
	
}