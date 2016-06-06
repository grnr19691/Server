/**
 * Created by grnr1 on 6/6/2016.
 */
import java.net.*;

public class Server
{

    public static void main(String[] args)
    {
        int nreq=1;
        InetAddress ip;
        try
        {
            ServerSocket sock = new ServerSocket(8121);
            ip = InetAddress.getLocalHost();
            System.out.println("Current Ip Address:" + ip );
            System.out.println("Current port Address:"+ sock.getLocalPort());
            while(true)
            {
                Socket newsock= sock.accept();
                System.out.println("Creating new thread ...");
                Thread t = new ThreadHandler(newsock,nreq);
                t.start();
                nreq++;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


}
