package com.mobica.mawa.fiszki.dao;

import android.content.Context;

import com.mobica.mawa.fiszki.dao.bean.Hit;

/**
 * DAO class for @Word
 * Created by mawa on 2014-10-18.
 */
class HitDao extends AbstractDao<Hit> implements IHitDao {

    public HitDao(Context context) {
        super(context, Hit.class);
    }
}
