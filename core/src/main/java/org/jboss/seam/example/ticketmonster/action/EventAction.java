package org.jboss.seam.example.ticketmonster.action;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.example.ticketmonster.model.Document;
import org.jboss.seam.example.ticketmonster.model.Event;
import org.jboss.seam.example.ticketmonster.model.Revision;
import org.jboss.seam.transaction.Transactional;
import org.jboss.seam.servlet.http.RequestParam;

/**
 * Event management related operations
 * 
 * @author Shane Bryzak
 *
 */
public @Named @ConversationScoped class EventAction implements Serializable
{
   private static final long serialVersionUID = 1735221845795268934L;
   
   @Inject EntityManager entityManager;
   @Inject Conversation conversation;
   @Inject @RequestParam("eventId") String eventId;
   
   private Event event;
   private String description;
   
   public void createEvent()
   {
      conversation.begin();
      event = new Event();      
   }
   
   public boolean isLoadEvent()
   {
      if (event == null && eventId != null && conversation.isTransient())
      {      
         conversation.begin();      
         event = entityManager.find(Event.class, Long.valueOf(eventId));
         description = event.getDescription().getActiveRevision().getContent();
      }
      
      return false;
   }   
   
   @Transactional
   public String save()
   {
      if (event.getId() != null)
      {
         entityManager.merge(event);
         
         if (!description.equals(event.getDescription().getActiveRevision().getContent()))
         {
            Revision rev = new Revision();
            rev.setContent(description);
            rev.setDocument(event.getDescription());
            rev.setCreated(new Date());
            
            entityManager.persist(rev);
            event.getDescription().setActiveRevision(rev);
            entityManager.merge(event.getDescription());            
         }
      }
      else
      {
         Document doc = new Document();
         event.setDescription(doc);         
         
         if (description != null)
         {
            Revision rev = new Revision();
            rev.setContent(description);
            rev.setDocument(event.getDescription());
            doc.setActiveRevision(rev);
            entityManager.persist(doc);
            entityManager.persist(rev);
         }
         else
         {
            entityManager.persist(doc);
         }
              
         entityManager.persist(event);

      }
      
      conversation.end();
      return "success";
   }
   
   public String cancel()
   {
      conversation.end();
      return "cancel";
   }   

   public Event getEvent()
   {
      return event;
   }
   
   public String getDescription()
   {
      return description;
   }
   
   public void setDescription(String description)
   {
      this.description = description;
   }
}
