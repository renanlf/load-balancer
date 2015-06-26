/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.br.ufrpe.uag.lb.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
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
    public void test() throws InterruptedException {
        //use thread
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                //get load balancer instance
                LoadBalancer loadBalancer = LoadBalancer.getInstance();
                //set configuration
                loadBalancer.setPort(1024);
                loadBalancer.setActive(true);
                //create server host
                Host localhost = new Host("localhost", 80);
                //vaio host - other computer
                Host vm1 = new Host("10.0.0.107", 80);
                //append the server host to load balancer hosts availables
                loadBalancer.getHosts().add(localhost);
                loadBalancer.getHosts().add(vm1);
                //listen on the port 1024 for requests
                loadBalancer.listen();
                
            }
        });
        thread.start();
        for (int i = 0; i < 1000; i++) {
            requester();
        }
    }
    /**
     * this method
     */
    public void requester() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 5000; i++) {
                    try {

			// representa um Uniform Resource Locator, 
                        // um ponteiro para um "recurso" na World Wide Web.
                        URL url = new URL("http://localhost:1024/xampp/index.php");
                        url.openConnection();
//                        System.out.println("** Tipo de objeto retornado **");
//                        System.out.println(url.getContent().getClass().getCanonicalName());

//                        System.out.println("\n** alguns métodos da classe URL **");
//                        System.out.println("Número da porta padrão do protocolo:" + url.getDefaultPort());
//                        System.out.println("Nome do arquivo:" + url.getFile());
//                        System.out.println("Nome do host:" + url.getHost());
//                        System.out.println("Parte do caminha:" + url.getPath());
//                        System.out.println("Número da porta:" + url.getPort());
//                        System.out.println("Nome do protocolo:" + url.getProtocol());
//                        System.out.println("Parte da consulta:" + url.getQuery());
//                        System.out.println("Ancora (referencia da URL):" + url.getRef());
//                        System.out.println("userInfo da URL:" + url.getUserInfo());
//                        System.out.println("Número de indexação de um hash table:" + url.hashCode());
//                        System.out.println("Representação da url:" + url.toExternalForm());
//                        System.out.println("Representação da url:" + url.toString());

                        // cria um stream de entrada do conteúdo
                        InputStreamReader inputReader = new InputStreamReader(url.openStream());
                        BufferedReader bufferedReader = new BufferedReader(inputReader);

                        System.out.println("\n** Conteúdo do recurso web **");
                        String linha = "";
                        while ((linha = bufferedReader.readLine()) != null) {
                            System.out.println(linha);
                        }

                    } catch (Exception e) {
//                        System.out.println(e.getMessage());
                    }
                }
            }
        });
        thread.run();
    }

}
