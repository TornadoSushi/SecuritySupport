package com.infinity.jerry.securitysupport.common.test;

import android.util.Log;

import com.infinity.jerry.securitysupport.common.entity.CoalCheckItem;
import com.infinity.jerry.securitysupport.common.entity.CompanyBaseInfo;
import com.infinity.jerry.securitysupport.common.entity.CompanyCoalEntity;
import com.infinity.jerry.securitysupport.common.entity.CompanyCoalEntityNew;
import com.infinity.jerry.securitysupport.common.entity.CompanyDoor;
import com.infinity.jerry.securitysupport.common.entity.CompanyProduct;
import com.infinity.jerry.securitysupport.common.entity.CompanySafty;
import com.infinity.jerry.securitysupport.common.entity.CompanyShaft;
import com.infinity.jerry.securitysupport.common.entity.CompanySixSys;
import com.infinity.jerry.securitysupport.common.z_utils.z_mvp.ZCommonEntity;
import com.infinity.jerry.securitysupport.common.z_utils.z_mvp.ZResultSubscriber;
import com.infinity.jerry.securitysupport.common.z_utils.z_mvp.ZServiceFactory;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jerry on 2017/11/14.
 */

public class PresenterModel {

    interface Servier {
        @POST("company/list")
        @FormUrlEncoded
        Observable<ZCommonEntity<TestCompany>> getCompany(@Field("pageNumber") int number, @Field("pageSize") int size);

        @POST("company/getBaseTable")
        Observable<ZCommonEntity<List<CompanyBaseInfo>>> getBaseInfo();

        @POST("company/getAllPro")
        Observable<ZCommonEntity<List<CompanyProduct>>> getProInfo();

        @POST("company/getAllSafty")
        Observable<ZCommonEntity<List<CompanySafty>>> getSaftyInfo();

        @POST("company/getAllDoor")
        Observable<ZCommonEntity<List<CompanyDoor>>> getDorrInfo();

        @POST("company/getAllShaft")
        Observable<ZCommonEntity<List<CompanyShaft>>> getShaftInfo();

        @POST("company/getAllSix")
        Observable<ZCommonEntity<List<CompanySixSys>>> getSixInfo();

        @POST("check/getCheckItem")
        Observable<ZCommonEntity<List<CoalCheckItem>>> getCheckItem();

//        @POST("company/listCompany")
//        Observable<ZCommonEntity<List<CompanyCoalEntityNew>>> getData();

    }

//    public static void synData() {
//        Observable<ZCommonEntity<List<CompanyCoalEntityNew>>> observable = ZServiceFactory.getInstance().createService(PresenterModel.Servier.class)
//                .getData();
//
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new ZResultSubscriber<List<CompanyCoalEntityNew>>() {
//                    @Override
//                    public void onSuccessZ(List<CompanyCoalEntityNew> entities) {
//                        Log.e("TAG", " synData " + entities.size());
//                        List<CompanyCoalEntity> entityList = new ArrayList<>();
//                        for(int i=0;i<entities.size();i++){
//                            CompanyCoalEntity coalEntity = new CompanyCoalEntity();
//                            coalEntity.setId(entities.get(i).getId());
//                            coalEntity.setCoalCell(entities.get(i).getCoal_cell());
//                            coalEntity.setCoalPhone(entities.get(i).getCoal_phone());
//                            coalEntity.setCompanyArea(entities.get(i).getCompany_area());
//                            coalEntity.setCompanyName(entities.get(i).getCompany_name());
//                            coalEntity.setCoalUrl(entities.get(i).getCoal_url());
//                            coalEntity.setCompanyLocation(entities.get(i).getCompany_location());
//                            coalEntity.setCompanyCode(entities.get(i).getCompany_code());
//                            coalEntity.setCompanyState(entities.get(i).getCompany_state());
//                            coalEntity.setDirector(entities.get(i).getDirector());
//                            coalEntity.setLeaglPerson(entities.get(i).getLeagl_person());
//                            entityList.add(coalEntity);
//                        }
//                        DataSupport.saveAll(entityList);
//                    }
//
//                    @Override
//                    public void onErrorZ(Throwable throwable) {
//                        Log.e("TAG", "onError   synData " + throwable.toString());
//                    }
//                });
//    }

    public static void synCheckItem() {
        Observable<ZCommonEntity<List<CoalCheckItem>>> observable = ZServiceFactory.getInstance().createService(PresenterModel.Servier.class)
                .getCheckItem();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<List<CoalCheckItem>>() {
                    @Override
                    public void onSuccessZ(List<CoalCheckItem> o) {
                        Log.e("TAG", o.size() + " pro");
                        DataSupport.saveAll(o);
                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        Log.e("TAG", "onError   company");

                    }

                });
    }


    public static void synCompany() {
        Observable<ZCommonEntity<TestCompany>> observable = ZServiceFactory.getInstance().createService(PresenterModel.Servier.class)
                .getCompany(1, 10000);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<TestCompany>() {
                    @Override
                    public void onSuccessZ(TestCompany o) {
                        Log.e("TAG", o.getContent() + " pro");
                        DataSupport.saveAll(o.getContent());

                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        Log.e("TAG", "onError   company");

                    }

                });

    }

    public static void synBaseIfo() {
        Observable<ZCommonEntity<List<CompanyBaseInfo>>> observable = ZServiceFactory.getInstance().createService(PresenterModel.Servier.class)
                .getBaseInfo();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<List<CompanyBaseInfo>>() {
                    @Override
                    public void onSuccessZ(List<CompanyBaseInfo> o) {
                        Log.e("TAG", o.size() + " base");
                        DataSupport.saveAll(o);
                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        Log.e("TAG", "base error");

                    }

                });
    }

    public static void synProInfo() {
        Observable<ZCommonEntity<List<CompanyProduct>>> observable = ZServiceFactory.getInstance().createService(PresenterModel.Servier.class)
                .getProInfo();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<List<CompanyProduct>>() {
                    @Override
                    public void onSuccessZ(List<CompanyProduct> o) {
                        Log.e("TAG", o.size() + " pro");
                        DataSupport.saveAll(o);
                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        Log.e("TAG", "pro error");
                    }

                });
    }

    public static void synSafty() {
        Observable<ZCommonEntity<List<CompanySafty>>> observable = ZServiceFactory.getInstance().createService(PresenterModel.Servier.class)
                .getSaftyInfo();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<List<CompanySafty>>() {
                    @Override
                    public void onSuccessZ(List<CompanySafty> o) {
                        Log.e("TAG", o.size() + " safty");
                        DataSupport.saveAll(o);


                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        Log.e("TAG", "safty error");
                    }

                });
    }

    public static void synDoor() {
        Observable<ZCommonEntity<List<CompanyDoor>>> observable = ZServiceFactory.getInstance().createService(PresenterModel.Servier.class)
                .getDorrInfo();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<List<CompanyDoor>>() {
                    @Override
                    public void onSuccessZ(List<CompanyDoor> o) {
                        Log.e("TAG", o.size() + " CompanyDoor");
                        DataSupport.saveAll(o);


                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        Log.e("TAG", "error CompanyDoor");
                    }

                });
    }

    public static void synShaft() {
        Observable<ZCommonEntity<List<CompanyShaft>>> observable = ZServiceFactory.getInstance().createService(PresenterModel.Servier.class)
                .getShaftInfo();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<List<CompanyShaft>>() {
                    @Override
                    public void onSuccessZ(List<CompanyShaft> o) {
                        Log.e("TAG", o.size() + " CompanyShaft");
                        DataSupport.saveAll(o);


                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        Log.e("TAG", "error CompanyShaft");
                    }

                });
    }

    public static void synSix() {
        Observable<ZCommonEntity<List<CompanySixSys>>> observable = ZServiceFactory.getInstance().createService(PresenterModel.Servier.class)
                .getSixInfo();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ZResultSubscriber<List<CompanySixSys>>() {
                    @Override
                    public void onSuccessZ(List<CompanySixSys> o) {
                        Log.e("TAG", o.size() + " synSix");
                        DataSupport.saveAll(o);

                    }

                    @Override
                    public void onErrorZ(Throwable throwable) {
                        Log.e("TAG", "error synSix");
                    }

                });
    }
}
