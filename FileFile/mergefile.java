public class mergefile {

	public static void main(String[] args) throws Exception{
		DoFile dofile = new DoFile();
		// Դ�ļ�  Ŀ���ļ�  ��ֵĸ���
		//dofile.splitfile("test.dat", "subtest/test", 10);
		//dofile.splitfile(args[0], args[1], Integer.parseInt(args[2]));
		
		//�ϲ��ļ�  װС�ļ����ļ���   ���ļ�
		dofile.mergefile(args[0], args[1]);
	}

}