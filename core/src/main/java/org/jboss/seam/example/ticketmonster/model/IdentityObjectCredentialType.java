package org.jboss.seam.example.ticketmonster.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.jboss.errai.bus.server.annotations.ExposeEntity;

/**
 * Lookup table containing credential types
 * 
 * @author Shane Bryzak
 */
@ExposeEntity
@Entity
public class IdentityObjectCredentialType implements Serializable
{
   private static final long serialVersionUID = 282711089697868242L;
   
   private Long id;
   private String name;
   
   @Id @GeneratedValue
   public Long getId()
   {
      return id;
   }
   
   public void setId(Long id)
   {
      this.id = id;
   }
   
   //@IdentityProperty(PropertyType.NAME)
   public String getName()
   {
      return name;
   }
   
   public void setName(String name)
   {
      this.name = name;
   }
}
