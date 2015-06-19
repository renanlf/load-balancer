/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.br.ufrpe.uag.lb.algorithms;

import edu.br.ufrpe.uag.lb.model.Host;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author renan
 */
public class DinamicWeight implements Algorithm {

    private static final double MAX = 1;

    @Override
    public Host getHost(List<Host> hosts) {
        double[] values = new double[hosts.size()];
        double sum = 0;
        for (int i = 0; i < hosts.size(); i++) {
            values[i] = hosts.get(i).getTicket();
            sum += values[i];
        }
        int index = getIndex(values, sum);
        return hosts.get(index);
    }

    /**
     * this method is used to calculate the index for the selected host
     *
     * @param values vector with tickets for all enabled hosts.
     * @param sum sum for all tickets values.
     * @return the index for the selected host.
     */
    public int getIndex(double[] values, double sum) {
        Random random = new Random();
        double randomNumber = random.nextDouble();
        double cumulate = 0;
        for (int i = 0; i < values.length; i++) {
            double value = values[i];
            double proportion = value / sum;
            if (proportion > cumulate && proportion <= proportion + cumulate) {
                return i;
            }
            cumulate += proportion;
        }
//        return 0;
        return -1;
    }

    public void updateTicket(List<Host> hosts) {
        for (Host host : hosts) {
            if (host.getConnections() > 0 && host.getTime().size() > 0) {
                long mean = 0;
                for (Long time : host.getTime()) {
                    mean += time;
                }
                mean = mean / host.getTime().size();
                host.setTicket(1.0 / (host.getConnections() * mean));
                host.setTime(new ArrayList<>());
            } else {
                host.setTicket(MAX);
                host.setTime(new ArrayList<>());
            }
            System.out.println(host.getTicket());
        }
    }

}
