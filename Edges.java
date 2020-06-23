/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrentprogramming;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author User
 */
public class Edges {
    
    private Nodes nodes;
    private int t;
    private int[] [] edges;
    private int m;
    
    Edges(){
    }
    
    Edges(Nodes nodes, int t, int m) throws InterruptedException, ExecutionException, java.util.concurrent.ExecutionException{
        this.nodes = nodes;
        this.t = t;
        this.m = m;
        String string= "";
        
        List<Future<String>> resultList = new ArrayList<>();
        
        ExecutorService executor = Executors.newFixedThreadPool(t);
        
        // Get amount of threads (t), Loop to create new thread - F
        for (int i=1; i<=t; i++){
            Future<String> finalString = (Future<String>) executor.submit ((Runnable) new EdgeThread (i, nodes));
            try{
                System.out.println("Started..");
                finalString.get(m, TimeUnit.MILLISECONDS);
            }
            catch (TimeoutException e) {
                finalString.cancel(true);
                System.out.println("Terminated");
            }
            resultList.add(finalString);
        }
        
        //cconvert result from future to string -F
        for (int i = 0; i<resultList.size(); i++){
            Future<String> result = resultList.get(i);
            try{
                string = result.get();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (ExecutionException e){
                e.printStackTrace();
            }
        }
        
        executor.shutdown();
        
        String finalString = string.substring(0, string.length() - 2);
        try{
            executor.awaitTermination (m, TimeUnit.SECONDS);
        }
        catch (InterruptedException e1) {
            e1.printStackTrace();
            }
        
        String test = "";
        
        finalString = finalString.replace("{","");
        finalString = finalString.substring (0, finalString.length()-1);
        String edge[] = finalString.split("},");
        
        int final_edge[][] = new int[edge.length] [edge.length];
        
        for (int i =0; i<edge.length; i++) {
            edge[i] = edge[i].trim();
            String single_int[] = edge[i].split(",");
            for (int j = 0; j<single_int.length; j++) {
                final_edge[i][j] = Integer.parseInt(single_int[j]);
            }
        }
        
        int [][] edges = final_edge;
    }
    public int [][] getEdges(){
        return this.edges;
    }
}
