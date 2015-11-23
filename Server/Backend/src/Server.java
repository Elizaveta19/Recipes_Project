import java.net.*;
import java.io.*;
public class Server{
	static final int PORT = 8888;
	static String HostName = null;
	static long n = 0;
	static ServerSocket s;
	
	public static void main(String[] args) throws IOException {
	    try{
	        s = new ServerSocket(PORT);
	    }catch (IOException e) {
	        System.out.println("����: "+PORT+" �����");
	        return;
	    }
	    System.out.println("������ ������� �����");
	    System.out.println("������ ��������....");
	    try {
	        //�������� ����
	        while (true) {
	            Socket socket = s.accept();  //��������� ������
	            HostName = socket.getInetAddress().getHostName(); //������� � ���������� ��� �������
	            n++;
	            System.out.println("���������� � "+ n +". ����������� ������: "+HostName);
	            try {
	                new ServerMain(socket, HostName, n);
	            } catch (IOException e) {
	                System.out.println("���������� � "+ n +" � ��������: "+HostName+" ���������");
	                socket.close();
	            }
	        }
	    } finally {
	        s.close();
	        System.out.println("���������� � ������ ��������� ���������");
	
	    }
	}
}