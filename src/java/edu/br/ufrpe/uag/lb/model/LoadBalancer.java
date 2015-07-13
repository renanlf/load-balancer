/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.br.ufrpe.uag.lb.model;

import edu.br.ufrpe.uag.lb.algorithms.Algorithm;
import edu.br.ufrpe.uag.lb.algorithms.DinamicWeight;
import edu.br.ufrpe.uag.lb.algorithms.RoundRobin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author renan
 */
public class LoadBalancer {
    
    private static final int LIMIAR = 50;

    private static LoadBalancer instance = null;
    
    private int port;
    private final CopyOnWriteArrayList<Host> hosts;
    private final Algorithm algorithm;
    private boolean active;
//    private final List<Request> requests;

    private LoadBalancer(int port, Algorithm algorithm) {
        this.hosts = new CopyOnWriteArrayList<>();
        this.algorithm = algorithm;
        this.port = port;
        this.active = false;
//        this.hosts.add(new Host("localhost", 8084));
    }

    public static LoadBalancer getInstance() {
        if (instance == null) {
//            instance = new LoadBalancer(80, new DinamicWeight());
            instance = new LoadBalancer(80, new RoundRobin());
            instance.setPort(1024);
        }
        return instance;
    }

    public void listen() {

        ServerSocket ss = null;
        try {
            ss = new ServerSocket(getPort());
            int connections = 0;
            while (active) {
                Socket socket = ss.accept();
                
                if(algorithm instanceof DinamicWeight){
                    if(connections < LIMIAR){
                        connections++;
                    } else {
                        DinamicWeight dW = (DinamicWeight) algorithm;
                        System.out.println("UpdateTickets");
                        dW.updateTicket(enabledHosts());
                        System.out.println("-------------------");
                        connections = 0;
                    }
                }
                
                Host host = algorithm.getHost(enabledHosts());
                System.out.println("Host selected= "+host.getIp());
                Thread t = new Thread(new Workload(socket, host));
                t.start();
            }
            ss.close();
        } catch (IOException ex) {
            Logger.getLogger(LoadBalancer.class.getName()).log(Level.SEVERE, null, ex);
            this.active = false;
            try {
                if(ss != null) ss.close();
            } catch (IOException ex1) {
                Logger.getLogger(LoadBalancer.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

    }

    public static String getStringFromInputStream(InputStream in) throws IOException {
        if (in != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[4096];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(in));
                int i = reader.read(buffer);
                writer.write(buffer, 0, i);
            } catch (IOException e) {
                return "";
            }
            return writer.toString();
        } else {
            return "";
        }
        
//        if(in != null){
//            Reader reader = new BufferedReader(new InputStreamReader(in));
//            Writer writer = new StringWriter();
//            
//            char[] buffer = new char[2048];
//            int length;
//            while((length = reader.read(buffer)) != -1){
//                writer.write(buffer, 0, length);
//            }
//            return writer.toString();
//        }
//        
//        throw new IOException("InputStream not found!");

    }

    /**
     * @return the hosts
     */
    public CopyOnWriteArrayList<Host> getHosts() {
        return hosts;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    public CopyOnWriteArrayList<Host> enabledHosts() {
        CopyOnWriteArrayList<Host> enabledHost = new CopyOnWriteArrayList<>();
        for (Host host : this.hosts) {
            if (host.isEnabled()) {
                enabledHost.add(host);
            }
        }
        return enabledHost;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
