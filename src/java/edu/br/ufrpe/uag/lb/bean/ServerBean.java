/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.br.ufrpe.uag.lb.bean;

import edu.br.ufrpe.uag.lb.model.Host;
import edu.br.ufrpe.uag.lb.model.LoadBalancer;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author renan
 */
@ManagedBean
public class ServerBean {

    private String hostAddr;
    private int port, priority;
    private boolean enabled, render;
    private Host[] hosts;

    @PostConstruct
    public void init() {
        setHosts(LoadBalancer.getInstance().getHosts().toArray(new Host[0]));
    }

    public void add() {
        Host host = new Host(hostAddr, port, priority, enabled);
        if (!LoadBalancer.getInstance().getHosts().contains(host)) {
            LoadBalancer.getInstance().getHosts().add(host);

            setHosts(LoadBalancer.getInstance().getHosts().toArray(new Host[0]));
        } else{
            setRender(true);
        }
    }

    /**
     * @return the hostAddr
     */
    public String getHostAddr() {
        return hostAddr;
    }

    /**
     * @param hostAddr the hostAddr to set
     */
    public void setHostAddr(String hostAddr) {
        this.hostAddr = hostAddr;
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
     * @return the hosts
     */
    public Host[] getHosts() {
        return hosts;
    }

    /**
     * @param hosts the hosts to set
     */
    public void setHosts(Host[] hosts) {
        this.hosts = hosts;
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

    /**
     * @return the render
     */
    public boolean isRender() {
        return render;
    }

    /**
     * @param render the render to set
     */
    public void setRender(boolean render) {
        this.render = render;
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
}
