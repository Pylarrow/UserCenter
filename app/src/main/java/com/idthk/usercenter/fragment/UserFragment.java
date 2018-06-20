//package com.idthk.usercenter.fragment;
//
//import android.Manifest;
//import android.annotation.TargetApi;
//import android.content.ContentUris;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.DocumentsContract;
//import android.provider.MediaStore;
//import android.support.v4.content.FileProvider;
//import android.support.v7.app.AlertDialog;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//import com.idthk.usercenter.APP;
//import com.idthk.usercenter.R;
//import com.idthk.usercenter.dialog.LoginDialog;
////import com.idthk.usercenter.permission.PermissionsActivity;
////import com.idthk.usercenter.permission.PermissionsChecker;
//import com.idthk.usercenter.utils.tools.FileStorage;
//import com.idthk.usercenter.view.CircleImageView;
//
//import java.io.File;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//import static android.app.Activity.RESULT_OK;
//
///**
// * Created by williamyin on 2017/12/20.
// */
//
//public class UserFragment extends BaseFragment {
//    public static UserFragment newInstance(String s) {
//        UserFragment userCenterFragment = new UserFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("user", s);
//        userCenterFragment.setArguments(bundle);
//        return userCenterFragment;
//    }
//
//    private ViewHolder viewHolder;
//    private CircleImageView user_photo;
//    /* 请求识别码 */
//    private static final int CODE_GALLERY_REQUEST = 0xa0;//相册选取
//    private static final int CODE_CAMERA_REQUEST = 0xa1; //拍照
//    private static final int CODE_RESULT_REQUEST = 0xa2; //剪裁图片
//    private static final int REQUEST_PERMISSION = 0xa5;  //权限请求
//
////    private PermissionsChecker mPermissionsChecker; // 权限检测器
//    static final String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.CAMERA};
//    private Uri imageUri;//原图保存地址
//    private boolean isClickCamera;
//    private String imagePath;
//
//    @Override
//    protected void lazyLoad() {
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case CODE_GALLERY_REQUEST: // 相册
//                if (Build.VERSION.SDK_INT >= 19) {
//                    handleImageOnKitKat(data);
//                } else {
//                    handleImageBeforeKitKat(data);
//                }
//                break;
//            case CODE_CAMERA_REQUEST:  //拍照
//                if (hasSdcard()) {
//                    if (resultCode == RESULT_OK) {
//                        cropPhoto();
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "没有SDCard!", Toast.LENGTH_LONG)
//                            .show();
//                }
//
//                break;
//            case CODE_RESULT_REQUEST:
//
//                Bitmap bitmap = null;
//                try {
//                    if (isClickCamera) {
//                        Log.i("onActivitfyResult:1 ", imageUri.toString());
//                        bitmap = BitmapFactory.decodeStream(APP.getContext().getContentResolver().openInputStream(imageUri));
//                    } else {
//                        Log.i("onActivitfyResult: 2", imagePath);
//                        bitmap = BitmapFactory.decodeFile(imagePath);
//                    }
//                    setImageToHeadView(bitmap);
//                } catch (Exception e) {
//                    Log.i("onActivitfyResult:1 ", "异常");
//                    e.printStackTrace();
//                }
//                break;
//            case REQUEST_PERMISSION://权限请求
//
////                if (resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
//////                    finish();
////
////                } else {
////
////                    if (isClickCamera) {
////                        openCamera();
////                    } else {
////                        selectFromAlbum();
////                    }
////                }
//                break;
//        }
//    }
//
//    /**
//     * 提取保存裁剪之后的图片数据，并设置头像部分的View
//     */
//    private void setImageToHeadView(final Bitmap bitmap) {
//        viewHolder.userPhoto.setImageBitmap(bitmap);
//    }
//
//    /**
//     * 打开系统相机
//     */
//    private void openCamera() {
//        File file = new FileStorage(getActivity()).createIconFile();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            imageUri = FileProvider.getUriForFile(APP.getContext(), "com.idthk.globeuser.fileprovider", file);//通过FileProvider创建一个content类型的Uri
//        } else {
//            imageUri = Uri.fromFile(file);
//        }
//        Intent intent = new Intent();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
//        }
//        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
//        startActivityForResult(intent, CODE_CAMERA_REQUEST);
//    }
//
//    /**
//     * 从相册选择
//     */
//    private void selectFromAlbum() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        startActivityForResult(intent, CODE_GALLERY_REQUEST);
//    }
//
//    /**
//     * 裁剪
//     */
//    private void cropPhoto() {
//        Log.i("onActivitfyResult: ", "55");
//        File file = new FileStorage(getActivity()).createCropFile();
//        Uri outputUri = Uri.fromFile(file);//缩略图保存地址
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        }
//        intent.setDataAndType(imageUri, "image/*");
////        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("scale", true);
//        intent.putExtra("return-data", false);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("noFaceDetection", true);
//        Log.i("onActivitfyResult:", "66");
//        startActivityForResult(intent, CODE_RESULT_REQUEST);
//        Log.i("onActivitfyResult: ", "77");
//    }
//
//    @TargetApi(19)
//    private void handleImageOnKitKat(Intent data) {
//        imagePath = null;
//        imageUri = data.getData();
//        if (DocumentsContract.isDocumentUri(APP.getContext(), imageUri)) {
//            //如果是document类型的uri,则通过document id处理
//            String docId = DocumentsContract.getDocumentId(imageUri);
//            if ("com.android.providers.media.documents".equals(imageUri.getAuthority())) {
//                String id = docId.split(":")[1];//解析出数字格式的id
//                String selection = MediaStore.Images.Media._ID + "=" + id;
//                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
//            } else if ("com.android.downloads.documents".equals(imageUri.getAuthority())) {
//                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
//                imagePath = getImagePath(contentUri, null);
//            }
//        } else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
//            //如果是content类型的Uri，则使用普通方式处理
//            imagePath = getImagePath(imageUri, null);
//        } else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
//            //如果是file类型的Uri,直接获取图片路径即可
//            imagePath = imageUri.getPath();
//        }
//
//        cropPhoto();
//    }
//
//    private void handleImageBeforeKitKat(Intent intent) {
//        imageUri = intent.getData();
//        imagePath = getImagePath(imageUri, null);
//        cropPhoto();
//    }
//
//    private void startPermissionsActivity() {
////        PermissionsActivity.startActivityForResult(getActivity(), REQUEST_PERMISSION, PERMISSIONS);
//    }
//
//    private String getImagePath(Uri uri, String selection) {
//        String path = null;
//        //通过Uri和selection老获取真实的图片路径
//        Cursor cursor = APP.getContext().getContentResolver().query(uri, null, selection, null, null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//            }
//            cursor.close();
//        }
//        return path;
//    }
//
//    /**
//     * 检查设备是否存在SDCard的工具方法
//     */
//    public static boolean hasSdcard() {
//        String state = Environment.getExternalStorageState();
//        if (state.equals(Environment.MEDIA_MOUNTED)) {
//            // 有存储的SDCard
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.user;
//    }
//
//    @Override
//    protected void initView(View view) {
//
//        viewHolder = new ViewHolder(view);
//        viewHolder.photoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AlertDialog.Builder(getActivity())
//                        .setTitle("选择头像")//
//
//                        .setNegativeButton("相册", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                                    if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
////                                        startPermissionsActivity();
////                                    } else {
////                                        selectFromAlbum();
////                                    }
//                                } else {
//                                    selectFromAlbum();
//                                }
//                                isClickCamera = false;
//                            }
//                        })
//
//                        .setPositiveButton("拍照", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                //检查权限(6.0以上做权限判断)
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                    if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
//                                        Log.i("onClickdd: ", "11");
//                                        startPermissionsActivity();
//                                    } else {
//                                        Log.i("onClickdd: ", "22");
//                                        openCamera();
//                                    }
//                                } else {
//                                    openCamera();
//                                }
//                                isClickCamera = true;
//
//                            }
//                        }).show();
//            }
//        });
//    }
//
//    @Override
//    protected void initData() {
//        mPermissionsChecker = new PermissionsChecker(APP.getContext());
//        viewHolder.outLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new LoginDialog(getActivity());
////                Intent intent = new Intent(getActivity(), LoginActivity.class);
////                startActivity(intent);
//            }
//        });
//    }
//
//    static class ViewHolder {
//        @BindView(R.id.text_title)
//        TextView textTitle;
//        @BindView(R.id.root)
//        View root;
//        @BindView(R.id.root_lin)
//        LinearLayout rootLin;
//        @BindView(R.id.user_photo)
//        CircleImageView userPhoto;
//        @BindView(R.id.photo_button)
//        ImageView photoButton;
//        @BindView(R.id.out_login)
//        Button outLogin;
//
//
//        ViewHolder(View view) {
//            ButterKnife.bind(this, view);
//        }
//    }
//}
