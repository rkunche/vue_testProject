package com.test.vue.vuetest.models;

import com.test.vue.vuetest.domain.client.ClientAisle;

import java.util.ArrayList;

public class ListDataContainer {
   public static ListDataContainer listDataContainer = null;
    ArrayList<ClientAisle> windowList = new ArrayList<ClientAisle>();
    private ListDataContainer(){
        ClientAisle tempAisle = new ClientAisle();
        windowList.add(tempAisle);
     }
    public static ListDataContainer getInstance(){
        if(listDataContainer == null){
            listDataContainer = new ListDataContainer();
        }
        return listDataContainer;
    }

    public void addData(ArrayList<ClientAisle> list){
        windowList.addAll(list);
    }
    public ArrayList<ClientAisle> getWindowList(){
        return windowList;
    }
}
