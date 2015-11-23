import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class ServerMain extends Thread {

		long n;
		String HostName;
		Socket socket;
		BufferedReader in;
		PrintWriter out;

		public ServerMain(Socket s, String HostName, long n) throws IOException {
		    this.n = n; //��� ���������� �������� �� ��������� � ��������, ������� �����������
		    this.HostName = HostName;  //��� ���������� �������� �� ��������� ��� ��������, ������� �����������
		    this.socket = s;
		    //� ������������� ������ ��������� ������
		    this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    //��� ���������� �� ����� ������ �������
		    this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		    start();
		}

		public void run() {
		    try {
		        while (true) {
		            String str = in.readLine();
		            if (str.equalsIgnoreCase("END")) {
		                    break;
		            }
		            int a=1+3; //������� ���� ����������
		            out.println("� ������� ���� ������, ��� �� ��� �� ��������: "+str);
		            out.println("� ����������� ��������: "+a);
		        }
		    } catch (IOException e) {
		        System.err.println("���������� � "+ n +". ������ ��� �������� �������: " +HostName);
		    } finally {
		        try {
		            socket.close();
		        } catch (IOException e) {
		            System.err.println("���������� � "+ n +". �� ���������� ��������� ����� � ��������: " +HostName);
		        }
		    }
		    System.out.println("��������� ���������� � "+ n +", ������: " +HostName+" ��������");

		}
}