/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocios;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author mario
 */
public class Entidad {
    private static final EntityManagerFactory ent=
            Persistence.createEntityManagerFactory("ChemyColorPU");
    
    public Entidad (){
    }
    
    public static EntityManagerFactory getInstance(){
        
        return ent;
        
    }
    
}
