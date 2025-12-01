package com.project.XmlCrud.Service;

import com.project.XmlCrud.Model.Municipalite;
import com.project.XmlCrud.Model.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    public void addNotification(Notification notification) {
        Municipalite municipalite = XmlUtil.loadMunicipalite();
        municipalite.addNotification(notification);
        XmlUtil.saveMunicipalite(municipalite);
    }

    public List<Notification> getAllNotifications() {
        return XmlUtil.loadMunicipalite().getNotifications();
    }

    public Notification getNotificationById(Integer idN) {
        return XmlUtil.loadMunicipalite().getNotifications()
                .stream()
                .filter(n -> n.getIdN().equals(idN))
                .findFirst()
                .orElse(null);
    }

    public boolean updateNotification(Notification updatedNotification) {
        Municipalite municipalite = XmlUtil.loadMunicipalite();

        for (int i = 0; i < municipalite.getNotifications().size(); i++) {
            Notification existing = municipalite.getNotifications().get(i);
            if (existing.getIdN().equals(updatedNotification.getIdN())) {
                municipalite.getNotifications().set(i, updatedNotification);
                XmlUtil.saveMunicipalite(municipalite);
                return true;
            }
        }
        return false;
    }

    public boolean deleteNotification(Integer idN) {
        Municipalite municipalite = XmlUtil.loadMunicipalite();
        boolean removed = municipalite.removeNotificationById(idN);
        if (removed) {
            XmlUtil.saveMunicipalite(municipalite);
        }
        return removed;
    }
}
