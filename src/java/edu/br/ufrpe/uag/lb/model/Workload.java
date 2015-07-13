/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.br.ufrpe.uag.lb.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

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
            
//            String get = getStringFromInputStream(in);
//            
//            String firstLine = get.split("\n")[0];
//            String url = firstLine.split(" ")[1];
//            URL destiny = new URL("http://" + host.getIp() + ":" + host.getPort() + url);
            
            host.setConnections(host.getConnections() + 1);
            
//            System.out.println("Abrindo Socket--------------");
            Socket conn = new Socket(host.getIp(), host.getPort());
            
//            System.out.println("Transferindo GET");
            byte[] buffer = new byte[2048];
            int length1 = in.read(buffer);
            if(length1 != -1){
                conn.getOutputStream().write(buffer, 0, length1);
            }
//            while((length1 = in.read(buffer)) != -1){
//                conn.getOutputStream().write(buffer, 0, length1);
//            }
            conn.getOutputStream().flush();
            
//            PrintWriter pw = new PrintWriter(conn.getOutputStream());
//            
//            pw.println(get);
//            pw.flush();
            
//            HttpURLConnection conn = (HttpURLConnection) destiny.openConnection();
            
//            System.out.println("Transferindo saída do socket para a request");
            byte[] byteData = new byte[2048];
            int length;
            while ((length = conn.getInputStream().read(byteData)) != -1) {
                socket.getOutputStream().write(byteData, 0, length);
            }
//            System.out.println("Finalizando...");
            conn.close();
            in.close();
            socket.close();
            
            host.setConnections(host.getConnections()-1);
            long time = System.currentTimeMillis() - timeBegin;
            host.getTime().add(time);
//            System.out.println("Fim-----------------");
        } catch (IOException ex) {
//            host.setEnabled(false);
//            Logger.getLogger(Workload.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }
}
