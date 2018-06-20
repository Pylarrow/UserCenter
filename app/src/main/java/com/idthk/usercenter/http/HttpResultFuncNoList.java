package com.idthk.usercenter.http;


import com.dgrlucky.log.LogX;

import rx.functions.Func1;


public class HttpResultFuncNoList implements Func1<ResponseModel, ResponseModel> {

    @Override
    public ResponseModel call(ResponseModel responseModel) {
        LogX.e(responseModel);
        if (responseModel.getCode() == NetworkException.REQUEST_OK) {
            //Log.i("lin", "---lin--->  目前没发生错误：  " + responseModel.getCode()+responseModel.getMsg());
            return responseModel;
        }else if(responseModel.getCode() == NetworkException.RESPONSE_CODE_901
                ||responseModel.getCode() == NetworkException.RESPONSE_CODE_91
                ){

            throw new NetworkException(responseModel.getMsg());
        } else {
            //Log.i("lin", "---lin--->  错误代码：  " + responseModel.getCode());
            throw  new NetworkException(responseModel.getCode());
        }
    }
}
