package com.train.portal.utils;

import com.train.portal.db.DataStore;
import com.train.portal.ticket.modal.UserDetails;

import java.util.List;
import java.util.Map;

public class UserUtils {

    public static List<UserDetails> getUserDetailsFromEmails(List<String> emails) {

        Map<String, UserDetails> usersMap=DataStore.getInstance().getUserDetailsMap();

        return emails.stream()
                .map(usersMap::get)
                .toList();

    }
}
