/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.service;

/**
 *
 * @author AJ
 */
public abstract class BaseService<E> {

    public abstract E create(E entity);

    public abstract E update(E entity);

}
