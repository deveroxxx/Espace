package espace.controllers;

import espace.entity.Item;
import espace.entity.User;
import espace.managers.UserManager;
import espace.utils.Messages;
import org.primefaces.component.fileupload.FileUpload;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean(name = "fileUploadView", eager = true)
@RequestScoped
public class FileUploadController implements Serializable {

        private UploadedFile upFile;

        @Inject
        private UserManager userManager;

        public UploadedFile getFile() {
            return upFile;
        }

        public void setFile(UploadedFile file) {
            this.upFile = file;
        }

        public String upload(Object object) {

            if (upFile != null) {
                if (upFile.getFileName().isEmpty()) {
                    Messages.warn("Warning!", "Select a picture before press the button");
                    return null;
                }
                InputStream input = null;
                try {
                    input = upFile.getInputstream();
                    //we addCategory this before the filename so same file can be uploaded multiple times
                    String currentTimeString = "" + System.currentTimeMillis();

                    ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                    String resHomeImgPath = servletContext.getRealPath("Content\\images");
                    //System.out.println(resHomeImgPath);

                    File file = new File(resHomeImgPath + "/",currentTimeString+ upFile.getFileName());
                    //file.mkdirs();
                    Files.copy(input, file.toPath());

                    if (object instanceof Item) {
                        Item temp = (Item) object;
                        temp.setPicture("/Content/images/" + currentTimeString + upFile.getFileName());
                    }

                    if (object instanceof User) {
                        User temp = (User) object;
                        temp.setPicture("/Content/images/" + currentTimeString + upFile.getFileName());
                        userManager.update(temp);
                    }

                    //System.out.println(file.toPath());
                } catch (IOException ex) {
                    Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Warning:", "The file already exists!");
                    RequestContext.getCurrentInstance().showMessageInDialog(message);
                } finally {
                    try {
                        input.close();
                    } catch (IOException ex) {
                        Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            return null;
        }
    }
