import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class DownloadImage {
    public static void main(String[] args) {
        String host = "kiokahn.synology.me"; //서버 컴퓨터의 주소
        int port = 30000; // 프로세스 구분
        String fileURL = "/uploads/-/system/appearance/logo/1/Gazzi_Labs_CI_type_B_-_big_logo.png"; //파일의 위치
        String filePath = "downloaded.png"; //내 컴퓨터에서 파일이 저장되는 파일명
        
        try (Socket socket = new Socket(host, port);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        InputStream in = new BufferedInputStream(socket.getInputStream());
        OutputStream fileOut = new BufferedOutputStream(new FileOutputStream(filePath))) {
            out.println("GET " + fileURL + " HTTP/1.1");
            out.println("Host: " + host);
            out.println("Connection: Close");
            out.println("\r\n");

            StringBuilder response = new StringBuilder();
            int nextByte;
            while ((nextByte = in.read()) != -1) {
                response.append((char) nextByte);
                // Check for end of headers (empty line)
                if (response.toString().endsWith("\r\n\r\n")) {
                    break;
                }
            }

            byte[] buffer = new byte[4096];
            int bytesRead;
            while( (bytesRead = in.read(buffer)) != -1 ) {
                fileOut.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}