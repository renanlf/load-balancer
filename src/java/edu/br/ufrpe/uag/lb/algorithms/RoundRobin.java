/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.br.ufrpe.uag.lb.algorithms;

import edu.br.ufrpe.uag.lb.model.Host;
import java.util.List;

/**
 *
 * @author renan
 */
public class RoundRobin implements Algorithm{
    int position;
    public RoundRobin(){
        this.position = 0;
    }
    @Override
    public Host getHost(List<Host> hosts) {
        int turn = position % hosts.size();
        position++;
        return hosts.get(turn);
        
    }
    
}
