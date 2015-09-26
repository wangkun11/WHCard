package com.whcard.myview;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import com.whcard.main.R;
   //�Զ���Dialog
    class MyDialog extends AlertDialog{
        
        private Window window = null;
        
        public MyDialog(Context context)
        {
            super(context);
        }
        
        public void showDialog(int layoutResID, int x, int y){
            setContentView(layoutResID);
            
            windowDeploy(x, y);
            
            //���ô����Ի�������ĵط�ȡ���Ի���
            setCanceledOnTouchOutside(true);
            show();
        }
        
        //���ô�����ʾ
        public void windowDeploy(int x, int y){
            window = getWindow(); //�õ��Ի���
            window.setWindowAnimations(R.style.dialogWindowAnim); //���ô��ڵ�������
            window.setBackgroundDrawableResource(R.color.vifrification); //���öԻ��򱳾�Ϊ͸��
            WindowManager.LayoutParams wl = window.getAttributes();
            //����x��y�������ô�����Ҫ��ʾ��λ��
            wl.x = x; //xС��0���ƣ�����0����
            wl.y = y; //yС��0���ƣ�����0����  
//            wl.alpha = 0.6f; //����͸����
//            wl.gravity = Gravity.BOTTOM; //��������
            window.setAttributes(wl);
        }
    
}

 
