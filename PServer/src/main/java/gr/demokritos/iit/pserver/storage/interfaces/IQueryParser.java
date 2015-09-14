/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.pserver.storage.interfaces;

/**
 * Represents a parser that reads a query and returns an object
 * which can be used in a Storage class to filter records.
 * The template type T should indicate the return type
 * of the parsing process.
 *
 * @author Giotis Panagiotis <giotis.p@gmail.com>
 */
public interface IQueryParser<T> {
    public T getParsedQuery(String sExpression);
}
