/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aura.gui.article;

import aura.entities.User;
import aura.entities.article;
import aura.gui.HomeForm;
import aura.gui.Listact;
import aura.gui.Listth;
import aura.gui.objectif.ObjectifForm;
import aura.services.ServiceArticle;
import aura.services.ServiceUser;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import java.util.ArrayList;

/**
 *
 * @author akram
 */

    public class ListArticle extends Form{
        Form current;
    User user;
    String username;
 ServiceArticle sa = new ServiceArticle();
    ArrayList<article> List=sa.getAllObjectifs();
  
    public ListArticle(String username){
       this.setTitle("List");
        this.setLayout(BoxLayout.y());
              this.username = username;
        this.user = ServiceUser.getInstance().getUser(this.username);
        //***************************************SIDE MENU********************
        Toolbar tb= this.getToolbar();
                 Form forms = new Form();
//Image icon=theme.getImage("logo.png");
Container topbar=BorderLayout.east(new Label());
topbar.add(BorderLayout.SOUTH,new Label("AURA"));
topbar.setUIID("SideCommand");
tb.addComponentToSideMenu(topbar);

        tb.addMaterialCommandToSideMenu("Therapie", FontImage.MATERIAL_RECORD_VOICE_OVER, ev->{
                         new Listth(current,username);
                         

        });
          tb.addMaterialCommandToSideMenu("Activite", FontImage.MATERIAL_EXTENSION, ev->{
                         new Listact(current,username);
                         

        });
           tb.addMaterialCommandToSideMenu("Objectifs", FontImage.MATERIAL_CHECK, ev->{
                         new ObjectifForm(username);
                         

        });
           tb.addMaterialCommandToSideMenu("Articles", FontImage.MATERIAL_ARTICLE, ev -> {
            new HomeArticle(username).show();

        });
//***************************************End SIDE MENU********************
        //SpanLabel tasksListSP = new SpanLabel();
       // tasksListSP.setText(ServiceArticle.getInstance().getAllObjectifs().toString());
        
        //this.add(tasksListSP);
        /* Container cnt2 = new Container(BoxLayout.y());
        cnt2.setScrollableY(true);
        this.add(cnt2);*/
        
      
                 
       for(int i=0; i < List.size() ; i++){
           MultiButton mb = new MultiButton(List.get(i).getTitre());
           mb.setTextLine2(List.get(i).getTheme());
           mb.setTextLine3(List.get(i).getNom_auteur());
          int id = List.get(i).getId();
          String Titre =List.get(i).getTitre();
          String Theme =List.get(i).getTheme();
          String Auteur =List.get(i).getNom_auteur();
          String Date =List.get(i).getDate();
          String Article=List.get(i).getArticle();
          String idUser=List.get(i).getId_user();
           mb.addActionListener(e -> new detailarticle(idUser,id, Titre, Theme, Auteur, Date, Article,false,username).show());
           add(mb);
       }
          this.getToolbar().addSearchCommand(e -> {
    String text = (String)e.getSource();
    if(text == null || text.length() == 0) {
        // clear search
        for(Component cmp : this.getContentPane()) {
            cmp.setHidden(false);
            cmp.setVisible(true);
        }
        this.getContentPane().animateLayout(150);
    } else {
        text = text.toLowerCase();
        for(Component cmp : this.getContentPane()) {
            MultiButton mb = (MultiButton)cmp;
            String line1 = mb.getTextLine1();
            String line2 = mb.getTextLine2();
            boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1 ||
                    line2 != null && line2.toLowerCase().indexOf(text) > -1;
            mb.setHidden(!show);
            mb.setVisible(show);
        }
        this.getContentPane().animateLayout(150);
    }
}, 4);

this.show();
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, ev-> new HomeArticle(username).showBack());
}
        
      
    }

