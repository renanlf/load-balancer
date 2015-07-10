/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.br.ufrpe.uag.lb.model;

import static edu.br.ufrpe.uag.lb.model.LoadBalancer.getStringFromInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;

/**
 *
 * @author renan
 */
public class Workload implements Runnable {

    private final Socket socket;
    private final Host host;

    public Workload(Socket socket, Host host) {
        this.socket = socket;
        this.host = host;
    }

    @Override
    public void run() {
        InputStream in;
        try {
            long timeBegin = System.currentTimeMillis();
            
            in = socket.getInputStream();
            
            String get = getStringFromInputStream(in);
//            
//            String firstLine = get.split("\n")[0];
//            String url = firstLine.split(" ")[1];
//            URL destiny = new URL("http://" + host.getIp() + ":" + host.getPort() + url);
            
            host.setConnections(host.getConnections() + 1);
            
            Socket conn = new Socket(host.getIp(), host.getPort());
            
//            byte[] buffer = new byte[2048];
//            int length1;
//            while((length1 = in.read(buffer)) != -1){
//                conn.getOutputStream().write(buffer, 0, length1);
//            }
//            in.close();
            
            PrintWriter pw = new PrintWriter(conn.getOutputStream());
            
            pw.println(get);
            pw.flush();
            
//            HttpURLConnection conn = (HttpURLConnection) destiny.openConnection();
            
            byte[] byteData = new byte[2048];
            int length;
            while ((length = conn.getInputStream().read(byteData)) != -1) {
                socket.getOutputStream().write(byteData, 0, length);
            }
            conn.close();
            socket.close();
            
            host.setConnections(host.getConnections()-1);
            long time = System.currentTimeMillis() - timeBegin;
            host.getTime().add(time);
        } catch (IOException ex) {
//            host.setEnabled(false);
//            Logger.getLogger(Workload.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
