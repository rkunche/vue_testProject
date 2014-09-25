package com.test.vue.vuetest.messagecenter;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.test.vue.vuetest.R;
import com.test.vue.vuetest.swipeoption.ListViewSwipeGesture;
import com.test.vue.vuetest.swipeoption.swipelistview.BaseSwipeListViewListener;
import com.test.vue.vuetest.swipeoption.swipelistview.SwipeListView;


public class PopupFragment extends Fragment {
    Context mContext;
    LayoutInflater mInflater;
    private int mListWidthFactor = 20;
    BaseAdapter mNotificationAdapter;
    private ArrayList<NotificationAisle> mNotificationList;
    private SwipeDismissList mSwipeList;
    SwipeListView mListView;

   // EditText mFeedbackEditText;
    //TextView mFeedbackHintTview;
    boolean animStarted = false;
    int mBeforeTextChangeCount = 0;
    Typeface typeface;
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }
    
  /*  public PopupFragment(ArrayList<NotificationAisle> notificationList) {
        mNotificationList = notificationList;
    }*/
    
    public PopupFragment() {
        
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_list_layout, container, false);
        typeface = Typeface.createFromAsset(mContext.getAssets(), "Roboto-Regular.ttf");
        View header_messages_list = inflater.inflate(R.layout.popup_header, null);

        
       // mFeedbackEditText = (EditText) header_messages_list.findViewById(R.id.message_id);
      //  mFeedbackHintTview = (TextView) header_messages_list.findViewById(R.id.text_id);
       // mFeedbackHintTview.setVisibility(View.GONE);
      //  final ColorStateList colors = mFeedbackEditText.getHintTextColors();
      //  mFeedbackHintTview.setTextColor(colors);
        ImageView sendButton = (ImageView) header_messages_list.findViewById(R.id.send_button);
        LinearLayout listLay = (LinearLayout) v.findViewById(R.id.list_lay_id);
        mListView = (SwipeListView) v.findViewById(R.id.list_id);
        LayoutParams params = new LayoutParams(
                getLayoutWidth(), LayoutParams.MATCH_PARENT);

        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        listLay.setLayoutParams(params);
        mListView.addHeaderView(header_messages_list);
        mNotificationAdapter = new NotificationListAdapter(mContext,
                mNotificationList,typeface);

        mListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d("swipe", String.format("onStartClose %d", position));
            }

            @Override
            public void onClickFrontView(int position) {
                Log.d("swipe", String.format("onClickFrontView %d", position));


             //   mListView.openAnimate(position); //when you touch front view it will open


            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));
                mListView.closeAnimate(position);//when you touch back view it will close
               // mListView.removeViewAt(position);
              //  mNotificationAdapter.notifyDataSetChanged();

            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {

            }

        });

        //These are the swipe listview settings. you can change these
        //setting as your requirement
        mListView.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT); // there are five swiping modes
        mListView.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL); //there are four swipe actions
        mListView.setSwipeActionRight(SwipeListView.SWIPE_ACTION_NONE);
        mListView.setOffsetLeft(convertDpToPixel(120f)); // left side offset
       // mListView.setOffsetRight(convertDpToPixel(60f)); // right side offset
        mListView.setAnimationTime(500); // Animation time
      //  mListView.setSwipeOpenOnLongPress(true); // enable or disable SwipeOpenOnLongPress

        mListView.setAdapter(mNotificationAdapter);

        //for like ios swipe
//        final ListViewSwipeGesture touchListener = new ListViewSwipeGesture(
//                mListView, swipeListener, getActivity());
//        touchListener.SwipeType	=	ListViewSwipeGesture.Double;
       // mListView.setOnTouchListener(touchListener);
 /*       mFeedbackEditText.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                
            }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                mBeforeTextChangeCount = s.length();
                
            }
            
            @Override
            public void afterTextChanged(Editable s) {
                if (mBeforeTextChangeCount < s.length() && s.length() == 1) {
                    moveUpAnim();
                } else if (s.length() == 0) {
                    
                    if (!animStarted) {
                        animStarted = true;
                        mFeedbackEditText.setText(" ");
                        moveDownAnim();
                    }
                    
                }
            }
        });*/
      /*  sendButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                final String feedBackText = mFeedbackEditText.getText()
                        .toString();
                if (feedBackText.length() > 0) {
                    InputMethodManager imm = (InputMethodManager) getActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            mFeedbackEditText.getWindowToken(), 0);
                    mFeedbackEditText.setText("");
                    // TODO: when clicks on send button do some action here.
                } else {
                    Toast.makeText(getActivity(), "Type your feedback",
                            Toast.LENGTH_SHORT).show();
                }
                
            }
        });*/
      /*  mListView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        mListView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                mFeedbackEditText.getParent()
//                        .requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });*/
//        mFeedbackEditText.setOnTouchListener(new OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // to provide touch scroll for edit text
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });
       /* SwipeDismissList.UndoMode mode = SwipeDismissList.UndoMode.values()[0];
        // Create a new SwipeDismissList from the activities listview.
        mSwipeList = new SwipeDismissList(
        // 1st parameter is the ListView you want to use
                mListView,
                // 2nd parameter is an OnDismissCallback, that handles the
                // deletion
                // and undo of list items. It only needs to implement onDismiss.
                // This method can return an Undoable (then this deletion can be
                // undone)
                // or null (if the user shouldn't get the possibility to undo
                // the
                // deletion).
                new SwipeDismissList.OnDismissCallback() {
                    *//**
                     * Will be called, whenever the user swiped out an list
                     * item.
                     * 
                     * @param listView
                     *            The {@link android.widget.ListView} that the item was deleted
                     *            from.
                     * @param position
                     *            The position of the item, that was deleted.
                     * @return An {@link com.test.vue.vuetest.messagecenter.SwipeDismissList.Undoable} or {@code null} if this
                     *         deletion shouldn't be undoable.
                     *//*
                    public SwipeDismissList.Undoable onDismiss(
                            AbsListView listView, final int position) {
                        // Delete that item from the adapter.
                        NotificationAisle notificationAisle = ((NotificationListAdapter) mNotificationAdapter)
                                .removeItem(position - 1);
                        int slNo = 0;
                        if (notificationAisle != null) {
                            slNo = notificationAisle.getId();
                        }
                        
                        if (slNo != 0) {*//*
                                         * if
                                         * (notificationAisle.getAggregatedAisles
                                         * () != null &&
                                         * notificationAisle.getAggregatedAisles
                                         * () .size() > 0) { for
                                         * (NotificationAisle notificationAisle1
                                         * : notificationAisle
                                         * .getAggregatedAisles()) {
                                         * DataBaseManager.getInstance(mContext)
                                         * .deleteNotificationAisle(
                                         * notificationAisle1.getId()); } } else
                                         * {
                                         * DataBaseManager.getInstance(mContext)
                                         * .deleteNotificationAisle(slNo); }
                                         *//*
                        }
                        
                        // Return an Undoable, for that deletion. If you write
                        // return null
                        // instead, this deletion won't be undoable.
                        return new SwipeDismissList.Undoable() {
                            *//**
                             * Optional method. If you implement this method,
                             * the returned String will be presented in the undo
                             * view to the user.
                             *//*
                            @Override
                            public String getTitle() {
                                return *//* item + *//*" deleted";
                            }
                            
                            *//**
                             * Will be called when the user hits undo. You want
                             * to reinsert the item to the adapter again. The
                             * library will always call undo in the reverse
                             * order the item has been deleted. So you can
                             * insert the item at the position it was deleted
                             * from, unless you have modified the list (added or
                             * removed items) somewhere else in your activity.
                             * If you do so, you might want to call
                             * {@link SwipeDismissList#discardUndo()}, so the
                             * user cannot undo the action anymore. If you still
                             * want the user to be able to undo the deletion
                             * (after you modified the list somewhere else) you
                             * will need to calculate the new position of this
                             * item yourself.
                             *//*
                            @Override
                            public void undo() {
                                // Reinsert the item at its previous position.
                                // mNotificationAdapter.insert(item, position);
                            }
                            
                            *//**
                             * Will be called, when the user doesn't have the
                             * possibility to undo the action anymore. This can
                             * either happen, because the undo timed out or
                             * {@link SwipeDismissList#discardUndo()} was
                             * called. If you have stored your objects somewhere
                             * persistent (e.g. a database) you might want to
                             * use this method to delete the object from this
                             * persistent storage.
                             *//*
                            @Override
                            public void discard() {
                                // Just write a log message (use logcat to see
                                // the effect)
                                *//*
                                 * Log.w("DISCARD", "item " + item "" +
                                 * " now finally discarded");
                                 *//*
                            }
                        };
                        
                    }



                },
                // 3rd parameter needs to be the mode the list is generated.
                mode);

        mSwipeList.setSwipeDirection(SwipeDismissList.SwipeDirection.START);
        mSwipeList.discardUndo();*/
     /*   mListView.setOnItemClickListener(new OnItemClickListener() {
            
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                ((NotificationListAdapter) mNotificationAdapter)
                        .loadScreenForNotificationAisle(arg2 - 1);
            }
        });*/
        return v;
    }
    public int convertDpToPixel(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
    }

    ListViewSwipeGesture.TouchCallbacks swipeListener = new ListViewSwipeGesture.TouchCallbacks() {

        @Override
        public void FullSwipeListView(int position) {
            // TODO Auto-generated method stub
            Toast.makeText(getActivity(),"Action_2", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void HalfSwipeListView(int position) {
            // TODO Auto-generated method stub
            Toast.makeText(getActivity(),"Action_1", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void LoadDataForScroll(int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onDismiss(ListView listView, int[] reverseSortedPositions) {
            // TODO Auto-generated method stub
            Toast.makeText(getActivity(),"Delete", Toast.LENGTH_SHORT).show();
            for(int i:reverseSortedPositions){
                NotificationAisle notificationAisle = ((NotificationListAdapter) mNotificationAdapter)
                        .removeItem(i - 1);
                mNotificationAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void OnClickListView(int position) {
            // TODO Auto-generated method stub
            //	startActivity(new Intent(getApplicationContext(),TestActivity.class));
        }

    };

    /**
     * 
     * returns % of the screen width. This is the width of the Notification
     * list.
     */
    private int getLayoutWidth() {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int widthOfList = dm.widthPixels - (dm.widthPixels * mListWidthFactor)
                / 100;
        return widthOfList;
        
    }
    
    @Override
    public void onPause() {
        super.onPause();
        
    }
    
    @Override
    public void onResume() {
        super.onResume();

        
    }





}
