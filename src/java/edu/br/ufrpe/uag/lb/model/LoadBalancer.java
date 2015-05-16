/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.br.ufrpe.uag.lb.model;

import edu.br.ufrpe.uag.lb.algorithms.Algorithm;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author renan
 */
public class LoadBalancer {

    private static LoadBalancer instance = null;

    private String ip;
    private int port;
    private final List<Host> hosts;
    private final Algorithm algorithm;
    private boolean active;
//    private final List<Request> requests;

    private LoadBalancer(int port, Algorithm algorithm) {
        this.hosts = new ArrayList<>();

        this.algorithm = algorithm;
        this.port = port;
        this.active = false;
//        this.hosts.add(new Host("localhost", 8084));
    }

    public static LoadBalancer getInstance() {
        if (instance == null) {
            instance = new LoadBalancer(80, new RoundRobin());
        }
        return instance;
    }

    public void listen() {

        ServerSocket ss = null;
        try {
            ss = new ServerSocket(getPort());
            while (active) {
                Socket socket = ss.accept();
                Host host = algorithm.getHost(enabledHosts());
                new Workload(socket, host).start();
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

    public static String getStringFromInputStream(InputStream in) {
        if (in != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[2048];
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

    }

    /**
     * @return the hosts
     */
    public List<Host> getHosts() {
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

    public List<Host> enabledHosts() {
        List<Host> enabledHost = new ArrayList<>();
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
