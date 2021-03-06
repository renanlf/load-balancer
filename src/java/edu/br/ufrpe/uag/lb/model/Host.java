/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.br.ufrpe.uag.lb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author renan
 */
public class Host {
    private String ip;
    private int port;
    private int connections;
    private boolean enabled;
    private int priority;
    private int remaningConnections = 0;
    private CopyOnWriteArrayList<Long> time;
    private double ticket;
    
    public Host(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.connections = 0;
        this.enabled = true;
        this.time = new CopyOnWriteArrayList<>();
        this.ticket = 1.0;
    }
    
    public Host(String ip, int port, int priority, boolean enabled){
        this.ip = ip;
        this.port = port;
        this.connections = 0;
        this.enabled = enabled;
        this.priority = priority;
        this.time = new CopyOnWriteArrayList<>();
        this.ticket = 1.0;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
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

    /**
     * @return the connections
     */
    public synchronized int getConnections() {
        return connections;
    }

    /**
     * @param connections the connections to set
     */
    public synchronized void setConnections(int connections) {
        this.connections = connections;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.ip);
        hash = 97 * hash + this.port;
        return hash;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Host){
            Host host = (Host) o;
            return this.getIp().equals(host.getIp()) && this.getPort() == host.getPort();
        }
        throw new IllegalArgumentException();
    }

    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * @return the remaningConnections
     */
    public int getRemaningConnections() {
        return remaningConnections;
    }

    /**
     * @param remaningConnections the remaningConnections to set
     */
    public void setRemaningConnections(int remaningConnections) {
        this.remaningConnections = remaningConnections;
    }

    /**
     * @return the time
     */
    public CopyOnWriteArrayList<Long> getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(CopyOnWriteArrayList<Long> time) {
        this.time = time;
    }

    /**
     * @return the ticket
     */
    public double getTicket() {
        return ticket;
    }

    /**
     * @param ticket the ticket to set
     */
    public void setTicket(double ticket) {
        this.ticket = ticket;
    }
}
