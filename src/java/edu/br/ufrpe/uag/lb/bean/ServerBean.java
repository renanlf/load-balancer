/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.br.ufrpe.uag.lb.bean;

import edu.br.ufrpe.uag.lb.model.Host;
import edu.br.ufrpe.uag.lb.model.LoadBalancer;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author renan
 */
@ManagedBean
public class ServerBean {

    private boolean render;
    private CopyOnWriteArrayList<Host> hosts;
    private Host host = new Host("localhost", 80);

    @PostConstruct
    public void init() {
        setHosts(LoadBalancer.getInstance().getHosts());
    }

    public void add() {
        if(hosts.contains(host)){
            setRender(true);
        } else {
            setRender(false);
            hosts.add(host);
        }
    }
    /**
     * @return the hosts
     */
    public CopyOnWriteArrayList<Host> getHosts() {
        return hosts;
    }

    /**
     * @param hosts the hosts to set
     */
    public void setHosts(CopyOnWriteArrayList<Host> hosts) {
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
