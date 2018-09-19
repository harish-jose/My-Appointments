package com.harishjose.myappointments.services;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.harishjose.myappointments.constants.AppConstants;
import com.harishjose.myappointments.models.RssFeed;
import com.harishjose.myappointments.utils.PreferenceUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by harish.jose on 19-09-2018.
 */
public class RssFeedPollingTask implements Runnable {

    private Context context;
    private OnRssFeedCallback callback;
    private String oldFeedId;

    public RssFeedPollingTask(Context context, OnRssFeedCallback onRssFeedCallback) {
        this.context = context;
        this.callback = onRssFeedCallback;
    }

    @Override
    public void run() {
        oldFeedId = new PreferenceUtil(context).getStringValue(PreferenceUtil.PreferenceKeys.OLD_FEED_GUID);
        if(oldFeedId == null) {
            oldFeedId = "";
        }
        try {
            URL url = new URL(AppConstants.RSS_FEED_URL);
            InputStream inputStream = url.openConnection().getInputStream();
            RssFeed rssFeed =  parseFeed(inputStream);
            if (callback != null && rssFeed != null && !rssFeed.getGuid().equals(oldFeedId)) {
                new PreferenceUtil(context).save(PreferenceUtil.PreferenceKeys.OLD_FEED_GUID, rssFeed.getGuid());
                callback.onNewFeed(rssFeed);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Parse the xml data to RssFeed object
     * @param inputStream
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    private RssFeed parseFeed(InputStream inputStream) throws XmlPullParserException, IOException {
        RssFeed feedData = new RssFeed();
        boolean isItem = false;
        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);

            xmlPullParser.nextTag();
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();
                if(name == null)
                    continue;

                if(eventType == XmlPullParser.END_TAG) {
                    if(name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if(name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }

                Log.d("MyXmlParser", "Parsing name ==> " + name);
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    if(isItem) {
                        feedData.setMessage(result);  //Item title captured
                    } else {
                        feedData.setChannelTitle(result); //Channel title captured
                    }
                }

                if(isItem && name.equalsIgnoreCase("guid")) {
                    feedData.setGuid(result);
                    break;
                }
            }

            return feedData;
        } finally {
            inputStream.close();
        }
    }


    public interface OnRssFeedCallback {
        void onNewFeed(RssFeed rssFeed);
    }
}
