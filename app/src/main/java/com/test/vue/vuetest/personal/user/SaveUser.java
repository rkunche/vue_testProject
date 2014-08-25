package com.test.vue.vuetest.personal.user;

import android.content.Context;

import com.test.vue.vuetest.AnchoredContext;
import com.test.vue.vuetest.domain.client.ClientUser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveUser {
    private static final String sUserFileName ="VueUser.ser";
    public static void saveUserToFile(ClientUser user){
        try {
            FileOutputStream fos = AnchoredContext.getInstance().openFileOutput(sUserFileName,
                    Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(user);
            os.close();
        }catch (Exception e){
            e.printStackTrace();;
        }
    }
    public static ClientUser getUserFromFile(){
        try {
            FileInputStream fis = AnchoredContext.getInstance().openFileInput(sUserFileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            ClientUser vueUser = (ClientUser) is.readObject();
            is.close();
            return vueUser;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
}
