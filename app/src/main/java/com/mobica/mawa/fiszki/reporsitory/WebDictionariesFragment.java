package com.mobica.mawa.fiszki.reporsitory;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.common.AlertHelper;
import com.mobica.mawa.fiszki.common.DownloadAdapterClickListener;
import com.mobica.mawa.fiszki.common.DownloadArrayAdapter;
import com.mobica.mawa.fiszki.dao.FiszkiDao;
import com.mobica.mawa.fiszki.dao.bean.Language;
import com.mobica.mawa.fiszki.dao.bean.Word;
import com.mobica.mawa.fiszki.helper.ObjectHelper;
import com.mobica.mawa.fiszki.rest.RestAdapter;
import com.mobica.mawa.fiszki.rest.WordsService;
import com.mobica.mawa.fiszki.rest.dto.Dictionaries;
import com.mobica.mawa.fiszki.rest.dto.Dictionary;
import com.mobica.mawa.fiszki.rest.dto.Words;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.fragment.provided.RoboFragment;
import roboguice.inject.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebDictionariesFragment extends RoboFragment implements DownloadAdapterClickListener {

    private static final String DICT_KEY = "DICT_KEY";
    @Inject
    RestAdapter restAdapter;
    @Inject
    FiszkiDao fiszkiDao;

    private Dictionaries dictionaries;
    private Context context;

    @InjectView(R.id.webDictionariesList)
    private ListView dictionariesListView;


    public WebDictionariesFragment() {
        // Required empty public constructor
    }


    public static Fragment newInstance(Dictionaries dictionaries) {
        WebDictionariesFragment wdf = new WebDictionariesFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DICT_KEY, dictionaries);
        wdf.setArguments(bundle);
        return wdf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_dictionaries, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dictionaries = getArguments().getParcelable(DICT_KEY);

        DownloadArrayAdapter adapter = new DownloadArrayAdapter(getActivity(), dictionaries.dictionaries, this);
        dictionariesListView.setAdapter(adapter);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    @Override
    public void downloadDictionary(final Dictionary dictionary) {
        WordsService wordsService = restAdapter.create(WordsService.class);
        wordsService.enumerate(dictionary.getId(), new Callback<Words>() {

            @Override
            public void success(Words words, Response response) {
                if (words != null && words.words != null) {
                    addDictionary(dictionary, words.words);
                    AlertHelper.showInfo(context, context.getString(R.string.info), context.getString(R.string.dictionaryDownloaded));
                } else {
                    AlertHelper.showError(context, context.getString(R.string.noDataAvailableOnServer));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                AlertHelper.showError(context, String.format(context.getString(R.string.couldNotRetrieveWords), error.getLocalizedMessage()));
            }
        });
    }

    private void addDictionary(Dictionary dictionary, List<com.mobica.mawa.fiszki.rest.dto.Word> words) {
        try {
            Language baseLanguage = fiszkiDao.getLanguageDao().getBaseLanguage();
            Language refLanguage = fiszkiDao.getLanguageDao().getRefLanguage();
            com.mobica.mawa.fiszki.dao.bean.Dictionary dbDictionary = ObjectHelper.fromDictionaryDto(dictionary, baseLanguage, refLanguage);
            dbDictionary = fiszkiDao.getDictionaryDao().create(dbDictionary);
            List<Word> dbWords = ObjectHelper.fromWordDto(words, dbDictionary);
            fiszkiDao.getWordDao().create(dbWords);
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void displayDictionaryDetails(Dictionary dictionary) {
        AlertHelper.showInfo(context, dictionary.getName(), dictionary.getDescription());
    }
}
