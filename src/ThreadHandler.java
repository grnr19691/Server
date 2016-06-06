/**
 * Created by grnr1 on 6/6/2016.
 */
import java.io.*;
import java.net.*;

class ThreadHandler extends Thread
{
    Socket newsock;
    int n;
    ThreadHandler (Socket s,int v)
    {
        newsock = s;
        n = v;

    }
    public void run()
    {
        try
        {
            InputStream inp = newsock.getInputStream();
            //BufferedOutputStream outB = new BufferedOutputStream(newsock.getOutputStream());
            PrintStream outp= new PrintStream(newsock.getOutputStream(),true);
            outp.println("It is connected to "+InetAddress.getLocalHost()+":"+newsock.getLocalPort());
            boolean more_data=true;
            while(more_data)
            {
                BufferedReader br=new BufferedReader(new InputStreamReader(inp));
                String line=br.readLine();
                if(line==null)
                    more_data=false;
                else
                {
                    String temp1Array[]=line.split(" ");
                    String request=temp1Array[0];
                    String filePath=temp1Array[1];
                    String httpType=temp1Array[2];
                    outp.println("Request : " + request);
                    outp.println("File path : " + filePath);
                    outp.println("http request Type : " + httpType);
                    File f = new File(filePath.replace("/",""));
                    if(!f.exists())
                    {
                        outp.println("404 error: File does not exist");
                    }
                    else
                    {
                        outp.println("200 SUCCESS: The file exists");
                        FileInputStream requestedfile = new FileInputStream(filePath.replace("/",""));
                        byte[] buffer = new byte[1];
                        outp.println("Content-Length: "+new File(filePath.replace("/","")).length()); // for the client to receive file
                        while((requestedfile.read(buffer)!=-1))
                        {
                            outp.print("S");
                            outp.write(buffer);
                            outp.flush();
                            outp.close();
                        }
                        requestedfile.close();
                    }
                    if(line.trim().equals("QUIT"))
                        more_data=false;
                }
            }
            newsock.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
