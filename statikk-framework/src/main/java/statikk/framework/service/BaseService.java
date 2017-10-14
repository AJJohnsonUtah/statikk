/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.framework.service;

/**
 *
 * @author AJ
 */
public abstract class BaseService<E> {

    public abstract void create(E entity);

    public abstract void update(E entity);

}
