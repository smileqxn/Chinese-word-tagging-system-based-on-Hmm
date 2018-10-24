
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class eDoIt {
	public void evaluation(String filename) throws IOException{
		
		File file = new File(filename);
		if (!file.exists()) {
			throw new IOException(file + "不存在！！！");
		}
		if (!file.isFile()) {
			throw new IOException(file + "不是文件");
		}
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(file)));
		int sum = 0;
		int correct = 0;
		String str;
		String[] strarray;
		int flag = -1;
		int k = 1;
		while(true) {
			str = null;
			strarray = new String[2];
			for (int i = 0; i < 2; i++) {
				str = br.readLine();
				if (str != null) {
					strarray[i] = str;
				}else {
					flag = 1;
					break;
				}	
			}
			if (flag > 0)
				break;
			String[] temp1 = strarray[1].split(" ");
			String[] temp2 = strarray[0].split(" ");
			for (int i = 0; i < temp1.length; i++) {
				if (temp1[i].split("/").length == 2 && temp2[i].split("/").length == 2){
				    sum++;
				    String[] str1 = temp1[i].split("/");
				    String[] str2 = temp2[i].split("/");
				    if (str1[1].equals(str2[1])) {
					    correct++;
				}
				}
				
			}
		}
		double accuracy = 100.0 * correct / sum;
		
		
		
		System.out.println("总单词的个数：" + sum +  "\n正确标注的单词个数：" + correct);
		System.out.println("准确率为：" + accuracy + "%");
		
		br.close();
		
		
	}
}