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
public class ConfigureBean {
    private int port = LoadBalancer.getInstance().getPort();
    
    public void save(){
        LoadBalancer.getInstance().setPort(port);
    }

    /**
     * @return the Port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param Port the Port to set
     */
    public void setPort(int port) {
        this.port = port;
    }
}
