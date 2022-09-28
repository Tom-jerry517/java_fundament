import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Testtt {

    @Test
    public void client() throws IOException {
        Socket socket=new Socket("127.0.0.1",8899);
        OutputStream os=socket.getOutputStream();
        os.write("你好，hello".getBytes());
        os.close();
        socket.close();
    }

    @Test
    public void server() throws IOException {
        ServerSocket ss=new ServerSocket(8899);
        Socket socket = ss.accept();
        InputStream is = socket.getInputStream();

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        byte[] buffer=new byte[5];
        int len;
        while((len=is.read(buffer))!=-1){
            baos.write(buffer,0,len);
        }
        System.out.println(baos.toString());
        baos.close();
        is.close();
        socket.close();
        ss.close();
    }

}

