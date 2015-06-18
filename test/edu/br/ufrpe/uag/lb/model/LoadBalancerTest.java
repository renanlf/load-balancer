/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.br.ufrpe.uag.lb.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author renan
 */
public class LoadBalancerTest {

    /**
     * Test of getInstance method, of class LoadBalancer.
     */
    @Test
    @SuppressWarnings("empty-statement")
    public void test() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                LoadBalancer loadBalancer = LoadBalancer.getInstance();
                loadBalancer.setPort(1024);
                loadBalancer.setActive(true);
                Host localhost = new Host("localhost", 80);
                loadBalancer.getHosts().add(localhost);
                loadBalancer.listen();
            }
        });
        thread.start();
        requester();
        while(thread.isAlive());
    }

    public void requester() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                for(int i = 0; i < 50; i++){
                    try {
                        Socket socket = new Socket("localhost", 1024);
                        socket.close();
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(LoadBalancerTest.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(LoadBalancerTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        thread.run();
    }

}
