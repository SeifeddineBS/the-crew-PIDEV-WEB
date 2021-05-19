/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.article;

import aura.entities.User;
import aura.entities.article;
import aura.gui.Listact;
import aura.gui.Listth;
import aura.gui.objectif.ObjectifForm;
import aura.services.ServiceArticle;
import aura.services.ServiceUser;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Util;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import static com.codename1.util.Base64.encodeNoNewline;
import java.util.Calendar;



/**
 *
 * @author akram
 */
public class updatearticle extends Form {
String data;
String username ;
Form current;
    User user;
    public updatearticle(String idUser,int id , String Titre, String Themee, String Auteur ,String Datee , String Article,String username) {
this.username=username;
       this.user=ServiceUser.getInstance().getUser(this.username);
        TextField titre = new TextField(Titre, "titre");
        TextField theme = new TextField(Themee, "theme");
        TextField auteur = new TextField(Auteur, "auteur");
        //TextField date = new TextField(Datee, "date");
        //TextField article = new TextField(Article, "article");
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
       Button filechooser = new Button("article");
        filechooser.addActionListener(e -> {
        
        if (FileChooser.isAvailable()) {
                FileChooser.showOpenDialog(".pdf", e2 -> {
                    String file = (String) e2.getSource();
                    if (file == null) {
                        this.add("No file was selected");
                        this.revalidate();
                    } else {
                        try {
                            
                           
                             data = encodeNoNewline(Util.readInputStream(FileSystemStorage.getInstance().openInputStream(file)));
                            //se.send(data);                       
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        
        
        
        });
        
           Button btnValide = new Button("Creer ");

    btnValide.addActionListener ( 
        new ActionListener() {
            
            @Override
        public void actionPerformed
        (ActionEvent evt
        
            ) {
                if ((titre.getText().length() == 0) || (theme.getText().length() == 0) || (auteur.getText().length() == 0)) {
                Dialog.show("Attention", "Veuillez verifier vos données ", new Command("OK"));
            } else {
                try {
                    //User u = new User(Integer.parseInt(tfStatus.getText()), tfName.getText());
                    article t = new article();
                    t.setId(55);
                    t.setTitre(titre.getText());
                    t.setTheme(theme.getText());
                  t.setDate(new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()));
                    t.setNom_auteur(auteur.getText());

                    //t.setArticle(article.getText());
                    t.setId_user(user.getId());
                    t.setApprouver(0);

                    if (ServiceArticle.getInstance().updateEvent(t, id)) {
                        Dialog.show("Success", "Compte crée avec succès", new Command("OK"));
                    } else {
                        Dialog.show("ERROR", "Server error", new Command("OK"));
                    }
                } catch (NumberFormatException e) {
                    Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                }

            }

        }
    }

    );

      addAll(titre, theme, auteur, filechooser, btnValide);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, ev-> new detailarticle(idUser,id, titre.getText(), Themee, Auteur, Datee, Article,false,username).showBack());

}

}
