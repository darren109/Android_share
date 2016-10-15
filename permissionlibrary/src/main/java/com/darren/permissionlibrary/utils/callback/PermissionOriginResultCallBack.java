package com.darren.permissionlibrary.utils.callback;


import com.darren.permissionlibrary.utils.PermissionInfo;

import java.util.List;

/**
 * Created by darren on 16/1/19.
 */
public interface PermissionOriginResultCallBack {

    /**
     * 返回所有结果的列表list,包括通过的,允许提醒,拒绝的的三个内容,各个list有可能为空
     * list中的元素为PermissionInfo,提供getName()[例如:android.permission.CAMERA]和getShortName()[例如:CAMERA]方法
     * 在进行申请方法调用后,此方法一定会被调用返回此次请求后的权限申请的情况
     *
     * @param acceptList   已经同意
     * @param rationalList 允许提醒
     * @param deniedList   拒绝
     */
    void onResult(List<PermissionInfo> acceptList, List<PermissionInfo> rationalList, List<PermissionInfo> deniedList);
}
