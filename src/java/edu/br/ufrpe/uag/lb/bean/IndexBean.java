/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.br.ufrpe.uag.lb.bean;

import edu.br.ufrpe.uag.lb.model.LoadBalancer;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author renan
 */
@ManagedBean
//@ApplicationScoped
public class IndexBean {
    private LoadBalancer loadBalancer = LoadBalancer.getInstance();
    private boolean active = LoadBalancer.getInstance().isActive();
    
    public int getPort(){
        return LoadBalancer.getInstance().getPort();
    }
    
    public boolean isActive(){
        return active;
    }
    
    public void setActive(boolean active){
        getLoadBalancer().setActive(active);
        this.active = active;
    }
    
    public void activate(){
        getLoadBalancer().setActive(true);
        getLoadBalancer().listen();
    }
    
    public void deactivate(){
        getLoadBalancer().setActive(false);
    }

    /**
     * @return the loadBalancer
     */
    public LoadBalancer getLoadBalancer() {
        return loadBalancer;
    }

    /**
     * @param loadBalancer the loadBalancer to set
     */
    public void setLoadBalancer(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }
    
}
