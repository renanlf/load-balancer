/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.br.ufrpe.uag.lb.bean;

import edu.br.ufrpe.uag.lb.model.Host;
import edu.br.ufrpe.uag.lb.model.LoadBalancer;
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
    private Host host = new Host("", 0);

    @PostConstruct
    public void init() {
        setHosts(LoadBalancer.getInstance().getHosts().toArray(new Host[0]));
    }

    public void add() {
        if (!LoadBalancer.getInstance().getHosts().contains(host)) {
            LoadBalancer.getInstance().getHosts().add(getHost());

            setHosts(LoadBalancer.getInstance().getHosts().toArray(new Host[0]));
        } else{
            setRender(true);
        }
    }
    
    public void updateSelectHost(Host host){
        this.setHost(host);
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
     * @return the host
     */
    public Host getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(Host host) {
        this.host = host;
    }

}
