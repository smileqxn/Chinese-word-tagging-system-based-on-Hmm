public class splitfile {

	public static void main(String[] args) throws Exception{
		DoFile dofile = new DoFile();
		// 源文件  目标文件  拆分的个数
		//dofile.splitfile("test.dat", "subtest/test", 10);
		dofile.splitfile(args[0], args[1], Integer.parseInt(args[2]));
		
	}

}