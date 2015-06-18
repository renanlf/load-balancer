/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.br.ufrpe.uag.lb.model;

import edu.br.ufrpe.uag.lb.algorithms.Algorithm;
import static edu.br.ufrpe.uag.lb.model.LoadBalancer.getStringFromInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author renan
 */
public class Workload extends Thread {

    private final Socket socket;
    private final Algorithm algorithm;
    private final Host host;

    public Workload(Socket socket, Host host) {
        super("Workload");
        this.socket = socket;
        this.algorithm = null;
        this.host = host;
    }

    @Override
    public void run() {
        InputStream in;
        try {
            long timeBegin = System.currentTimeMillis();
            in = socket.getInputStream();
            String get = getStringFromInputStream(in);
            String firstLine = get.split("\n")[0];
            String url = firstLine.split(" ")[1];
            URL destiny = new URL("http://" + host.getIp() + ":" + host.getPort() + url);
            host.setConnections(host.getConnections() + 1);
            HttpURLConnection conn = (HttpURLConnection) destiny.openConnection();
            byte[] byteData = new byte[2048];
            int length;
            while ((length = conn.getInputStream().read(byteData)) != -1) {
                socket.getOutputStream().write(byteData, 0, length);
            }
            in.close();
            socket.close();
            host.setConnections(host.getConnections()-1);
            long time = System.currentTimeMillis() - timeBegin;
            host.getTime().add(time);
        } catch (IOException ex) {
            host.setEnabled(false);
            Logger.getLogger(Workload.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
