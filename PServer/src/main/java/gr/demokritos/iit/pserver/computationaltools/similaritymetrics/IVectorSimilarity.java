/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.pserver.computationaltools.similaritymetrics;

/**
 * Interface for vector similarity implementations
 *
 * @author Giotis Panagiotis <giotis.p@gmail.com>
 */
public interface IVectorSimilarity {

    double getSimilarity(double[] docVector1, double[] docVector2);

}
