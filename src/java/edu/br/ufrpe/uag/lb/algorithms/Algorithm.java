/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.br.ufrpe.uag.lb.algorithms;

import edu.br.ufrpe.uag.lb.model.Host;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author renan
 */
public interface Algorithm {
    public Host getHost(CopyOnWriteArrayList<Host> hosts);
}
