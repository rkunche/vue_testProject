package com.test.vue.vuetest.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.test.vue.vuetest.R;
import com.test.vue.vuetest.domain.UserBase;
import com.test.vue.vuetest.domain.client.ClientUser;
import com.test.vue.vuetest.personal.OnResult;
import com.test.vue.vuetest.personal.user.SaveUser;
import com.test.vue.vuetest.personal.user.UserManager;

import junit.framework.Assert;


public class VueFacebookLoginActivity extends Activity{

    boolean loginFb = false;
    boolean updateUserWithFbId = false;
    ClientUser fbClientUser = null;

    String TAG = "VueFacebookLoginActivity";
    String logMessage = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socialnetworkingloginscreen);

        // start Facebook Login
        ClientUser clientUser =  SaveUser.getUserFromFile();
         if(clientUser == null){
             loginFb = true;
         } else if(clientUser != null && clientUser.getFacebookId() == UserBase.DEFAULT_FACEBOOK_ID){
             updateUserWithFbId = true;
             loginFb = true;
         } else {
             loginFb = false;
         }
        if(loginFb){
           loginWithFb();
        } else {
            Toast.makeText(VueFacebookLoginActivity.this,"User Logged in with Facebook Already",Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    /**
     * Login with facebook.
     */
    private void loginWithFb(){
        Session.openActiveSession(VueFacebookLoginActivity.this, true, new Session.StatusCallback() {

            // callback when session changes state
            @Override
            public void call(final Session session, SessionState state, Exception exception) {
                if (session.isOpened()) {

                    // make request to the /me API
                    Request.newMeRequest(session, new Request.GraphUserCallback() {

                        // callback after Graph API response with user object
                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                            if (user != null) {
                                String firstName = user.getFirstName();
                                String lastName = user.getLastName();
                                String fbId = user.getId();
                                String token = session.getAccessToken();
                                fbClientUser = new ClientUser();
                                fbClientUser.setFirstName(firstName);
                                fbClientUser.setLastName(lastName);
                                fbClientUser.setFacebookId(fbId);
                                fbClientUser.setFacebookShortTermAcessToken(token);
                                //check whether  user already existed in server with the same FB id.
                                    UserManager userManager = new UserManager();
                                    userManager.getUserWithFacebookId(fbId,new Callback());
                            }
                        }
                    }).executeAsync();
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

class Callback implements  OnResult{
    @Override
    public void onResultComplete(boolean status) {
        if(!status){

            if(!updateUserWithFbId) {
                //no user existed , create new client with fb id.
                Assert.assertNotNull(fbClientUser);
                UserManager userManager = new UserManager();
                userManager.createNewClientUserWithFacebookId(fbClientUser,new UpdateCallBack());
                logMessage = "createFbUser";
            } else {
                //update existing user with FB id and details.
                ClientUser clientUser =  SaveUser.getUserFromFile();
                clientUser.setFirstName(fbClientUser.getFirstName());
                clientUser.setLastName(fbClientUser.getLastName());
                clientUser.setFacebookId(fbClientUser.getFacebookId());
                clientUser.setFacebookShortTermAcessToken(fbClientUser.getFacebookShortTermAcessToken());
                UserManager userManager = new UserManager();
                userManager.updateClientUserWithFacebookId(clientUser,new UpdateCallBack());
                logMessage = "updateFbUser";
            }
        } else {
            Toast.makeText(VueFacebookLoginActivity.this,"User Logged in with Facebook",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
class UpdateCallBack implements  OnResult {
    @Override
    public void onResultComplete(boolean status) {
        if(status){
            printLog(logMessage +"Success");
            //user updated Successfully
        } else {
            //user updation failed.
            printLog(logMessage +"Failed");
        }
        finish();
    }
}
    private void printLog(String message){
        Log.i(TAG,TAG+" "+message);
    }
}
