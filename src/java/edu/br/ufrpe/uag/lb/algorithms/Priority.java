/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.br.ufrpe.uag.lb.algorithms;

import edu.br.ufrpe.uag.lb.model.Host;
import java.util.concurrent.CopyOnWriteArrayList;


public class Priority implements Algorithm {

    @Override
    public Host getHost(CopyOnWriteArrayList<Host> hosts) {
        int maxPriority = 0;
        Host selectedHost = null;
        for(Host host : hosts){
            if(host.getPriority() > maxPriority){
                maxPriority = host.getPriority();
                selectedHost = host;
            }
        }
        return selectedHost;
    }
    
}
