package com.rm.empresarial.dao;

import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.RepertorioUsuario;
import java.io.Serializable;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;



@LocalBean
@Stateless
public class RpuDao extends Generico<RepertorioUsuario> implements Serializable { 
    
        public  List<RepertorioUsuario> listarRepertorioUsuarioEstado(){
        
            String sql = "SELECT * from repertorioUsuario "
                
                ;
        Query q = getEntityManager().createNativeQuery(sql , RepertorioUsuario.class);
        return q.getResultList();
    }
        
        
    
    
}
